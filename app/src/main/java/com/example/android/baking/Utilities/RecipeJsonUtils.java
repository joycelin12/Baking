package com.example.android.baking.Utilities;

import android.content.Context;
import android.util.Log;

import com.example.android.baking.Model.Recipe;
import com.example.android.baking.Model.Steps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        Log.i("TAG", parsedRecipeData.get(0).toString() );

        return parsedRecipeData;
    }

    public static ArrayList<Steps> getStepsFromJson(Context context, String StepsJsonStr)
            throws JSONException {

        JSONObject stepsJson = new JSONObject(StepsJsonStr);
        JSONArray stepsArray = stepsJson.getJSONArray(M_STEPS);

        Gson gson = new GsonBuilder().create();

        ArrayList<Steps> parsedStepData = gson.fromJson(stepsArray.toString(), new TypeToken<ArrayList<Steps>>() {
        }.getType());

        return parsedStepData;
    }


}