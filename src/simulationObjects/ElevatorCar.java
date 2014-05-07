package simulationObjects;


/**
 * An ElevatorCar is a SimulationObject that can receive floor requests.
 * 
 * @author Kyle Moy
 * @see SimulationObject
 */
public interface ElevatorCar extends SimulationObject{
	
	/**
	 * An internal floor request (ie. an Occupant requests a floor from inside the elevator)
	 * @param floor The floor to be requested.
	 */
	public void floorRequest(int floor);
	
	/**
	 * An external floor requests (ie. from the ElevatorController)
	 * @param floor The floor to be requested.
	 * @param direction The direction in which to be requested (True up, False down)
	 * @return True if the request is accepted, false otherwise.
	 */
	public boolean floorRequest(int floor, boolean direction);
	/**
	 * Returns the idle state of the ElevatorCar
	 * @return True if the ElevatorCar has no tasks in the forseeable future. Otherwise, false.
	 */
	public boolean isIdle();
}
