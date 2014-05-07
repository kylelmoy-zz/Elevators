package locations;
import simulationObjects.CallBox;
import simulationObjects.Occupant;
import simulationObjects.SimulationObject;

/**
 * A floor that contains Occupants and a CallBox.
 * 
 * @author Kyle Moy
 *
 */
public class Floor extends Location implements SimulationObject {
	private CallBox callBox;
	private int floor;
	/**
	 * Default constructor.
	 * @param floor The number of this floor.
	 */
	public Floor (int floor) {
		this.floor = floor;
	}
	/**
	 * Returns this floor's CallBox,
	 * @return This floor's CallBox.
	 */
	public CallBox getCallBox() {
		return callBox;
	}
	@Override
	public void step(SimulationObject self) {
		for (Occupant o : occupantList) {
			o.step(this);
		}
	}
}
