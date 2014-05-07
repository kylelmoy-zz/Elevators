package simulationObjects;

import java.util.ArrayList;

import locations.Floor;



/**
 * The Building houses the floors and ElevatorController.
 * The Building sets up all other elements of the simulation,
 * and is where the simulation can be controlled from.
 * 
 * @author Kyle Moy
 *
 */
public class Building implements SimulationObject {

	private static Building instance;
	private static ElevatorController elevatorController;
	private static ArrayList<Floor> floorList;
	private static int floors;
	private static long time = -1;
	
	/**
	 * Default constructor.
	 */
	private Building () {
		floorList = new ArrayList<Floor>(floors);
		elevatorController = ElevatorController.getInstance();
		for (int i = 0; i < floors; i++) {
			floorList.add(new Floor(i));
		}
	}
	/**
	 * Set the number of floors in the Building.
	 * @param numfloors The number of floors.
	 */
	public static void setFloors(int numFloors) {
		floors = numFloors;
	}
	/**
	 * Returns the number of floors.
	 * @return The number of floors.
	 */
	public static int getFloors() {
		return floors;
	}
	/**
	 * Returns the current time in ticks.
	 * @return The current time.
	 */
	public static long currentTime() {
		return time;
	}
	/**
	 * Returns the singleton instance.
	 * @return The singleton instance.
	 */
	public static Building getInstance() {
		if (instance == null) {
			synchronized (Building.class) {
				if (instance == null) {
					instance = new Building();
				}
			}
		}
		return instance;
	}
	@Override
	public void step(SimulationObject self) {
		time += 1;
		for (Floor f : floorList) {
			f.step(this);
		}
		elevatorController.step(this);
	}
	/**
	 * Step passthrough.
	 */
	public void step() {
		step(this);
	}
}
