package com.github.adepalatis.ee461.recipez;

import android.app.Application;
import android.os.Parcel;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testRecipeSearchParametersParcelPackAndUnpack() {
        RecipeSearchParameters rsp = new RecipeSearchParameters("pasta");
        Parcel p = Parcel.obtain();
        rsp.writeToParcel(p, 0);
        RecipeSearchParameters result = RecipeSearchParameters.CREATOR.createFromParcel(p);
        Assert.assertTrue(rsp.getQuery().equals(result.getQuery()));
    }
}