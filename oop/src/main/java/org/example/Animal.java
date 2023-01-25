package org.example;

public class Animal {

    private String name;
    private double weight;
    private double height;

    public Animal() {

    }

    public Animal(String name, double weight, double height) {
        this.name = name;
        this.weight = weight;
        this.height = height;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                '}';
    }
}
