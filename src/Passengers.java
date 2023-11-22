import java.util.Random;

public class Passengers implements Comparable<Passengers> {
    private static final Random RANDOM = new Random();

    private int startFloor;
    private int destinationFloor;
    private int arrivalTime;
    private int conveyanceTime;

    public Passengers(int startFloor, int destinationFloor, int tick) {
        this.startFloor = startFloor;
        this.destinationFloor = destinationFloor;
        this.arrivalTime = tick;
    }

    public int getStartFloor() {
        return startFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getConveyanceTime() {
        return conveyanceTime;
    }

    public int getWaitingTime() {
        // Calculate and return the waiting time of the passenger
        return conveyanceTime - arrivalTime;
    }

    public static Passengers generateRandomPassenger(int currentFloor, int totalFloors, int tick) {
        // Set the start floor to the current floor of the elevator
        int startFloor = currentFloor;

        int destinationFloor;
        do {
            destinationFloor = RANDOM.nextInt(totalFloors) + 1;
        } while (destinationFloor == startFloor);

        return new Passengers(startFloor, destinationFloor, tick);
    }

    public static boolean shouldAppear(double probabilityOfAppearance) {
        // Determine if passengers appear on floor based on probability
        return RANDOM.nextDouble() < probabilityOfAppearance;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setConveyanceTime(int conveyanceTime) {
        this.conveyanceTime = conveyanceTime;
    }

    @Override
    public int compareTo(Passengers otherPassenger) {
        // Compare passengers based on their waiting times
        return Integer.compare(this.getWaitingTime(), otherPassenger.getWaitingTime());
    }
}
