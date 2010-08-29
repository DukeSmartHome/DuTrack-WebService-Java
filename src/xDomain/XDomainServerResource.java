package xDomain;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Options;
import org.restlet.resource.ServerResource;

public class XDomainServerResource extends ServerResource {
	@Options
	public void doOptions(Representation entity) {
	    Form responseHeaders = (Form) getResponse().getAttributes().get("org.restlet.http.headers");
	    if (responseHeaders == null) {
	        responseHeaders = new Form();
	        getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
	    }
	    responseHeaders.add("Access-Control-Allow-Origin", "*");
	    responseHeaders.add("Access-Control-Allow-Methods", "GET,OPTIONS");
	    responseHeaders.add("Access-Control-Allow-Headers", "Content-Type");
	    responseHeaders.add("Access-Control-Allow-Headers", "x-requested-with");
	    responseHeaders.add("Access-Control-Allow-Credentials", "false");
	    responseHeaders.add("Access-Control-Max-Age", "60");
	}
}
