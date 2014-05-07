package locations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import simulationObjects.Occupant;


/**
 * A location allows Occupants to enter and leave.
 * 
 * @author Kyle Moy
 *
 */
public class Location {
	protected Set<Occupant> occupantList = Collections.synchronizedSet(new HashSet<Occupant>());
	/**
	 * Removes an Occupant from this Location
	 * @param self The Occupant to remove.
	 */
	public void leave(Occupant self){
		if (occupantList.contains(self)) {
			occupantList.remove(self);
		} else {
			throw new Error("Cannot leave location: not at location");
		}
	}
	/**
	 * Adds an Occupant to this Location
	 * @param self The Occupant to add.
	 */
	public void join(Occupant self) {
		if (occupantList.contains(self)) {
			throw new Error("Cannot join location: already at location");
		} else {
			occupantList.add(self);
		}
	}
}
