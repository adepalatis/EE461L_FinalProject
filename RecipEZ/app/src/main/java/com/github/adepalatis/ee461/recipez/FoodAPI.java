package com.github.adepalatis.ee461.recipez;
/**
 * Created by michael on 4/20/16.
 */
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

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

    public void httpGetRequest(String urlWithParams, Callback c) {
        Request req = new Request.Builder()
                .url(urlWithParams)
                .header("X-Mashape-Key", "splYpiM6ukmshBq11FF1vCWMOxcQp1kD1ssjsnNa56Dn5KkgfA")
                .header("Accept", "application/json")
                .build();

        client.newCall(req).enqueue(c);
        Log.d("App", "Called HTTP GET on url: " + urlWithParams);
    }

    public void searchIngredient(String query, Callback c) throws IOException{
        String url = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/" +
                "ingredients/autocomplete?query=" + query;

        httpGetRequest(url, c);
    }

    public List<Ingredient> parseIngredientJson(Response r) throws IOException {
        String json = r.body().string();
        Type t = new TypeToken<List<Ingredient>>(){}.getType();
        return gson.fromJson(json, t);
    }

    public void searchRecipes(RecipeSearchParameters rsp, Callback c) throws Exception {
        String ingredients = rsp.getIngredients();
        String ranking = rsp.getRanking();
        String query = rsp.getQuery();
        String license = rsp.getLimitLicense();
        String num = rsp.getMaxNumber();

        if (ingredients != null && query != null) {
            throw new IllegalArgumentException("Query and Ingredients cannot both be defined");
        }

        if (ingredients != null && ranking != null) {
            String url = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?";
            url += "ingredients=" + ingredients + "&ranking=" + ranking;

            String fill = rsp.getIngredientLists();
            if (fill != null) {
                url += "&fillIngredients=" + fill;
            }

            if (license != null) {
                url += "&limitLicense=" + license;
            }

            if (num != null) {
                url += "&number=" + num;
            }

            httpGetRequest(url, c);
        } else if (query != null) {
            String url = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/search?";
            url += "query=" + query;

            String cuisine = rsp.getCuisine();
            if (cuisine != null) {
                url += "&cuisine=" + cuisine;
            }

            String diet = rsp.getDiet();
            if (diet != null) {
                url += "&diet=" + diet;
            }

            String intol = rsp.getIntolerance();
            if (intol != null) {
                url += "&intolerances=" + intol;
            }

            if (license != null) {
                url += "&limiLicense=" + license;
            }

            if (num != null) {
                url += "&number=" + num;
            }

            String offset = rsp.getOffset();
            if (offset != null) {
                url += "&offset=" + offset;
            }

            String type = rsp.getType();
            if (type != null) {
                url += "&type" + type;
            }

            httpGetRequest(url, c);
        } else {
            throw new IllegalArgumentException("Query cannot be null, or Ingredients and Ranking cannot both be null.");
        }
    }

    public List<RecipeSearchResult> parseRecipeSearchResultJson(Response r, Boolean useWrapper) throws IOException {
        String json = r.body().string();

        if (useWrapper) {
            Type t = new TypeToken<RecipeSearchResultWrapper>(){}.getType();
            RecipeSearchResultWrapper wrapper = gson.fromJson(json, t);
            return wrapper.getRecipes();
        } else {
            Type t = new TypeToken<List<RecipeSearchResult>>(){}.getType();
            return gson.fromJson(json, t);
        }
    }

    public void getRecipe(Integer id, Boolean nutrition, Callback c) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException();
        }

        String url = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id +
                "/information";

        if (nutrition != null) {
            url += "?includeNutrition=" + nutrition.toString();
        }

        httpGetRequest(url, c);
    }

    public Recipe parseRecipeJson(Response r) throws IOException {
        String json = r.body().string();
        Type t = new TypeToken<Recipe>(){}.getType();
        return gson.fromJson(json, t);
    }
}
