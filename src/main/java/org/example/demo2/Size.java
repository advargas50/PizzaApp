package org.example.demo2;

/**
 * An enum class of the possible sizes of the pizza
 * @author Maxim Trofimchuk, Angel Vargas
 */
public enum Size {
    SMALL("SMALL"),
    MEDIUM("MEDIUM"),
    LARGE("LARGE");

    private final String size;

    /**
     * the constructor of the size class
     * @param size the size of the pizza as a string
     */
    Size(String size) {
        this.size=size;
    }

    /**
     * returns the size of the pizza
     * @return the size
     */
    public String getType() {
        return size;
    }


}
