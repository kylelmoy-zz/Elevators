package simulationObjects;


/**
 * A basic door.
 * A basic door that remains open for an amount of time.
 * 
 * @author Kyle Moy
 *
 */
public class ElevatorDoor implements SimulationObject{
	private boolean isOpen = false;
	private long openDuration;
	public ElevatorDoor() {}
	/**
	 * Opens the door for a duration.
	 * @param dur The duration.
	 */
	public void open(int dur) {
		isOpen = true;
		openDuration = dur;
	}
	/**
	 * Returns if the door is currently open.
	 * @return If the door is currently open.
	 */
	public boolean isOpen() {
		return isOpen;
	}
	@Override
	public void step(SimulationObject self) {
		if (isOpen) {
			openDuration --;
			if (openDuration == 0) {
				isOpen = false;
			}
		}
	}
}
