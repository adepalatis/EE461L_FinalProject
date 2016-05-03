package com.github.adepalatis.ee461.recipez;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by michael on 4/24/16.
 */
public class RecipeSearchParameters implements Parcelable {

    private static String LIST = "lists";
    private static String LICENSE = "limit";
    private static String MAX = "max";
    private static String OFFSET = "off";
    private static String RANK = "rank";
    private static String QUERY = "query";
    private static String TYPE = "type";
    private static String INGREDIENTS = "ingredients";
    private static String EXCLUDE = "exclude";
    private static String CUISINE = "cuisine";
    private static String DIET = "diet";
    private static String INTOLERANCE = "intolerance";

    private Map<String, String> params;

    public void setIngredientLists(Boolean is) {
        params.put(LIST, is.toString());
    }

    public String getIngredientLists() {
        return params.get(LIST);
    }

    public void setLimitLicense(Boolean is) {
        params.put(LICENSE, is.toString());
    }

    public String getLimitLicense() {
        return params.get(LICENSE);
    }

    public void setMaxNumber(Integer num) {
        params.put(MAX, num.toString());
    }

    public String getMaxNumber() {
        return params.get(MAX);
    }

    public void setOffset(Integer num) {
        params.put(OFFSET, num.toString());
    }

    public String getOffset() {
        return params.get(OFFSET);
    }

    public void setRanking(Integer num) {
        params.put(RANK, num.toString());
    }

    public String getRanking() {
        return params.get(RANK);
    }

    public void setQuery(String query) {
        params.put(QUERY, query);
    }

    public String getQuery() {
        return params.get(QUERY);
    }

    public void setType(String type) {
        params.put(TYPE, type);
    }

    public String getType() {
        return params.get(TYPE);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        params.put(INGREDIENTS, convertToString(ingredients));
    }

    public String getIngredients() {
        return params.get(INGREDIENTS);
    }

    public void setExcludeIngredients(List<Ingredient> excludeIngredients) {
        params.put(EXCLUDE, convertToString(excludeIngredients));
    }

    public String getExcludeIngredients() {
        return params.get(EXCLUDE);
    }

    public void setCuisine(List<String> cuisine) {
        params.put(CUISINE, convertStringsToString(cuisine));
    }

    public String getCuisine() {
        return params.get(CUISINE);
    }

    public void setDiet(List<String> diet) {
        params.put(DIET, convertStringsToString(diet));
    }

    public String getDiet() {
        return params.get(DIET);
    }

    public void setIntolerance(List<String> intolerance) {
        params.put(INTOLERANCE, convertStringsToString(intolerance));
    }

    public String getIntolerance() {
        return params.get(INTOLERANCE);
    }

    private RecipeSearchParameters() {
        params = new HashMap<>();
    }

    public RecipeSearchParameters(String query) {
        this();
        setQuery(query);
    }

    public RecipeSearchParameters(List<Ingredient> ingredients, Integer ranking) {
        this();
        setIngredients(ingredients);
        setRanking(ranking);
    }

    public RecipeSearchParameters(Parcel p) {
        this();
        int paramSize = p.readInt();
        for (int i = 0; i < paramSize; i++) {
            String key = p.readString();
            String val = p.readString();
            params.put(key, val);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        int paramSize = params.size();
        dest.writeInt(paramSize);

        for (Map.Entry<String, String> e: params.entrySet()) {
            dest.writeString(e.getKey());
            dest.writeString(e.getValue());
        }
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

    private String convertToString(List<Ingredient> ingredients) {
        String list = "";
        for (Ingredient i: ingredients) {
            list += i.getName() + ",";
        }
        if(list.equals(""))
            return "";
        else
            return list.substring(0, list.length()-1); // Remove last comma
    }

    private String convertStringsToString(List<String> strings) {
        String result = "";
        for (String s: strings) {
            result += s + ",";
        }
        if(result.equals(""))
            return "";
        else
            return result.substring(0, result.length()-1);
    }
}
