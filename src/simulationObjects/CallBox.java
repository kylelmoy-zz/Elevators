package simulationObjects;

/**
 * A call box that can request floors.
 * The CallBox can include one or more buttons, allowing Occupants
 * to request an elevator in a given direction.
 * @author Kyle Moy
 *
 */
public interface CallBox {
	/**
	 * Requests an elevator to this floor going up.
	 */
	public void requestUp();
	/**
	 * Requests and elevator to this floor going down.
	 */
	public void requestDown();
}
