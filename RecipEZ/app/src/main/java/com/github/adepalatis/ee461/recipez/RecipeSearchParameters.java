package com.github.adepalatis.ee461.recipez;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by michael on 4/24/16.
 */
public class RecipeSearchParameters implements Parcelable {
    private boolean ingredientLists = true; // Show used and missing ingredients in results
    private boolean limitLicense = true; // show with proper attribution
    private int maxNumber = 10; // Max number of results
    private int offset = 0; // Number of results to skip
    private int ranking = 1; // 1 = maximize used ingredients, 2 = minimize missing ingredients
    private String query = ""; // Recipe search query
    private String type = ""; // The type of the recipe, i.e main course
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Ingredient> excludeIngredients = new ArrayList<>();
    private List<String> cuisine = new ArrayList<>();
    private List<String> diet = new ArrayList<>();
    private List<String> intolerance = new ArrayList<>();

    public boolean isIngredientLists() {
        return ingredientLists;
    }

    public void setIngredientLists(boolean ingredientLists) {
        this.ingredientLists = ingredientLists;
    }

    public boolean isLimitLicense() {
        return limitLicense;
    }

    public void setLimitLicense(boolean limitLicense) {
        this.limitLicense = limitLicense;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getExcludeIngredients() {
        return excludeIngredients;
    }

    public void setExcludeIngredients(List<Ingredient> excludeIngredients) {
        this.excludeIngredients = excludeIngredients;
    }

    public List<String> getCuisine() {
        return cuisine;
    }

    public void setCuisine(List<String> cuisine) {
        this.cuisine = cuisine;
    }

    public List<String> getDiet() {
        return diet;
    }

    public void setDiet(List<String> diet) {
        this.diet = diet;
    }

    public List<String> getIntolerance() {
        return intolerance;
    }

    public void setIntolerance(List<String> intolerance) {
        this.intolerance = intolerance;
    }

    public RecipeSearchParameters(String query) {
        this.query = query;
    }

    public RecipeSearchParameters(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public RecipeSearchParameters(Parcel p) {
        ingredientLists = (boolean) p.readValue(null);
        limitLicense = (boolean) p.readValue(null);
        maxNumber = p.readInt();
        offset = p.readInt();
        ranking = p.readInt();
        query = p.readString();
        type = p.readString();

        ingredients = Arrays.asList(p.createTypedArray(Ingredient.CREATOR));
        excludeIngredients = Arrays.asList(p.createTypedArray(Ingredient.CREATOR));

        p.readList(cuisine, List.class.getClassLoader());
        p.readList(diet, List.class.getClassLoader());
        p.readList(intolerance, List.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(ingredientLists);
        dest.writeValue(limitLicense);
        dest.writeInt(maxNumber);
        dest.writeInt(offset);
        dest.writeInt(ranking);
        dest.writeString(query);
        dest.writeString(type);

        dest.writeTypedList(ingredients);
        dest.writeTypedList(excludeIngredients);

        dest.writeList(cuisine);
        dest.writeList(diet);
        dest.writeList(intolerance);
    }

    public static final Parcelable.Creator<RecipeSearchParameters> CREATOR = new Parcelable.Creator<RecipeSearchParameters>() {
        @Override
        public RecipeSearchParameters createFromParcel(Parcel source) {
            return new RecipeSearchParameters(source);
        }

        @Override
        public RecipeSearchParameters[] newArray(int size) {
            return new RecipeSearchParameters[size];
        }
    };
}
