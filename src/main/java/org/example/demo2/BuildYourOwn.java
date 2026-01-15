package org.example.demo2;

import java.util.ArrayList;

/**
 * A subclass of pizza that creates a build your own pizza, either of Chicago or New York style
 * @author Angel Vargas, Maxim Trofimchuk
 */
public class BuildYourOwn extends Pizza {
    private static final int MAX_TOPPINGS = 7;
    private static final double TOPPING_COST = 1.69;
    private ArrayList<Topping> toppings;
    private Crust crust;
    private Size size;

    /**
     * The parameterized constructor of the build your own pizza
     * @param size size of the pizza
     * @param crust crust type of the pizza
     * @param toppings array of toppings on the pizza
     */
    public BuildYourOwn(Size size, Crust crust, ArrayList<Topping> toppings) {
        super(size, crust, toppings);
        this.size = size;
        this.toppings=toppings;
        this.crust=crust;

    }

    /**
     * sets the toppings of pizza
     * @param toppings customized toppings of the pizza
     */
    public void setToppings(ArrayList<Topping> toppings){
        this.toppings = toppings;
    }

    /**
     * calculates the price of the pizza based on size
     * @return the price of the pizza as a double
     */
    @Override
    public double price() {
        switch (size.getType()){
            case "LARGE":
                return Math.round((12.99 + (toppings.size() * TOPPING_COST)) * 100.0 ) / 100.0 ;
            case "MEDIUM":
                return Math.round((10.99 + (toppings.size() * TOPPING_COST)) * 100.0 ) / 100.0;
            default:
                return Math.round((8.99 + (toppings.size() * TOPPING_COST)) * 100.0 ) / 100.0;
        }

    }

    /**
     * makes the pizza into a string
     * @return the string of the pizza
     */
    @Override
    public String toString() {
        return "Build Your Own Pizza (" + size + ", " + crust + ", Toppings: " + toppings + ")";
    }

}
