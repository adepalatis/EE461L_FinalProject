package com.github.adepalatis.ee461.recipez;

import java.util.Arrays;
import java.util.List;

/**
 * Created by michael on 4/20/16.
 */
public class RecipeSearchResultWrapper {
    public RecipeSearchResult[] results = null;
    public String baseUri = null;
    public Integer offset = null;
    public Integer number = null;
    public Integer totalResults = null;
    public Integer processingTimeMs = null;
    public String expires = null;
    public Boolean isStale = null;

    public RecipeSearchResultWrapper() {}

    public List<RecipeSearchResult> getRecipes() {
        return Arrays.asList(results);
    }
}
