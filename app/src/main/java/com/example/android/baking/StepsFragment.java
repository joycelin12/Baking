package com.example.android.baking;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.baking.Model.Steps;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.widget.Toast.*;

/**
 * Created by joycelin12 on 7/12/18.
 */

public class StepsFragment extends Fragment implements StepsAdapter.ItemClickListener{

    @BindView(R.id.recipe_steps_recyclerview) RecyclerView recipeStepsRecyclerView;
    private Unbinder unbinder;
    private ArrayList<Steps> stepsData = new ArrayList<>();
    private Boolean mTwoPane;


    //Mandatory constructor for instantiating the fragment
    public StepsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //inflate ingredients layout
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        //referencing https://stackoverflow.com/questions/26621060/display-a-recyclerview-in-fragment
        //set layoutManager
        recipeStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*
                stepsData.add(new Steps("0", "Recipe Introduction", "Recipe Introduction", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdae8_-intro-cheesecake/-intro-cheesecake.mp4", ""));
                stepsData.add(new Steps("1","Starting prep.","1. Preheat the oven to 350\u00b0F. Grease the bottom of a 9-inch round springform pan with butter. ", "",  ""));
                stepsData.add(new Steps("2", "Prep the cookie crust.",
                "2. To assemble the crust, whisk together the cookie crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt for the crust in a medium bowl. Stir in the melted butter and 1 teaspoon of vanilla extract until uniform. ",
                "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdb1d_2-form-crust-to-bottom-of-pan-cheesecake/2-form-crust-to-bottom-of-pan-cheesecake.mp4",
                "thumbnailURL"));*/
        StepsAdapter sAdapter = new StepsAdapter(stepsData.size(), stepsData, this.getContext());
        sAdapter.setClickListener(this);
        recipeStepsRecyclerView.setAdapter(sAdapter);

        //Get the mTwoPane data referencing https://stackoverflow.com/questions/12739909/send-data-from-activity-to-fragment-in-android
        RecipeDetailActivity activity = (RecipeDetailActivity) getActivity();
        mTwoPane = activity.getTwoPane();

        return rootView;

    }

    @Override
    public void onItemClick(View view, int position) throws JSONException {

        if(!mTwoPane) {

            Intent intent = new Intent(getContext(), StepDetailActivity.class);
            intent.putExtra(StepDetailActivity.EXTRA_POSITION, position);
            intent.putExtra(StepDetailActivity.STEP_DETAILS, stepsData);

            startActivity(intent);

        } else {

            StepDetailFragment detailFragment = new StepDetailFragment();

            detailFragment.setSteps(stepsData);
            detailFragment.setPosition(position);


            //Use a FragmentManager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            //Fragment transaction
            fragmentManager.beginTransaction()
                    .add(R.id.detail, detailFragment)
                    .commit();
        }


    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setSteps(ArrayList<Steps> stepsData) {
        this.stepsData = stepsData  ;
    }
}
