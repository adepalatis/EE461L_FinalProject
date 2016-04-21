package com.github.adepalatis.ee461.recipez;

/**
 * Created by michael on 4/20/16.
 */
public class Ingredient {
    public String aisle = null;
    public String name = null;
    public Double amount = null;
    public String unit = null;
    public String originalString = null;
    public String image = null;

    public Ingredient() {}

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        if (name != null) {
            return name;
        }
        return "";
    }

    @Override
    public String toString() {
        String s = "{\n";

        if (aisle != null) {
            s += "\taisle: " + aisle + "\n";
        }

        if (name != null) {
            s += "\tname: " + name + "\n";
        }

        if (amount != null) {
            s += "\tamount: " + amount + "\n";
        }

        if (unit != null) {
            s += "\tunit: " + unit + "\n";
        }

        if (originalString != null) {
            s += "\toriginalString: " + originalString + "\n";
        }

        if (image != null) {
            s += "\timage: " + image + "\n";
        }

        return s + "}";
    }
}
