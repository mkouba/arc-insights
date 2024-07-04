package org.acme;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HelloService {

    // @Inject is not needed in Quarkus if a CDI qualifier is used
    @ConfigProperty(name = "hello")
    String hello;

    @PostConstruct // This method is called after a new bean instance is created
    void init() {
        Log.info("Initialized!");
    }

    public String createHelloMessage(String name) {
        return "%s %s!".formatted(hello, name);
    }

}
