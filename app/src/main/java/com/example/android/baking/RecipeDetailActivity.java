package com.example.android.baking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.android.baking.Model.Ingredients;
import com.example.android.baking.Model.Recipe;
import com.example.android.baking.Model.Steps;

import java.util.ArrayList;

import static com.example.android.baking.Utilities.RecipeJsonUtils.createIngredients;


public class RecipeDetailActivity extends AppCompatActivity implements StepDetailFragment.OnBClickListener{

    public static final String RECIPE_DETAILS = "recipe_details";
    private boolean mTwoPane;
    public static final String EXTRA_POSITION = "extra_position";
    public static final String STEP_DETAILS = "step_details";
    private static final int DEFAULT_POSITION = -1;
    private ArrayList<Steps> stepData = new ArrayList<>();
    private ArrayList<Steps> stepsData = new ArrayList<>();
    private ArrayList<Ingredients> ingredData = new ArrayList<>();

    private Recipe recipe;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        final Recipe recipe = (Recipe) getIntent().getParcelableExtra(RECIPE_DETAILS);

        setTitle(recipe.getName());

        stepsData = recipe.getSteps();
        ingredData = recipe.getIngredients();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name",recipe.getName());
        editor.putString("ingredients",createIngredients(ingredData));
        editor.apply();

        //tablet mode
        //referencing https://stackoverflow.com/questions/9279111/determine-if-the-device-is-a-smartphone-or-tablet
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if(tabletSize) {

            mTwoPane = true;

            if(savedInstanceState == null) {

                Intent intent = getIntent();
                if (intent == null) {
                    closeOnError();
                }

                position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
                if (position == DEFAULT_POSITION) {
                    // EXTRA_POSITION not found in intent
                    closeOnError();
                    return;
                }

                stepData = getIntent().getExtras().getParcelableArrayList(STEP_DETAILS);
                }
                else {
                    stepData = savedInstanceState.getParcelableArrayList(STEP_DETAILS);
                    position = savedInstanceState.getInt(EXTRA_POSITION);
                }

              /*  StepDetailFragment detailFragment = new StepDetailFragment();

                detailFragment.setSteps(stepData);
                detailFragment.setPosition(position);
                detailFragment.setName(recipe.getName());

                //Use a FragmentManager and transaction to add the fragment to the screen
                FragmentManager fragmentManager = getSupportFragmentManager();
                //Fragment transaction
                fragmentManager.beginTransaction()
                        .add(R.id.detail, detailFragment)
                        .commit(); */




        } else {

            mTwoPane = false;
        }

        if(savedInstanceState == null) {

            //Create a ingredientsfragment instance and display it using FragmentManager
            IngredientsFragment ingredFragment = new IngredientsFragment();
            //Create the stepsfragment instance and display it using FragmentManager
            StepsFragment stepsFragment = new StepsFragment();

            stepsFragment.setSteps(stepsData);
            stepsFragment.setName(recipe.getName());
            ingredFragment.setIngredients(ingredData);

            //Use a FragmentManager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();
            //Fragment transaction
            fragmentManager.beginTransaction()
                    .add(R.id.ingredients, ingredFragment)
                    .add(R.id.steps, stepsFragment)
                    .commit();

        }
    }

    private void closeOnError() {

        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    public Boolean getTwoPane() {
        return mTwoPane;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("recipe", recipe);
        outState.putParcelableArrayList(STEP_DETAILS, stepsData);
        outState.putInt(EXTRA_POSITION, position);
        super.onSaveInstanceState(outState);



    }

    @Override
    public void onButtonSelected(int position) {

        if(stepsData != null ) {

            Log.i("TAG", stepsData.size() + " is size " + position + " is position ");


            if (position == stepsData.size()) {

                Toast.makeText(getApplicationContext(), R.string.last_step, Toast.LENGTH_SHORT).show();

            } else if (position == -1) {

                Toast.makeText(getApplicationContext(), R.string.first_step, Toast.LENGTH_SHORT).show();

            } else {

                StepDetailFragment detailFragment = new StepDetailFragment();

                detailFragment.setSteps(stepsData);
                detailFragment.setPosition(position);

                //Use a FragmentManager and transaction to add the fragment to the screen
                FragmentManager fragmentManager = getSupportFragmentManager();
                //Fragment transaction
                fragmentManager.beginTransaction()
                        .replace(R.id.detail, detailFragment)
                        .commit();
            }
        }
    }


}
