package com.github.adepalatis.ee461.recipez;

/**
 * Created by michael on 4/20/16.
 */
public class RecipeSearchResult {
    public Integer id = null;
    public String title = null;
    public String image = null;
    public String[] imageUrls = null;
    public String imageType = null;
    public Integer readyInMinutes = null;
    public Integer usedIngredientCount = null;
    public Integer missedIngredientCount = null;
    public Ingredient[] missedIngredients = null;
    public Ingredient[] usedIngredients = null;
    public Integer likes = null;

    public RecipeSearchResult() {}

    @Override
    public String toString() {

        String s = "{\n";

        if (id != -1) {
            s += "\tid: " + id + "\n";
        }

        if (title != null) {
            s += "\ttitle: " + title + "\n";
        }

        if (image != null) {
            s += "\timage: " + image + "\n";
        }

        if (imageUrls != null) {
            s += "\timageUrls: [";
            for (String i: imageUrls) {
                s += i + ", ";
            }
            s = s.substring(0, s.length()-2) + "]\n";
        }

        if (imageType != null) {
            s += "\timageType: " + imageType + "\n";
        }

        if (readyInMinutes != null) {
            s += "\treadyInMinutes: " + readyInMinutes + "\n";
        }

        if (usedIngredientCount != null) {
            s += "\tusedIngredientCount: " + usedIngredientCount + "\n";
        }

        if (missedIngredientCount != null) {
            s += "\tmissedIngredientCount: " + missedIngredientCount + "\n";
        }

        if (missedIngredients != null) {
            s += "\tmissedIngredients: ";
            for (Ingredient i: missedIngredients) {
                s += i + ", ";
            }
            s = s.substring(0, s.length()-2) + "\n";
        }

        if (usedIngredients != null) {
            s += "\tusedIngredients: ";
            for (Ingredient i: usedIngredients) {
                s += i + ", ";
            }
            s = s.substring(0, s.length()-2) + "\n";
        }

        if (likes != null) {
            s += "\tlikes: " + likes + "\n";
        }

        return s + "}";
    }
}
