package simulationObjects;

/**
 * A simulation object exists in time.
 * A simulation object can step a unit of time and execute behaviors.
 * @author Kyle Moy
 *
 */
public interface SimulationObject {
	/**
	 * Act one unit of time.
	 * @param parent The object that called the step.
	 */
	//Parent param possibly unnecessary due to changes in how stepping works.
	//The plan was to delegate the method to a Stepper object with overloaded step methods
	//which would handle the different SimulationObject behaviors. However, it seems
	//that wouldn't make much sense as the object I'd delegate to wouldn't have information
	//about the SimulationObject that called it.
	public void step(SimulationObject parent);
}
