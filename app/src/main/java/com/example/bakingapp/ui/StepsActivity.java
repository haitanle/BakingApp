package com.example.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity {

    private static final String TAG = StepsActivity.class.getSimpleName();

    private SimpleExoPlayer mExoPlayer;

    @BindView(R.id.playerView) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.description_textView) TextView mDescriptionView;
    @BindView(R.id.buttonNext) Button mButtonNext;
    @BindView(R.id.buttonPrevious) Button mButtonPrevious;
    @BindView(R.id.thumbNail_imageView) ImageView thumbNaiView;

    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    private int currentStepID;
    private Uri videoUri;
    private long currentVideoPosition;
    private boolean isPlayWhenReady;
    private Recipe recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        currentVideoPosition = 0;
        int stepID = 0;
        isPlayWhenReady = true;

        if (savedInstanceState != null && savedInstanceState.getLong(getString(R.string.currentVideoPosition)) != 0){
            currentVideoPosition = savedInstanceState.getLong(getString(R.string.currentVideoPosition));
            isPlayWhenReady = savedInstanceState.getBoolean(getString(R.string.isPlayWhenReady_key));

            stepID = savedInstanceState.getInt("currentStepID");
        }else{
            stepID = getIntent().getIntExtra(getString(R.string.intent_extra_stepID),-1);
        }

        int recipeID = getIntent().getIntExtra(getString(R.string.intent_extra_recipeID),-1);

        recipe = Recipe.getRecipeByID(this,recipeID);
        currentStepID = stepID;

        String stepURL = recipe.getStepsList().get(currentStepID).getVideoUrl();
        String description = recipe.getStepsList().get(currentStepID).getDescription();

        mDescriptionView.setText(description);

        initializeMediaSession();

        videoUri = Uri.parse(stepURL);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23){
            initializePlayer(videoUri, currentVideoPosition, isPlayWhenReady);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mExoPlayer == null){
            initializePlayer(videoUri, currentVideoPosition, isPlayWhenReady);
        }
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(this, TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);
    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri, long currentVideoPosition, boolean isPlayWhenReady) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);

            handleNoVideo(mediaUri);

            mExoPlayer.seekTo(currentVideoPosition);
            mExoPlayer.setPlayWhenReady(isPlayWhenReady);

            mButtonNext.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    currentStepID++;
                    if (recipe.getStepsList().size() <= currentStepID){
                        currentStepID = 0;
                    }
                    mDescriptionView.setText(recipe.getStepsList().get(currentStepID).getDescription());

                    Uri mediaUri = Uri.parse(recipe.getStepsList().get(currentStepID).getVideoUrl());
                    videoUri = mediaUri;

                    MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                            view.getContext(), Util.getUserAgent(view.getContext(),"BakingApp")), new DefaultExtractorsFactory(), null, null);

                    handleNoVideo(mediaUri);

                    mExoPlayer.prepare(mediaSource);
                    mExoPlayer.setPlayWhenReady(true);
                }
            });

            mButtonPrevious.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    currentStepID--;
                    if (currentStepID == -1){
                        currentStepID = recipe.getStepsList().size()-1;
                    }
                    mDescriptionView.setText(recipe.getStepsList().get(currentStepID).getDescription());

                    Uri mediaUri = Uri.parse(recipe.getStepsList().get(currentStepID).getVideoUrl());
                    videoUri = mediaUri;

                    MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                            view.getContext(), Util.getUserAgent(view.getContext(),"BakingApp")), new DefaultExtractorsFactory(), null, null);

                    handleNoVideo(mediaUri);

                    mExoPlayer.prepare(mediaSource);
                    mExoPlayer.setPlayWhenReady(true);
                }
            });
        }
    }


    private void handleNoVideo(Uri videoURL){

        Steps recipeStep= Recipe.getRecipeByID(this, MainActivity.recipeSelected).getStepsList().get(currentStepID);

        if (videoURL.toString().equals("")){
            String thumbNailUrl = recipeStep.getThumbnail();

            if (thumbNailUrl.equals("") || videoURL.toString().contains(".mp4")){

                mExoPlayer.setPlayWhenReady(false);

                Picasso.get()
                        .load(R.drawable.no_video_thumbnail2)
                        .into(thumbNaiView);

            }else{

                Picasso.get()
                        .load(thumbNailUrl)
                        .placeholder(R.color.colorPrimary)
                        .into(thumbNaiView);
            }

            showThumbNail();

        }else {

            showVideoPlayer();
        }
    }

    private void showThumbNail(){
        mPlayerView.setVisibility(View.GONE);
        thumbNaiView.setVisibility(View.VISIBLE);
    }

    private void showVideoPlayer(){
        thumbNaiView.setVisibility(View.GONE);
        mPlayerView.setVisibility(View.VISIBLE);
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    protected void onPause(){
        super.onPause();

        if (Util.SDK_INT <=23){
            releasePlayer();
        }

        //save the player state before pausing
        isPlayWhenReady = mExoPlayer.getPlayWhenReady();
        mExoPlayer.setPlayWhenReady(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23){
            currentVideoPosition = mExoPlayer.getCurrentPosition();
            releasePlayer();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mExoPlayer != null){
            releasePlayer();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        long videoCurrentPosition = (mExoPlayer==null)? currentVideoPosition: mExoPlayer.getCurrentPosition();

        try {
            outState.putLong(getString(R.string.currentVideoPosition), videoCurrentPosition);
            outState.putBoolean(getString(R.string.isPlayWhenReady_key), isPlayWhenReady);
            outState.putInt("currentStepID", currentStepID);
        } catch (NullPointerException e){
            Log.d(StepsActivity.class.getSimpleName(), "Unable to save videoPosition");
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
            return true;
        }
        return onOptionsItemSelected(item);
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}
