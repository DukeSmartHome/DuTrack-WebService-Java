package xDomain;

import org.restlet.data.Form;
import org.restlet.resource.ServerResource;

public class XDomain {
	
	public static void allowAllOrigins(ServerResource resource) {
		Form responseHeaders = (Form) resource.getResponse().getAttributes().get("org.restlet.http.headers");
	    if (responseHeaders == null) {
	        responseHeaders = new Form();
	        resource.getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
	    }
	    responseHeaders.add("Access-Control-Allow-Origin", "*");
	}
}
