package org.acme;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * This bean is considered "unused" and therefore no metadata class is
 * generated. However, the class is still part of the runtime.
 * <p>
 * See <a href=
 * "https://quarkus.io/guides/cdi-reference#remove_unused_beans">Removing Unused
 * Beans</a> for more information.
 */
@ApplicationScoped
public class ByeService {

    @ConfigProperty(name = "bye")
    String bye;

    @PostConstruct
    void init() {
        Log.info("Initialized!");
    }

    public String createByeMessage(String name) {
        return "%s %s!".formatted(bye, name);
    }

}
