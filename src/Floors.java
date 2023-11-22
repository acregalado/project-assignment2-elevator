import java.util.LinkedList;
import java.util.Queue;

public class Floors {
    private int totalFloors;
    private Queue<Passengers>[] passengerQueues;

    public Floors(int totalFloors) {
        this.totalFloors = totalFloors;
        passengerQueues = new LinkedList[totalFloors];
        for (int i = 0; i < totalFloors; i++) {
            passengerQueues[i] = new LinkedList<>();
        }
    }

    public int getTotalFloors() {
        return totalFloors;
    }

    public void addPassenger(int floor, Passengers passenger) {
        if (isValidFloor(floor)) {
            passengerQueues[floor - 1].add(passenger);
        }
    }

    public void loadPassengers(int floor, Elevator elevator) {
        Queue<Passengers> floorQueue = getPassengerQueue(floor);
        while (!floorQueue.isEmpty() && !elevator.isFull()) {
            Passengers passenger = floorQueue.poll();
            elevator.loadPassenger(passenger);
        }
    }

    public void unloadPassengers(int floor, Elevator elevator) {
        elevator.unloadPassengers(floor, this);
    }

    public Queue<Passengers> getPassengerQueue(int floor) {
        if (isValidFloor(floor)) {
            return passengerQueues[floor - 1];
        }
        return null; // Invalid floor
    }

    public Passengers removeNextPassenger(int floor) {
        if (isValidFloor(floor)) {
            return passengerQueues[floor - 1].poll();
        }
        return null; // Invalid floor
    }

    public boolean hasPassengers(int floor) {
        return isValidFloor(floor) && !passengerQueues[floor - 1].isEmpty();
    }

    private boolean isValidFloor(int floor) {
        return floor >= 1 && floor <= totalFloors;
    }
}
