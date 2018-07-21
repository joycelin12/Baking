package com.example.android.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.baking.Model.Steps;
import com.example.android.baking.Utilities.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailActivity extends AppCompatActivity implements StepDetailFragment.OnBClickListener {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String STEP_DETAILS = "step_details";
    private static final int DEFAULT_POSITION = -1;
    public static final String NAME = "recipe_name";
    private static final String FRAGMENT_TAG_STRING = "fragment_tag" ;
    private ArrayList<Steps> stepData = new ArrayList<>();
    int position;
    String mName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if (savedInstanceState == null) {

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

            mName = intent.getStringExtra(NAME);
            setTitle(mName);

            stepData = getIntent().getExtras().getParcelableArrayList(STEP_DETAILS);

            StepDetailFragment detailFragment = new StepDetailFragment();

            detailFragment.setSteps(stepData);
            detailFragment.setPosition(position);

            //Use a FragmentManager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();
            //Fragment transaction
            fragmentManager.beginTransaction()
                    .add(R.id.detail, detailFragment, "detail")
                    .commit();


        } else {
            stepData = savedInstanceState.getParcelableArrayList(STEP_DETAILS);
            position = savedInstanceState.getInt(EXTRA_POSITION);

            StepDetailFragment detailFragment = (StepDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail);

            detailFragment.setSteps(stepData);
            detailFragment.setPosition(position);

            //Use a FragmentManager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();
            //Fragment transaction
            fragmentManager.beginTransaction()
                    .replace(R.id.detail, detailFragment)
                    .commit();


        }


    }

    private void closeOnError() {

        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onButtonSelected(int position) {

        if(stepData != null ) {


            if (position == stepData.size()) {

                Toast.makeText(getApplicationContext(), R.string.last_step, Toast.LENGTH_SHORT).show();

            } else if (position == -1) {

                Toast.makeText(getApplicationContext(), R.string.first_step, Toast.LENGTH_SHORT).show();

            } else {

                StepDetailFragment detailFragment = new StepDetailFragment();

                detailFragment.setSteps(stepData);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STEP_DETAILS, stepData);
        outState.putInt(EXTRA_POSITION, position);
        super.onSaveInstanceState(outState);
    }


}
