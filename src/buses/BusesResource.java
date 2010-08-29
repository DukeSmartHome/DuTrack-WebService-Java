package buses;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * 
 * @author Dean Chen
 */
public class BusesResource extends ServerResource {
	@Get
    public Representation represent() {
		String source = getReference().getSegments().get(1);
		return null;
    }
}
