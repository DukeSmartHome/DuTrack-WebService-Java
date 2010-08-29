package application;

import messages.MessagesResource;
import routes.RoutesResource;
import buses.BusesResource;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * Creates a root Restlet that will receive all incoming calls.
 * @author Dean Chen
 */
public class RestApplication extends Application {
	
    @Override
    public synchronized Restlet createInboundRoot() {
    	
        Router router = new Router(getContext());
 
        // Define routes
        router.attach("/buses", BusesResource.class);
        router.attach("/messages", MessagesResource.class);
        router.attach("/routes", RoutesResource.class);
 
        return router;
    }
}
