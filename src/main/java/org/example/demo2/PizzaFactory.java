package org.example.demo2;

/**
 * This an interface class used by Chicago Pizza and NY Pizza for creating the different styles of pizza
 * in what's known as an abstract factory
 * @author Maxim Trofimchuk, Angel Vargas
 */
public interface PizzaFactory {
    /**
     * Creates a deluxe style pizza
     * @param size the size of the pizza
     * @return a pizza of the deluxe style and given size
     */
    Pizza createDeluxe(Size size);

    /**
     * Creates a meatzza style pizza
     * @param size the size of the pizza
     * @return a pizza of the meatzza style and given size
     */
    Pizza createMeatzza(Size size);

    /**
     * Creates a BBQChicken style pizza
     * @param size the size of the pizza
     * @return a pizza of the BBQChicken style and given size
     */
    Pizza createBBQChicken(Size size);

    /**
     * Creates a build your own style pizza
     * @param size the size of the pizza
     * @return a pizza of the build your own style pizza with given size
     */
    Pizza createBuildYourOwn(Size size);
}
