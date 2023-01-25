package org.factory;

public class Car implements Vehicle {

    @Override
    public String start() {
        return "Driving...";
    }

}
