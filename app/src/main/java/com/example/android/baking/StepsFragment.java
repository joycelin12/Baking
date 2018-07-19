package com.example.android.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.baking.Model.Steps;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by joycelin12 on 7/12/18.
 */

public class StepsFragment extends Fragment implements StepsAdapter.ItemClickListener{

    @BindView(R.id.recipe_steps_recyclerview) RecyclerView recipeStepsRecyclerView;
    private Unbinder unbinder;
    private ArrayList<Steps> stepsData = new ArrayList<>();
    public static final String STEPS_DETAILS = "steps_details";
    private Boolean mTwoPane;
    private String mName;

    //Mandatory constructor for instantiating the fragment
    public StepsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null) {
            stepsData = savedInstanceState.getParcelableArrayList(STEPS_DETAILS);

        }

        //inflate ingredients layout
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        //referencing https://stackoverflow.com/questions/26621060/display-a-recyclerview-in-fragment
        //set layoutManager
        recipeStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
            intent.putExtra(StepDetailActivity.NAME, mName );

            startActivity(intent);

        } else {

            StepDetailFragment detailFragment = new StepDetailFragment();

            detailFragment.setSteps(stepsData);
            detailFragment.setPosition(position);
            detailFragment.setName(mName);


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

    public void setSteps(ArrayList<Steps> stepData) {
        stepsData = stepData  ;
    }

    public void setName(String name) { mName= name; }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEPS_DETAILS, stepsData);


    }


}
