package org.example.demo2;

import java.util.ArrayList;

/**
 * A subclass of pizza that creates a pizza of meatzza style, either Chicago or New York
 * @author Maxim Trofimchuk, Angel vargas
 */
public class Meatzza extends Pizza {
    private ArrayList<Topping> toppings;
    private Crust crust;
    private Size size;

    /**
     * The parameterized constructor of the meatzza pizza
     * @param size size of the pizza
     * @param crust crust type of the pizza
     * @param toppings array of toppings on the pizza
     */
    public Meatzza(Size size, Crust crust, ArrayList<Topping> toppings) {
        super(size, crust, toppings);
        this.size=size;
        this.toppings=toppings;
        this.crust=crust;
    }

    /**
     * calculates the price of the pizza based on size
     * @return the price of the pizza as a double
     */
    @Override
    public double price() {
        switch (size.getType()){
            case "SMALL":
                return 17.99;
            case "MEDIUM":
                return 19.99;
            default:
                return 21.99;
        }
    }

    /**
     * makes the pizza into a string
     * @return the string of the pizza
     */
    @Override
    public String toString() {
        return " Meatzza (" + size + ", " + crust + ") with toppings " + toppings;
    }
}
