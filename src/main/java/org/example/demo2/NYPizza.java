package org.example.demo2;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class uses the Pizza Factory interface to create specific pizzas of New York style
 * @author Maxim Trofimchuk, Angel Vargas
 */
public class NYPizza implements PizzaFactory {

    /**
     * The function creates a meatzza style pizza with all requirements
     * @param size the size of the pizza
     * @return the pizza of the given style and size with the proper toppings and crust
     */
    @Override
    public Pizza createMeatzza(Size size) {
        Crust crust=Crust.HANDTOSSED;

        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.HAM);
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.BEEF);
        return new Meatzza(size, crust, toppings);
    }

    /**
     * The function creates a deluxe style pizza with all requirements
     * @param size the size of the pizza
     * @return the pizza of the given style and size with the proper toppings and crust
     */
    @Override
    public Pizza createDeluxe(Size size) {
        Crust crust= Crust.BROOKLYN;

        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.ONION);
        toppings.add(Topping.MUSHROOM);
        return new Deluxe(size, crust, toppings);
    }

    /**
     * The function creates a BBQ chicken style pizza with all requirements
     * @param size the size of the pizza
     * @return the pizza of the given style and size with the proper toppings and crust
     */
    @Override
    public Pizza createBBQChicken(Size size) {
        Crust crust= Crust.THIN;
        ArrayList<Topping> toppings = new ArrayList<>();
        toppings.add(Topping.BBQ_CHICKEN);
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.PROVOLONE);
        toppings.add(Topping.CHEDDAR);
        return new BBQChicken(size, crust, toppings);
    }

    /**
     * The function creates a build your own style pizza with all requirements
     * @param size the size of the pizza
     * @return the pizza of the given style and size with the proper toppings and crust
     */
    @Override
    public Pizza createBuildYourOwn(Size size) {
        Crust crust= Crust.HANDTOSSED;
        ArrayList<Topping> toppings = new ArrayList<>();
        return new BuildYourOwn(size, crust, toppings);
    }
}
