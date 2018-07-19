package com.example.android.baking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.Model.Ingredients;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.android.baking.Utilities.RecipeJsonUtils.createIngredients;

/**
 * Created by joycelin12 on 7/11/18.
 */

public class IngredientsFragment extends Fragment {

    private static final String INGRED_DETAILS =  "ingredients";
    @BindView(R.id.ingredients_text_view) TextView ingredientsTextView;
    private Unbinder unbinder;
    private ArrayList<Ingredients> ingredData;

    //Mandatory constructor for instantiating the fragment
    public IngredientsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            ingredData = savedInstanceState.getParcelableArrayList(INGRED_DETAILS);

        }


        //inflate ingredients layout
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        unbinder = ButterKnife.bind(this, rootView);


        ingredientsTextView.setText(createIngredients(ingredData));

        return rootView;

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setIngredients(ArrayList<Ingredients> ingredData) {
        this.ingredData = ingredData  ;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(INGRED_DETAILS, ingredData);

        super.onSaveInstanceState(outState);
    }
}
