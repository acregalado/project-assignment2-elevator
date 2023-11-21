import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Elevator {
    private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_DOWN = -1;
    private static final int DIRECTION_NONE = 0;

    private int elevatorId;
    private int currentFloor;
    private int capacity;
    private PriorityQueue<Passengers> passengersQueue; // Use PriorityQueue for priority handling
    private int direction;
    private Floors floors;

    public Elevator(int elevatorId, int capacity, Floors floors) {
        this.elevatorId = elevatorId;
        this.currentFloor = 1; // Start at the first floor
        this.capacity = capacity;
        this.passengersQueue = new PriorityQueue<>(); // Use PriorityQueue
        this.direction = DIRECTION_UP; // Initial direction (can be UP, DOWN, or NONE)
        this.floors = floors;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isFull() {
        return passengersQueue.size() >= capacity;
    }

    public void moveToFloor(int targetFloor) {
        // Move the elevator to the specified target floor
        if (targetFloor > currentFloor) {
            direction = DIRECTION_UP;
        } else if (targetFloor < currentFloor) {
            direction = DIRECTION_DOWN;
        } else {
            direction = DIRECTION_NONE; // No direction change if already on the target floor
            return; // Exit the method early if already on the target floor
        }
        currentFloor = targetFloor;
    }

    public void loadPassenger(Passengers passenger) {
        // Load a passenger into the priority queue
        passengersQueue.offer(passenger);
    }

    public void unloadPassengers(int floor) {
        // Unload passengers whose destination is the specified floor
        passengersQueue.removeIf(passenger -> passenger.getDestinationFloor() == floor);
    }

    public void move(int totalFloors) {
        // Simulate elevator movement and handle loading/unloading passengers
        int floorsToMove = Math.min(3, totalFloors); // Limit movement to 5 floors

        int targetFloor;
        if (direction == DIRECTION_UP) {
            targetFloor = currentFloor + floorsToMove;
            Math.min(currentFloor + floorsToMove, totalFloors);
        } else if (direction == DIRECTION_DOWN) {
            targetFloor = currentFloor - floorsToMove;
            Math.max(currentFloor - floorsToMove, 1);
        } else {
            // If the direction is none, no movement is needed
            return;
        }

        moveToFloor(targetFloor);
        floors.loadPassengers(targetFloor, this); // Load passengers from the current floor
        floors.unloadPassengers(targetFloor, this); // Unload passengers at the destination floor

        // Update the elevator's current floor based on the direction
        currentFloor += direction;
        direction = DIRECTION_NONE; // Update direction after completing movement and unloading passengers
    }
}