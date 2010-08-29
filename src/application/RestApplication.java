package application;

import messages.MessagesResource;
import routes.RoutesResource;
import routes.WayPointsResource;
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
        router.attach("/buses/{busId}", BusesResource.class);
        
        router.attach("/messages", MessagesResource.class);
        router.attach("/messages/{messageId}", MessagesResource.class);
        
        router.attach("/routes", RoutesResource.class);
        router.attach("/routes/{routeId}", RoutesResource.class);
        router.attach("/routes/{routeId}/buses", BusesResource.class);
        router.attach("/routes/{routeId}/waypoints", WayPointsResource.class);
 
        return router;
    }
}
