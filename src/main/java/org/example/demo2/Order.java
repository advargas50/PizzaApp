package org.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The order class holds all the pizzas in the order and increments in numbers for each subsequent order
 * @author Maxim Trofimchuk, Angel Vargas
 */
public class Order {
    private int number;
    private ObservableList<Pizza> pizzas;
    static final Double TAX_RATE=0.06625;

    /**
     * The constructor of the order class with the order number and the array of pizzas
     */
    public Order() {
        this.number = 0;
        this.pizzas = FXCollections.observableArrayList();

    }

    /**
     * adds a pizza to the order
     * @param pizza the pizza to be added
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    /**
     * removes a pizza from the order
     * @param pizza the pizza to be removed
     */
    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
    }

    /**
     * finds the total price of all the pizzas in the order
     * @return the total price
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        for (Pizza pizza : pizzas) {
            total += pizza.price();
        }
        return total;
    }

    /**
     * returns as a string, all the pizzas in the array and includes the price with and without tax
     * @return the string with all relevant information
     */
    @Override
    public String toString() {
        double totalPrice=calculateTotalPrice();
        StringBuilder orderDetails = new StringBuilder("Order " + number + " Details:\n");
        for (Pizza pizza : pizzas) {
            orderDetails.append(pizza).append("\n");
        }
        orderDetails.append("  Price before tax: $").append(String.format("%.2f", totalPrice));
        orderDetails.append("  Sales Tax: $").append(String.format("%.2f", totalPrice * TAX_RATE));
        orderDetails.append("  Total Price: $").append(String.format("%.2f", totalPrice * (1+TAX_RATE)));
        return orderDetails.toString();
    }

    /**
     * gets the order number
     * @return the order number
     */
    public int getNumber() {
        return number;
    }

    /**
     * sets the order number
     * @param number the order number modification
     */
    public void setNumber(int number){
        this.number = number;
    }

    /**
     * returns the array of pizzas
     * @return the pizzas array
     */
    public ObservableList<Pizza> getPizzas() {
        return pizzas;
    }
}
