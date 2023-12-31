import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Properties;
import java.io.File;

public class ElevatorSim {
    private static Queue<Passengers> passengerQueue;
    private String structures;
    private float passengers;
    private int elevators;
    private int elevatorCapacity;
    private int duration;
    private static Elevator elevator;
    private Floors floors;

    public static void main(String[] args) {
        ElevatorSim elevatorSim = new ElevatorSim();

        // Read properties from file or use defaults
        Properties properties = elevatorSim.readProperties(args);

        // Use properties to configure the simulation
        elevatorSim.configureSimulation(properties);

        // Run the simulation
        elevatorSim.runSimulation();

        // After the simulation, collect wait durations and perform analysis
        Analysis analysis = new Analysis();

        for (Passengers passenger : passengerQueue) {
            if (passenger.getConveyanceTime() != -1) {
                int waitDuration = passenger.getConveyanceTime() - passenger.getArrivalTime();
                analysis.addWaitDuration(waitDuration);
            }
        }

        // Report results
        System.out.println("Average Wait Duration: " + analysis.calculateAverageWaitDuration());
        System.out.println("Longest Wait Duration: " + analysis.findLongestWaitDuration());
        System.out.println("Shortest Wait Duration: " + analysis.findShortestWaitDuration());
    }

    private Properties readProperties(String[] args) {
        Properties properties = new Properties();

        System.out.println(new File("db.default").getAbsolutePath());

        // Load default properties from "db.default" using FileReader and BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader("db.default"))) {
            properties.load(reader);
        } catch (IOException e) {
            System.err.println("Error reading default properties from db.default");
            e.printStackTrace();
        }

        // Check if a file is specified in the command line and override default properties
        if (args.length > 0) {
            try (FileReader userInput = new FileReader(args[0]);
                 BufferedReader reader = new BufferedReader(userInput)) {
                // Load user-provided properties and override defaults
                properties.load(reader);
            } catch (IOException e) {
                System.err.println("Error reading properties from file: " + args[0]);
                e.printStackTrace();
            }
        }

        // Set class fields based on properties
        this.structures = properties.getProperty("structures");
        this.floors = new Floors(Integer.parseInt(properties.getProperty("floors"))); // Create Floors object
        this.passengers = Float.parseFloat(properties.getProperty("passengers"));
        this.elevators = Integer.parseInt(properties.getProperty("elevators"));
        this.elevatorCapacity = Integer.parseInt(properties.getProperty("elevatorCapacity"));
        this.duration = Integer.parseInt(properties.getProperty("duration"));

        return properties;
    }

    private void configureSimulation(Properties properties) {
        // Use properties to configure the simulation
        passengerQueue = new LinkedList<>();

        // Example: Assuming you have a Floors and Elevator class
        Floors totalFloors = new Floors(Integer.parseInt(properties.getProperty("floors")));
        int elevatorCapacity = Integer.parseInt(properties.getProperty("elevatorCapacity"));
        int duration = Integer.parseInt(properties.getProperty("duration"));

        elevator = new Elevator(1, elevatorCapacity, totalFloors); // Pass the floors object to the elevator
    }

    private void runSimulation() {
        // Initializing values
        int totalFloors = this.floors.getTotalFloors();
        double probabilityOfAppearance = this.passengers;
        int duration = this.duration;

        for (int tick = 0; tick < duration; tick++) {
            System.out.println("Tick: " + tick + ", Elevator Current Floor: " + elevator.getCurrentFloor());

            // Generate passengers and add them to the passenger queue
            for (int floor = 1; floor <= totalFloors; floor++) {
                if (Passengers.shouldAppear(probabilityOfAppearance)) {
                    Passengers passenger = Passengers.generateRandomPassenger(elevator.getCurrentFloor(), totalFloors, tick);
                    passengerQueue.add(passenger); // Add passenger to the queue
                    this.floors.addPassenger(passenger.getStartFloor(), passenger); // Add passenger to the floor queue
                }
            }

            // Move the elevator and handle loading/unloading passengers
            elevator.move(totalFloors);

            // Additional simulation logic
            for (Passengers passenger : passengerQueue) {
                if (passenger.getConveyanceTime() == -1) {
                    simulateElevatorMovementAndConveyance(elevator, this.floors, passenger, tick);
                }
            }

            // Debugging: Print passenger details after each tick
            for (Passengers passenger : passengerQueue) {
                System.out.println("Tick: " + tick + ", Passenger: " + passenger.getStartFloor() + " -> " + passenger.getDestinationFloor() +
                        ", Arrival Time: " + passenger.getArrivalTime());
            }
        }
    }

    private void simulateElevatorMovementAndConveyance(Elevator elevator, Floors floors, Passengers passenger, int tick) {
        int currentFloor = elevator.getCurrentFloor();
        int destinationFloor = passenger.getDestinationFloor();

        // Check if the elevator is at the destination floor
        if (currentFloor == destinationFloor && passenger.getConveyanceTime() == -1) {
            // Unload passengers whose destination is the current floor
            floors.unloadPassengers(currentFloor, elevator);

            // Load passengers from the current floor queue
            floors.loadPassengers(currentFloor, elevator);

            // Set the conveyance time when the passenger is dropped off
            passenger.setConveyanceTime(tick);
        } else {
            // Move the elevator to the destination floor
            elevator.moveToFloor(destinationFloor);
        }
    }
}