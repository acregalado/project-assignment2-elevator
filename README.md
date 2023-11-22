# Project-Assignment2-Elevator
## Overview
This project simulates the behavior of an elevator system, including the movement of elevators 
between floors, loading and unloading passengers, and analyzing wait times. The simulation is 
designed to provide insights into elevator efficiency and passenger wait times in a multi-floor 
building.

## Classes
The project is made up of  several classes, each representing a specific aspect of the simulation:

- ElevatorSim: The main class responsible for configuring and running the elevator simulation.
- Elevator: Models the behavior and state of an elevator.
- Floors: Represents the floors of the building and manages passenger queues on each floor.
- Passengers: Represents individual passengers with start and destination floors.

## Data Structure Choices
- PriorityQueue: Used to store passengers in the elevator based on their arrival time.
- LinkedList: Used to implement passenger queues on each floor.

## Other Information
My code is able to run, however I believe that it has trouble dealing with the loading and unloading 
of passengers, which results in an inaccurate conveyance time for the simulation.
