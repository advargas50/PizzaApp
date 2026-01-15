package org.example.demo2;

/**
 * The toppings class which includes all the toppings available to put on a pizza
 * @author Maxim Trofimcuk, Angel Vargas
 */
public enum Topping {
    PEPPERONI("PEPPERONI"),
    SAUSAGE("SAUSAGE"),
    GREEN_PEPPER("GREEN-PEPPER"),
    PROVOLONE("PROVOLONE"),
    BBQ_CHICKEN("BBQ-CHICKEN"),
    BEEF("BEEF"),
    HAM("HAM"),
    CHEDDAR("CHEDDAR"),
    MUSHROOM("MUSHROOM"),
    ONION("ONION"),
    PINEAPPLE("PINEAPPLE"),
    MEATBALL("MEATBALL"),
    BROCCOLI("BROCCOLI");


    private final String type;

    /**
     * the constructor of the topping class
     * @param type the topping
     */
    Topping(String type) {
        this.type=type;
    }

    /**
     * returns the topping
     * @return returns topping type
     */
    public String getType() {
        return type;
    }


}
