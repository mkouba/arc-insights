package org.acme.extension;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("extension")
public class GreetingResource {

    // Inject UndiscoveredBean with a CDI qualifier; note that the qualifier value is non-binding
    @Inject
    @MyQualifier(name = "any")
    UndiscoveredBean undiscoveredBean;

    // Inject a synthetic bean
    @Inject
    @MyQualifier(name = "synthetic")
    String stringBean;

    @GET
    public String hello() {
        return undiscoveredBean.sayHello(stringBean);
    }
}
