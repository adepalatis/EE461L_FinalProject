package com.github.adepalatis.ee461.recipez;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class FoodAPITest {

    @Test
    public void searchIngredient() throws Exception {
        FoodAPI api = FoodAPI.getInstance();
        List<Ingredient> res = api.searchIngredient("onion");
        System.out.println(res);
    }

    @Test
    public void searchRecipeWithIngredients() throws Exception {
        FoodAPI api = FoodAPI.getInstance();

        List<Ingredient> i = new ArrayList<Ingredient>() {{
            add(new Ingredient("Apple"));
            add(new Ingredient("Carrot"));
        }};

        List<RecipeSearchResult> res = api.searchRecipes(true, i, false, 2, 1);
        System.out.println(res);
    }

    @Test
    public void searchRecipeWithQuery() throws Exception {
        FoodAPI api = FoodAPI.getInstance();
        List<RecipeSearchResult> res = api.searchRecipes(null, null, null, null, null, 2, null, "taco", null);
        System.out.println(res);
    }

    @Test
    public void getRecipe() throws Exception {
        FoodAPI api = FoodAPI.getInstance();
        List<RecipeSearchResult> res = api.searchRecipes(null, null, null, null, null, 2, null, "pasta", null);
        Recipe r = api.getRecipe(res.get(0).id, true);
        System.out.println(r);
    }
}