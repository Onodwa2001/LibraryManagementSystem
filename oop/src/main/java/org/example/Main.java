package org.example;

public class Main {
    public static void main(String[] args) {

        Dog d = new Dog("xxx", 3, 5);

        System.out.println(d.getName());

        LivingThing v = () -> {
            System.out.println("hj");
            System.out.println("jnk");
        };

        v.breathe();

    }
}