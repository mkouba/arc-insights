package org.acme;

// Missing bean defining annotation!
public class NotABean {
    
    public String createHelloMessage(String name) {
        return name.toLowerCase();
    }

}
