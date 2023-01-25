package org.factory;

public class Factory {

    public Vehicle checkInstance(String value) {
        if (value.equalsIgnoreCase("car")) {
            return new Car();
        } else if (value.equalsIgnoreCase("plane")) {
            return new Plane();
        } else if (value.equalsIgnoreCase("boat")) {
            return new Boat();
        } else {
            return null;
        }
    }

}
