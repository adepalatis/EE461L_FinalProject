package me.ziyuanwang.recipez;

/**
 * Created by Tony on 3/23/2016.
 */

import java.io.IOException;
import okhttp3.Response;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Client {

    String spoonacularUrl = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients?ingredients=apples%2Cflour%2Csugar&limitLicense=false&number=5&ranking=1";
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
