package simulationObjects;

import java.util.ArrayList;


/**
 * The ElevatorController is responsible for handling the ElevatorCars.
 * The ElevatorController is a singleton object that handles the instantiation
 * and control of all the ElevatorCars. It handles requests and finds a suitable
 * ElevatorCar to accept it.
 * 
 * @author Kyle Moy
 *
 */
public class ElevatorController implements SimulationObject{
	private static ElevatorController instance;
	private static ArrayList<ElevatorCar> elevatorCarList;
	private static boolean[][] pendingRequests; 
	private static int elevatorSpeed; 
	private static int elevatorDoorSpeed;
	private static int timeOutSpeed;
	private static int elevators;
	
	/**
	 * Sets the ElevatorCars' movement speed.
	 * @param speed The movement speed in ticks.
	 */
	public static void setElevatorSpeed(int speed) {
		elevatorSpeed = speed;
	}
	/**
	 * Sets the ElevatorDoors' closing speed.
	 * @param speed The closing speed.
	 */
	public static void setDoorSpeed(int speed) {
		elevatorDoorSpeed = speed;
	}
	/**
	 * Set the amount of time to idle before returning to the default floor.
	 * @param time The time to wait.
	 */
	public static void setTimeOutSpeed(int time) {
		timeOutSpeed = time;
	}
	/**
	 * Set the number of elevators.
	 * @param The number of elevators.
	 */
	public static void setElevators(int e) {
		elevators = e;
	}
	/**
	 * Returns the entire list of ElevatorCars.
	 * In practice this shouldn't be necessary, since all requests can be handled by the
	 * cars themselves, or the ElevatorController. However, since we need to demonstrate
	 * functionality without Occupants, we'll need direct access to the ElevatorCars.
	 * @return The list of ElevatorCars.
	 * @see ElevatorCar
	 */
	public static ArrayList<ElevatorCar> getElevatorCarList() {
		return elevatorCarList;
	}
	/**
	 * The singleton constructor.
	 */
	private ElevatorController() {
		elevatorCarList = new ArrayList<ElevatorCar>(elevators);
		pendingRequests = new boolean[Building.getFloors()][2];
		for (int i = 0; i < elevators; i++) {
			elevatorCarList.add(new ElevatorCarImpl(i,elevatorSpeed, elevatorDoorSpeed, timeOutSpeed));
		}
	}
	/**
	 * Returns the singleton instance.
	 * @return The singleton instance.
	 */
	public static ElevatorController getInstance() {
		if (instance == null) {
			synchronized (ElevatorController.class) {
				if (instance == null) {
					instance = new ElevatorController();
				}
			}
		}
		return instance;
	}
	
	/**
	 * Makes a request for a floor in a given direction.
	 * @param floor The floor to be requested.
	 * @param direction The direction to be requested.
	 */
	public static void request(int floor, boolean direction) {
		if (floor < 0 || floor > pendingRequests.length) {
			throw new Error ("Floor request out of range");
		}
		pendingRequests[floor][direction?1:0] = true;
	}
	/**
	 * Attempts to find an idle ElevatorCar to take a request.
	 * @param floor The floor to request.
	 * @param direction The direction to request.
	 * @return
	 */
	private boolean requestIdleCar(int floor, boolean direction) {
		for (ElevatorCar e : elevatorCarList) {
			if (e.isIdle()) {
				if (e.floorRequest(floor,direction)) {
					 return true;
				}
			}
		}
		return false;
	}
	/**
	 * Attempts to find an ElevatorCar that can possibly take a request. (ie. Moving towards that floor)
	 * @param floor The floor to request.
	 * @param direction The direction to request.
	 * @return Whether the attempt succeeds (True) or fails (False)
	 */
	private boolean demandCar(int floor, boolean direction) {
		for (ElevatorCar e : elevatorCarList) {
			if (e.floorRequest(floor,direction)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public void step(SimulationObject parent) {
		
		//Query each elevator for each request
		//Bad, first elevator will get more jobs than the last
		//Should randomize order of inquiry
		//Should probably also scrap 2D Boolean array...
		for (int f = 0; f < pendingRequests.length; f ++) {
			if (pendingRequests[f][0]) {
				if (!requestIdleCar(f, false)) {
					if (demandCar(f, false)) {
						pendingRequests[f][1] = false;
					}
				} else pendingRequests[f][0] = false;
			}
			if (pendingRequests[f][1]) {
				if (!requestIdleCar(f, true)) {
					if (demandCar(f, true)) {
						pendingRequests[f][1] = false;
					}
				} else pendingRequests[f][1] = false;
			}
		}
		for (ElevatorCar e : elevatorCarList) {
			e.step(this);
		}
	}
}
