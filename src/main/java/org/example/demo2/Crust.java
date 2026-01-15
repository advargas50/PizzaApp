package org.example.demo2;

/**
 * An enum class of the possible crusts on the pizza which is determined by the style and type of the pizza
 * @author Angel Vargas, Maxim Trofimchuk
 */
public enum Crust {
    BROOKLYN("BROOKLYN"),
    HANDTOSSED("HAND-TOSSED"),
    PAN("PAN"),
    STUFFED("STUFFED"),
    DEEPDISH("DEEPDISH"),
    THIN("THIN");

    private final String type;

    /**
     * The constructor of the crust class
     * @param type the type of crust
     */
    Crust(String type) {
        this.type=type;
    }

    /**
     * returns the crust type
     * @return the type
     */
    public String getType() {
        return type;
    }


}
