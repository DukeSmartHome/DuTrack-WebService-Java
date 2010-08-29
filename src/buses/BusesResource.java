package buses;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * 
 * @author Dean Chen
 */
public class BusesResource extends ServerResource {
	@Get
    public String represent() {
        return "hello, world";
    }
}
