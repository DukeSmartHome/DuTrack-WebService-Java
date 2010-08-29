package restResources.buses;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import xDomain.XDomainServerResource;

/**
 * 
 * @author Dean Chen
 */
public class BusesResource extends XDomainServerResource {
	@Get
    public Representation represent() {
		String source = getReference().getSegments().get(1);
		return null;
    }
	
	@Post
	public void post() {
		
	}
}
