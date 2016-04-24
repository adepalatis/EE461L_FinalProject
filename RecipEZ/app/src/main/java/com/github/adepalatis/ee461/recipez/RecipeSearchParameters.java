package com.github.adepalatis.ee461.recipez;

import java.util.List;

/**
 * Created by michael on 4/24/16.
 */
public class RecipeSearchParameters {
    boolean ingredientLists = true; // Show used and missing ingredients in results
    boolean limitLicense = true; // show with proper attribution
    int maxNumber = 10; // Max number of results
    int offset = 0; // Number of results to skip
    int ranking = 1; // 1 = maximize used ingredients, 2 = minimize missing ingredients
    String query = null; // Recipe search query
    String type = null; // The type of the recipe, i.e main course
    List<Ingredient> ingredients = null;
    List<Ingredient> excludeIngredients = null;
    List<String> cuisine = null;
    List<String> diet = null;
    List<String> intolerance = null;

    public RecipeSearchParameters(String query) {
        this.query = query;
    }

    public RecipeSearchParameters(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
