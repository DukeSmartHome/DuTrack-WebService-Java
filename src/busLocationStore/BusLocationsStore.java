package busLocationStore;

import java.util.HashMap;

/**
 * Save the bus locations directly in Java HashMap to reduce lookup time
 * 
 * @author Dean Chen
 *
 */
public class BusLocationsStore {
	public static HashMap<Integer , BusLocation> busLocationsStore;
	
	static {
		busLocationsStore = new HashMap<Integer, BusLocation>();
	}
	
	public void updateLocation(int busId, BusLocation busLocation) {
		busLocationsStore.put(busId, busLocation);
	}
	
	public BusLocation getLocation (int busId) {
		return busLocationsStore.get(busId);
	}
}
