package com.example.android.baking.Utilities;

import android.content.Context;

import com.example.android.baking.Model.Ingredients;
import com.example.android.baking.Model.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by joycelin12 on 7/14/18.
 */

public class RecipeJsonUtils {

    private static final String M_STEPS = "steps";
    private static final String M_VALUE = "VALUE";


    public static ArrayList<Recipe> getRecipeFromJson(Context context, String RecipeJsonStr)
            throws JSONException {

        //JSONObject recipeJson = new JSONObject(RecipeJsonStr);
        //JSONArray recipesArray = recipeJson.getJSONArray(M_VALUE);

        Gson gson = new GsonBuilder().create();

        ArrayList<Recipe> parsedRecipeData = gson.fromJson(RecipeJsonStr, new TypeToken<ArrayList<Recipe>>() {
        }.getType());


        return parsedRecipeData;
    }

    public static String createIngredients(ArrayList<Ingredients> ingredData) {

        String ingredlist="";

        for (Ingredients in: ingredData) {

            ingredlist = ingredlist + in.getQuantity() + " " + in.getMeasure() + " " + in.getIngredient() + "\n";
        }

        return ingredlist;

    }




}