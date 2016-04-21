package com.github.adepalatis.ee461.recipez;

/**
 * Created by michael on 4/20/16.
 */
public class Nutrition {
    public Nutrient[] nutrients = null;

    public Nutrition(){}

    @Override
    public String toString() {
        String s = "[";
        for (Nutrient n: nutrients) {
            s += n + ", ";
        }
        return s.substring(0, s.length()-2) + "]";
    }
}
