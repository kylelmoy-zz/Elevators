package simulationObjects;


/**
 * A basic implementation of a CallBox.
 * 
 * @author Kyle Moy
 *
 */
public class CallBoxImpl {
	private ElevatorController elevatorController = ElevatorController.getInstance();
	private int currentFloor;
	/**
	 * Default constructor.
	 * @param floor The floor this box belongs to.
	 */
	public CallBoxImpl(int floor) {
		currentFloor = floor;
	}
	public void requestUp() {
		ElevatorController.request(currentFloor, true);
	}
	public void requestDown() {
		ElevatorController.request(currentFloor, false);
	}
}
