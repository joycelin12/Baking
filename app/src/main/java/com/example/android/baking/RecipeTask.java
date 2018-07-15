package com.example.android.baking;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.baking.Model.Recipe;
import com.example.android.baking.Utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.baking.Utilities.RecipeJsonUtils.getRecipeFromJson;

/**
 * Created by joycelin12 on 7/14/18.
 */

public class RecipeTask extends AsyncTask<String, Void, ArrayList<Recipe>> {

    private RecipeAdapter rAdapter;
    private RecyclerView recipesList;
    private Context context;
    private RecipeAdapter.ItemClickListener mClickListener;
    public  RecipeResponse recipes = null;
    private String JSONString;
    private static final int NUM_LIST_ITEMS = 20;


    public RecipeTask(Context context, RecipeAdapter.ItemClickListener itemClickListener, RecyclerView recipesList,
                     RecipeResponse recipes){
        this.context = context;
        this.mClickListener = itemClickListener;
        this.recipesList = recipesList;
        this.recipes = recipes;

    }

    @Override
    protected ArrayList<Recipe> doInBackground(String ... params) {

        URL recipeUrl = NetworkUtils.buildUrl();

        try {

            NetworkUtils test = new NetworkUtils();
            JSONString = test.run(recipeUrl.toString());
            Log.i("TAG", JSONString);


            ArrayList<Recipe> simpleRecipeData  = getRecipeFromJson(this.context, JSONString);
            recipes.processFinish(simpleRecipeData);

            return simpleRecipeData;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    // TODO (3) Override onPostExecute to display the results in the GridView
    @Override
    protected void onPostExecute(ArrayList<Recipe> recipeData) {

        rAdapter = new RecipeAdapter(NUM_LIST_ITEMS, recipeData, this.context);
        rAdapter.setClickListener(this.mClickListener);
        this.recipesList.setAdapter(rAdapter);

    }

    public interface RecipeResponse{
        void processFinish(ArrayList<Recipe> output);
    }
}
