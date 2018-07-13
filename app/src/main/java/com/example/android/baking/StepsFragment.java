package com.example.android.baking;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

        ArrayList<Steps> stepsData = new ArrayList<>();

                stepsData.add(new Steps("0", "Recipe Introduction", "Recipe Introduction", "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdae8_-intro-cheesecake/-intro-cheesecake.mp4", ""));
                stepsData.add(new Steps("1","Starting prep.","1. Preheat the oven to 350\u00b0F. Grease the bottom of a 9-inch round springform pan with butter. ", "",  ""));

        StepsAdapter sAdapter = new StepsAdapter(2, stepsData, this.getContext());
        sAdapter.setClickListener(this);
        recipeStepsRecyclerView.setAdapter(sAdapter);


        return rootView;

    }

    @Override
    public void onItemClick(View view, int position) throws JSONException {

        //Trailer trailer = parseSingleTrailerJson(TrailerString, position);
        //referencing from the website: https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent
        //Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
        //Intent webIntent = new Intent(Intent.ACTION_VIEW,
        //        Uri.parse(VID_URL + trailer.getKey()));
        //try {
        //    startActivity(appIntent);
        //} catch (ActivityNotFoundException ex) {
        //    startActivity(webIntent);
        //}


    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
