package com.example.android.baking;

import android.content.Intent;
import android.graphics.Movie;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.baking.Model.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static android.content.Intent.getIntent;

public class StepDetailActivity extends AppCompatActivity implements StepDetailFragment.OnBClickListener {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String STEP_DETAILS = "step_details";
    private static final int DEFAULT_POSITION = -1;
    private ArrayList<Steps> stepData = new ArrayList<>();
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

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

            StepDetailFragment detailFragment = new StepDetailFragment();

            detailFragment.setSteps(stepData);
            detailFragment.setPosition(position);


            //Use a FragmentManager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();
            //Fragment transaction
            fragmentManager.beginTransaction()
                    .add(R.id.detail, detailFragment)
                    .commit();

        }

    }

    private void closeOnError() {

        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onButtonSelected(int position) {


        if (position == stepData.size()){

            Toast.makeText(getApplicationContext(), "This is the last step of the recipe", Toast.LENGTH_SHORT).show();

        } else if (position == -1) {

            Toast.makeText(getApplicationContext(), "This is the first step of the recipe", Toast.LENGTH_SHORT).show();

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
