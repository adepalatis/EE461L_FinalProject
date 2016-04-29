package com.github.adepalatis.ee461.recipez;

import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class FoodAPITest {

    @Test
    public void testSearchIngredient() throws Exception {
        FoodAPI api = FoodAPI.getInstance();
        List<Ingredient> res = api.searchIngredient("onion");
        for (Ingredient i: res) {
            Assert.assertTrue(i.getName() != null && !i.getName().equals(""));
        }
    }

    @Test
    public void testSearchRecipeWithIngredientsMax2() throws Exception {
        FoodAPI api = FoodAPI.getInstance();

        List<Ingredient> i = new ArrayList<Ingredient>() {{
            add(new Ingredient("Apple"));
            add(new Ingredient("Carrot"));
        }};

        List<RecipeSearchResult> res = api.searchRecipes(true, i, false, 2, 1);
        Assert.assertTrue(res.size() <= 2);
        for (RecipeSearchResult r: res) {
            Assert.assertTrue(r.id != null && r.id >= 0);
        }
    }

    @Test
    public void testSearchRecipeWithQueryMax2() throws Exception {
        FoodAPI api = FoodAPI.getInstance();
        List<RecipeSearchResult> res = api.searchRecipes(null, null, null, null, null, 2, null, "taco", null);
        Assert.assertTrue(res.size() <= 2);
        for (RecipeSearchResult r: res) {
            Assert.assertTrue(r.id != null && r.id >= 0);
        }
    }

    @Test
    public void getRecipe() throws Exception {
        FoodAPI api = FoodAPI.getInstance();
        List<RecipeSearchResult> res = api.searchRecipes(null, null, null, null, null, 2, null, "pasta", null);
        Recipe r = api.getRecipe(res.get(0).id, true);
        Assert.assertTrue(r != null && r.id >= 0 && r.id.equals(res.get(0).id));
    }
}