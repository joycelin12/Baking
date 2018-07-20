package com.example.android.baking;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.android.baking.Model.Recipe;
import com.example.android.baking.Utilities.NetworkUtils;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ItemClickListener,
        RecipeTask.RecipeResponse {

    private static final int NUM_COLS = 1;
    @BindView(R.id.recipe_main_recyclerview) RecyclerView recipeMainRecyclerView;
    private ArrayList<Recipe> recipesList;
    private RecipeAdapter rAdapter;
    private RecipeAdapter.ItemClickListener mClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recipeMainRecyclerView.setLayoutManager(new GridLayoutManager(this, NUM_COLS));
        recipeMainRecyclerView.setHasFixedSize(true);

        //allow up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState != null) {

            this.recipesList = savedInstanceState.getParcelableArrayList("recipes");
            rAdapter = new RecipeAdapter(this.recipesList.size(), this.recipesList, this);
            rAdapter.setClickListener(this.mClickListener);
            this.recipeMainRecyclerView.setAdapter(rAdapter);


        } else {

            if (isOnline()) {
                loadRecipeData();
            } else  {

                //referencing https://www.androidhive.info/2015/09/android-material-design-snackbar-example/
                String message = getString(R.string.noic);
                Snackbar snackbar = Snackbar
                        .make(recipeMainRecyclerView, message, Snackbar.LENGTH_LONG);

                snackbar.show();
                //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("recipes", recipesList);


    }

    private void loadRecipeData() {

        new RecipeTask(this,  this, recipeMainRecyclerView, this).execute();

    }

    @Override
    public void processFinish(ArrayList<Recipe> output) {
        recipesList = output;
    }

    @Override
    public void onItemClick(View view, int position) throws JSONException {

        Recipe recipe = recipesList.get(position);

        launchDetailActivity(recipe, position);

    }

    private void launchDetailActivity(Recipe recipe, int position) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.EXTRA_POSITION, position);
        intent.putExtra(RecipeDetailActivity.RECIPE_DETAILS, recipe);
        startActivity(intent);
    }

    //referencing from https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out/27312494#27312494
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
