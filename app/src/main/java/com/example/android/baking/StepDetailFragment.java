package com.example.android.baking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.baking.Model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by joycelin12 on 7/12/18.
 */

public class StepDetailFragment extends Fragment {

    @BindView(R.id.stepDetailShort) TextView detailShortTextView;
    @BindView(R.id.stepDetailDescription) TextView detailDescriptionTextView;
    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.prev) Button prev;
    @BindView(R.id.next) Button next;

    private Unbinder unbinder;
    private SimpleExoPlayer mExoPlayer;
    private int mPosition;
    private String mName;
    private ArrayList<Steps> mSteps= new ArrayList<>();
    public static final String EXTRA_POSITION = "extra_position";
    public static final String STEP_DETAILS = "step_details";

    // Define a new interface OnBClickListener that triggers a callback in the host activity
    OnBClickListener mCallback;


    // OnBClickListener interface, calls a method in the host activity named onButtonSelected
    public interface OnBClickListener {
        void onButtonSelected(int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnBClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnButtonClickListener");
        }
    }


    //Mandatory constructor for instantiating the fragment
    public StepDetailFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // Load the saved state (the list of images and list index) if there is one
        if(savedInstanceState != null) {
            mSteps = savedInstanceState.getParcelableArrayList(STEP_DETAILS);
            mPosition = savedInstanceState.getInt(EXTRA_POSITION);

        }

        View rootView = inflater.inflate(R.layout.fragment_steps_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        Uri uri = Uri.parse(mSteps.get(mPosition).getVideoURL());
        initializePlayer(uri);


        detailShortTextView.setText(mSteps.get(mPosition).getShortDescription());
        detailDescriptionTextView.setText(mSteps.get(mPosition).getDescription());

        prev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCallback.onButtonSelected(mPosition-1);
            }

        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCallback.onButtonSelected(mPosition+1);
            }

        });

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initializePlayer(Uri mediaUri) {

        if (mExoPlayer == null) {

            //What you do if there is a file not found exception referencing https://forums.xamarin.com/discussion/100150/how-to-implement-exoplayer-eventlistner
            //Create an instance of the ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            //Prepare the MediaSource
            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);

        }

    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STEP_DETAILS, mSteps);
        outState.putInt(EXTRA_POSITION, mPosition);

        super.onSaveInstanceState(outState);
    }

    public void setSteps(ArrayList<Steps> steps) {
        mSteps = steps;
    }

    public void setPosition(int position) { mPosition = position; }

    public void setName(String name) { mName= name; }


}
