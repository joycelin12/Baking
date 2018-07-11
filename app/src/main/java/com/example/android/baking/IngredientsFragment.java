package com.example.android.baking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by joycelin12 on 7/11/18.
 */

public class IngredientsFragment extends Fragment {

    @BindView(R.id.ingredients_text_view) TextView ingredientsTextView;
    private Unbinder unbinder;

    //Mandatory constructor for instantiating the fragment
    public IngredientsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //inflate ingredients layout
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        ingredientsTextView.setText("Ingredients");

        return rootView;

    }
}
