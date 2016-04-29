package com.github.adepalatis.ee461.recipez;
/**
 * Created by michael on 4/20/16.
 */
import android.os.StrictMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import com.google.gson.*;

public class FoodAPI {

    private static final FoodAPI api = new FoodAPI();
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    private FoodAPI(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public static FoodAPI getInstance() {
        return api;
    }

    public List<Ingredient> searchIngredient(String query) throws IOException{
        String url = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/" +
                "ingredients/autocomplete?";

        Request req = new Request.Builder()
                .url(url + "query=" + query)
                .header("X-Mashape-Key", "splYpiM6ukmshBq11FF1vCWMOxcQp1kD1ssjsnNa56Dn5KkgfA")
                .build();

        Response res = client.newCall(req).execute();
        if (!res.isSuccessful()) throw new IOException("Error: " + res);

        Ingredient[] i = gson.fromJson(res.body().charStream(), Ingredient[].class);
        return Arrays.asList(i);
    }

    public List<RecipeSearchResult> searchRecipes(boolean ingredientLists, List<Ingredient> ingredients,
                                                  boolean limitLicense, int maxNumber, int ranking) throws Exception {

        if (!checkArguments(ingredients, ranking)) {
            throw new IllegalArgumentException();
        }

        String url = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" +
                "findByIngredients?";

        String igList = "";
        for (Ingredient i: ingredients) {
            igList += i.getName() + ",";
        }
        igList = igList.substring(0, igList.length()-1); // Remove last comma

        Request req = new Request.Builder()
                .url(url + "fillIngredients=" + ingredientLists + "&ingredients=" + igList +
                "&limitLicense=" + limitLicense + "&number=" + maxNumber + "&ranking=" + ranking)
                .header("X-Mashape-Key", "splYpiM6ukmshBq11FF1vCWMOxcQp1kD1ssjsnNa56Dn5KkgfA")
                .header("Accept", "application/json")
                .build();

        Response res = client.newCall(req).execute();
        if (!res.isSuccessful()) throw new IOException("Error: " + res);

        RecipeSearchResult[] r = gson.fromJson(res.body().string(), RecipeSearchResult[].class);
        return Arrays.asList(r);
    }

    public List<RecipeSearchResult> searchRecipes(List<String> cuisine, List<String> diet, List<Ingredient> exclude,
                                                  List<String> intolerance, Boolean limitLicense, Integer maxNumber,
                                                  Integer offset, String query, String type) throws Exception {

        if (!checkArguments(cuisine, diet, intolerance, maxNumber, offset, query, type)) {
            throw new IllegalArgumentException();
        }

        String url = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/search?";
        url += "query=" + query;

        if (cuisine != null) {
            url += "&cuisine=";
            for (String s: cuisine) {
                url += s + ",";
            }
            url = url.substring(0, url.length()-1);
        }

        if (diet != null) {
            url += "&diet=";
            for (String s: diet) {
                url += s + ",";
            }
            url = url.substring(0, url.length()-1);
        }

        if (exclude != null) {
            url += "&excludeIngredients=";
            for (Ingredient i: exclude) {
                url += i.getName() + ",";
            }
            url = url.substring(0, url.length()-1);
        }

        if (intolerance != null) {
            url += "&intolerances=";
            for (String s: intolerance) {
                url += s + ",";
            }
            url = url.substring(0, url.length()-1);
        }

        if (limitLicense != null) {
            url += "&limitLicense=" + limitLicense;
        }

        if (maxNumber != null) {
            url += "&number=" + maxNumber;
        }

        if (offset != null) {
            url += "&offset=" + offset;
        }

        if (type != null) {
            url += "&type=" + type;
        }

        Request req = new Request.Builder()
                .url(url)
                .header("X-Mashape-Key", "splYpiM6ukmshBq11FF1vCWMOxcQp1kD1ssjsnNa56Dn5KkgfA")
                .build();

        Response res = client.newCall(req).execute();
        if (!res.isSuccessful()) throw new IOException("Error: " + res);

        RecipeSearchResultWrapper r = gson.fromJson(res.body().string(), RecipeSearchResultWrapper.class);
        return r.getRecipes();
    }

    public Recipe getRecipe(Integer id, Boolean nutrition) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        String url = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id +
                "/information";

        if (nutrition != null) {
            url += "?includeNutrition=" + nutrition.toString();
        }

        Request req = new Request.Builder()
                .url(url)
                .header("X-Mashape-Key", "splYpiM6ukmshBq11FF1vCWMOxcQp1kD1ssjsnNa56Dn5KkgfA")
                .build();

        Response res = client.newCall(req).execute();
        if (!res.isSuccessful()) throw new IOException("Error: " + res);

        return gson.fromJson(res.body().string(), Recipe.class);
    }

    private boolean checkArguments(List<Ingredient> ingredients, int ranking) {
        if (ranking > 2 || ranking < 1 || ingredients == null || ingredients.isEmpty()) {
            return false;
        }

        return true;
    }

    private boolean checkArguments(List<String> cuisine, List<String> diet, List<String> intolerance,
                                   Integer maxNumber, Integer offset, String query, String type) {

        if (maxNumber != null && (maxNumber < 0 || maxNumber > 100 )) {
            return false;
        }

        if (offset != null && (offset < 0 || offset > 900)) {
            return false;
        }

        if (query == null) {
            return false;
        }

        if (cuisine != null && !cuisine.isEmpty()) {
            String valid = "african chinese japanese korean vietnamese thai indian british irish " +
                    "french italian mexican spanish middle eastern jewish american cajun southern " +
                    "greek german nordic eastern+european caribbean latin+american";

            for (String s: cuisine) {
                if (!valid.contains(s)) {
                    return false;
                }
            }
        }

        if (diet != null && !diet.isEmpty()) {
            String valid = "pescetarian lacto+vegetarian ovo+vegetarian vegan vegetarian";

            for (String s: diet) {
                if (!valid.contains(s)) {
                    return false;
                }
            }
        }

        if (intolerance != null && !intolerance.isEmpty()) {
            String valid = "dairy egg gluten peanut sesame seafood shellfish soy sulfite " +
                    "tree+nut wheat";

            for (String s: intolerance) {
                if (!valid.contains(s)) {
                    return false;
                }
            }
        }

        if (type != null) {
            String valid = "main+course side+dish dessert appetizer salad bread breakfast soup " +
                    "beverage sauce drink";

            if (!valid.contains(type)) {
                return false;
            }
        }

        return true;
    }
}
