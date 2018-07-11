package com.example.android.baking;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        //Create a ingredientsfragment instance and display it using FragmentManager
        IngredientsFragment ingredFragment = new IngredientsFragment();
        //Use a FragmentManager and transaction to add the fragment to the screen
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Fragment transaction
        fragmentManager.beginTransaction()
                .add(R.id.ingredients, ingredFragment)
                .commit();

    }
}