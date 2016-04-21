package com.github.adepalatis.ee461.recipez;

/**
 * Created by michael on 4/20/16.
 */
public class Nutrient {
    public String title = null;
    public Double amount = null;
    public String unit = null;
    public Double percentOfDailyNeeds = null;

    public Nutrient(){}

    @Override
    public String toString() {
        String s = "{\n";

        if (title != null) {
            s += "\ttitle: " + title + "\n";
        }

        if (amount != null) {
            s += "\tamount: " + amount + "\n";
        }

        if (unit != null) {
            s += "\tunit: " + unit + "\n";
        }

        if (percentOfDailyNeeds != null) {
            s += "\tpercentOfDailyNeeds: " + percentOfDailyNeeds + "\n";
        }

        return s + "}";
    }
}
