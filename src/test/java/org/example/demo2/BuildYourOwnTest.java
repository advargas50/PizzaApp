package org.example.demo2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class BuildYourOwnTest {
    @Test
    public void price() {


    }

    @org.junit.Test
    public void testPrice() {
        PizzaFactory chicagoFactory = new ChicagoPizza();
        PizzaFactory newYorkFactory= new NYPizza();
        Pizza NYpizza = newYorkFactory.createBuildYourOwn(Size.MEDIUM);
        Pizza smallChicagopizza = chicagoFactory.createBuildYourOwn(Size.SMALL);
        Pizza largeChicagopizza = chicagoFactory.createBuildYourOwn(Size.LARGE);
        Pizza medChicagopizza = chicagoFactory.createBuildYourOwn(Size.MEDIUM);
        ArrayList<Topping> toppings1 = new ArrayList<>();
        toppings1.add(Topping.CHEDDAR);
        ArrayList<Topping> toppings2 = new ArrayList<>();
        toppings2.add(Topping.SAUSAGE);
        toppings2.add(Topping.PEPPERONI);
        ArrayList<Topping> toppings3 = new ArrayList<>();
        toppings3.add(Topping.SAUSAGE);
        toppings3.add(Topping.PEPPERONI);
        toppings3.add(Topping.PINEAPPLE);

        BuildYourOwn byoNY1 = (BuildYourOwn) NYpizza;
        byoNY1.setToppings(toppings1);
        assertEquals(12.68, byoNY1.price());

        BuildYourOwn byoChic1 = (BuildYourOwn) smallChicagopizza;
        byoChic1.setToppings(toppings2);
        assertEquals(12.37, byoChic1.price());

        BuildYourOwn byoChic2 = (BuildYourOwn) largeChicagopizza;
        byoChic2.setToppings(toppings3);
        assertEquals(18.06, byoChic2.price());

        BuildYourOwn byoChic3 = (BuildYourOwn) medChicagopizza;
        assertEquals(10.99, byoChic3.price());

        BuildYourOwn byoChic4 = (BuildYourOwn) largeChicagopizza;
        byoChic2.setToppings(toppings1);
        assertEquals(14.68, byoChic4.price());
    }
}