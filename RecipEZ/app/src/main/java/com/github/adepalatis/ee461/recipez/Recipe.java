package com.github.adepalatis.ee461.recipez;

/**
 * Created by michael on 4/20/16.
 */
public class Recipe {
    public Integer servings = null;
    public String sourceUrl = null;
    public String spoonacularSourceUrl = null;
    public Integer aggregateLikes = null;
    public String sourceName = null;
    public Ingredient[] extendedIngredients = null;
    public Integer id = null;
    public String title = null;
    public Integer readyInMinutes = null;
    public String image = null;
    public Nutrition nutrition = null;

    public Recipe(){}

    @Override
    public String toString() {
        String s = "{\n";

        if (servings != null) {
            s += "\tservings: " + servings + "\n";
        }

        if (sourceUrl != null) {
            s += "\tsourceURL: " + sourceUrl + "\n";
        }

        if (spoonacularSourceUrl != null) {
            s += "\tspoonacularSourceURL: " + spoonacularSourceUrl + "\n";
        }

        if (aggregateLikes != null) {
            s += "\taggregateLikes " + aggregateLikes + "\n";
        }

        if (sourceName != null) {
            s += "\tsourceName: " + sourceName + "\n";
        }

        if (sourceUrl != null) {
            s += "\textendedIngredients: [";
            for (Ingredient i: extendedIngredients) {
                s += i + ", ";
            }
            s = s.substring(0, s.length()-2) + "]\n";
        }

        if (id != null) {
            s += "\tid: " + id + "\n";
        }

        if (title != null) {
            s += "\ttitle: " + title + "\n";
        }

        if (readyInMinutes != null) {
            s += "\treadyInMinutes: " + readyInMinutes + "\n";
        }

        if (image != null) {
            s += "\timage: " + image + "\n";
        }

        if (nutrition != null) {
            s += "\tnutrition: " + nutrition + "\n";
        }

        return s + "}";
    }
}
