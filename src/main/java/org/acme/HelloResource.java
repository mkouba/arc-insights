package org.acme;

import org.jboss.resteasy.reactive.RestQuery;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("hello")
public class HelloResource {

    @Inject
    HelloService helloService;

    @GET
    public String hello(@RestQuery String name) {
        return helloService.createHelloMessage(name);
    }

}