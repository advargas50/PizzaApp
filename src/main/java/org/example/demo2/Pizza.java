package org.example.demo2;

import java.util.ArrayList;

/**
 * The pizza class which is the parent of the other pizza styles and has the basics of creating a pizza
 * @author Maxim Trofimchuk, Angel Vargas
 */
public abstract class Pizza {
    private ArrayList<Topping> toppings;
    private Crust crust;
    private Size size;

    /**
     * The parameterized constructor for the pizza class
     * @param size the size of the pizza
     * @param crust the type of crust
     * @param toppings the array of toppings on the pizza
     */
    public Pizza(Size size, Crust crust, ArrayList<Topping> toppings) {
        this.size = size;
        this.crust = crust;
        this.toppings = toppings;
    }

    /**
     * Returns the pizza's pre-set toppings
     * @return returns an arraylist of toppings
     */
    public ArrayList<Topping> getToppings(){
        return toppings;
    }

    /**
     * abstract class that returns the price
     * @return price of the pizza
     */
    public abstract double price();

    /**
     * turns the pizza into a string
     * @return the string of the pizza
     */
    public String toString() {
        return " Pizza (" + size + ", " + crust + ") with toppings " + toppings;
    }

}