package application;

import restResources.buses.BusesResource;
import restResources.messages.MessagesResource;
import restResources.routes.RoutesResource;
import restResources.routes.WayPointsResource;

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
        router.attach("/" + Source.BUS, BusesResource.class);
        router.attach("/" + Source.BUS + "/{busId}", BusesResource.class);
        
        router.attach("/" + Source.MESSAGE, MessagesResource.class);
        router.attach("/" + Source.MESSAGE + "/{messageId}", MessagesResource.class);
        
        router.attach("/" + Source.ROUTE, RoutesResource.class);
        router.attach("/" + Source.ROUTE + "/{routeId}", RoutesResource.class);
        router.attach("/" + Source.ROUTE + "/{routeId}/busLocations", BusesResource.class);
        router.attach("/" + Source.ROUTE + "/{routeId}/waypoints", WayPointsResource.class);
 
        return router;
    }
}
