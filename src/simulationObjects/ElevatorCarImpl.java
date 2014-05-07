package simulationObjects;

import java.util.ArrayList;
import java.util.Collections;

import locations.Location;


/**
 * Basic implementation of an ElevatorCar.
 * This implementation of ElevatorCar covers basic functionality, such as accepting
 * floor requests, moving between floors, and opening doors.
 * 
 * @author Kyle Moy
 *
 */
public class ElevatorCarImpl extends Location implements ElevatorCar{
	private int elevatorSpeed;
	private int doorSpeed;
	private int moveDuration;
	private int currentFloor;
	private int defaultFloor;
	private int eId;
	private boolean idle;
	private int timeOutDuration;
	private int timeOut;
	private boolean direction;
	private boolean doorWait;
	//private boolean[] floorRequests;
	private ArrayList<Integer> floorRequests;
	private ElevatorDoor door;
	/**
	 * Default constructor.
	 * @param id The number of this elevator.
	 * @param elevatorSpeed The movement speed of this elevator.
	 * @param doorSpeed The door speed of this elevator.
	 * @param timeOut The timeout length.
	 */
	public ElevatorCarImpl (int id, int elevatorSpeed, int doorSpeed, int timeOut) {
		eId = id;
		defaultFloor = 1; //To be implemented
		currentFloor = defaultFloor;
		direction = true;
		idle = true;
		doorWait = false;
		this.timeOut = timeOut;
		timeOutDuration = timeOut;
		door = new ElevatorDoor();
		//floorRequests = new boolean[Building.getFloors()];
		floorRequests = new ArrayList<Integer>();
		this.elevatorSpeed = elevatorSpeed;
		this.doorSpeed = doorSpeed;
	}
	@Override
	public void floorRequest(int floor) {
		if (floor < 0 || floor > Building.getFloors()) {
			throw new Error ("Floor request out of range");
		}
		if (!idle) {
			if (direction) {
				if (floor < currentFloor) {
					printAction("Denied request to floor " + floor);
					return;
				}
			} else {
				if (floor > currentFloor) {
					printAction("Denied request to floor " + floor);
					return;
				}
			}
		}
		//floorRequests[floor] = true;
		floorRequests.add(floor);
		printAction("Accepted request to floor " + floor);
	}
	public boolean floorRequest(int floor, boolean dir) {
		if (floor < 0 || floor > Building.getFloors()) {
			throw new Error ("Floor request out of range");
		}
		if (!idle) {
			//Wrong direction 
			if ((dir ^ direction)) return false;
			
			//Floor not in direction
			if (direction) {
				if (floor < currentFloor) {
					printAction("Denied request to floor " + floor);
					return false;
				}
			} else {
				if (floor > currentFloor) {
					printAction("Denied request to floor " + floor);
					return false;
				}
			}
		}
		//floorRequests[floor] = true;
		floorRequests.add(floor);
		printAction("Accepted request to floor " + floor);
		return true;
	}
	public boolean isIdle() {
		if (!floorRequests.isEmpty()) return false;
		return idle;
	}
	@Override
	public void step(SimulationObject parent) {
		door.step(this);
		if (door.isOpen()) return; //Waiting on door!
		else if (doorWait) {
			doorWait = false;
			printAction("Doors close");
		}
		if (idle && currentFloor != defaultFloor) {
			timeOutDuration --;
			if (timeOutDuration < 0) {
				printAction("Timed out. Returning to floor " + defaultFloor);
				//floorRequests[defaultFloor] = true;
				floorRequests.add(defaultFloor);
			}
		}
		if (idle) {
			if (!floorRequests.isEmpty()) {
				Collections.sort(floorRequests);
				int floor;
				if (direction) {
					floor = floorRequests.get(floorRequests.size()-1);
				} else {
					floor = floorRequests.get(0);
				}
				printAction("Moving towards floor " + floor);
				moveDuration = elevatorSpeed;
				idle = false;
				direction = floor > currentFloor ? true : false;
			}
		}
		if (!idle) {
			timeOutDuration = timeOut; 
			if (moveDuration == 0) {
				//Arrive on floor
				currentFloor += direction ? 1 : -1;
				printAction("Arrived on floor " + currentFloor);
				if (floorRequests.contains(currentFloor)) {
					floorRequests.remove((Integer)currentFloor);
					printAction("On requested floor " + currentFloor);
					printAction("Doors open");
					door.open(doorSpeed);
					doorWait = true;
					idle = true;
					return;
				}
				moveDuration = elevatorSpeed;
				moveDuration --;
				return;
			}
			moveDuration --;
		}
	}
	/**
	 * @param str Action to be printed
	 */
	public void printAction(String str) {
		String reqs = "";
		for (int floor : floorRequests) {
			reqs += ", " + floor;
		}
		if (reqs.length() != 0) reqs = reqs.substring(2);
		System.out.println(String.format("%-10d %-35s %10s",Building.currentTime(), "Elevator " + eId + " [Requests: " + reqs + "]: ", str));
	}
}
