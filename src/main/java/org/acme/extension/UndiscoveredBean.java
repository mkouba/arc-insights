package org.acme.extension;

/**
 * This class has no bean defining annotation and is therefore not discovered as a bean.
 * We will use {@link CdiExtension} to transform it into a bean with {@link jakarta.enterprise.context.ApplicationScoped}
 * and {@link MyQualifier}.
 */
public class UndiscoveredBean {

    public String sayHello(String s) {
        return "Hello from " + s;
    }
}
