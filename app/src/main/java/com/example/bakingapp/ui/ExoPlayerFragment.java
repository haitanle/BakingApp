package com.example.bakingapp.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class ExoPlayerFragment extends Fragment {

    private static final String TAG = ExoPlayerFragment.class.getSimpleName();

    private TextView stepsTextView;
    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private SimpleExoPlayerView mPlayerView;
    private ImageView thumbNaiView;

    private boolean isPlayWhenReady;
    private long currentVideoPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeMediaSession();
        initializePlayer();
    }

    /**
     * Create a new instance of ExoPlayerFragment
     */
    public static ExoPlayerFragment newInstance(int index){
        ExoPlayerFragment f = new ExoPlayerFragment();

        // Supply index input as an argument
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownInsdex() {
        return getArguments().getInt("index", 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.activity_player_view, container, false);
        rootView.findViewById(R.id.buttonPrevious).setVisibility(View.GONE);
        rootView.findViewById(R.id.buttonNext).setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        stepsTextView = (TextView) view.findViewById(R.id.description_textView);
        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.playerView);
        mPlayerView.setPlayer(mExoPlayer);
        thumbNaiView = (ImageView) view.findViewById(R.id.thumbNail_imageView);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentVideoPosition = 0;
        isPlayWhenReady = true;

        if (savedInstanceState != null && savedInstanceState.getLong(getString(R.string.currentVideoPosition)) != 0){
            currentVideoPosition = savedInstanceState.getLong(getString(R.string.currentVideoPosition));
            isPlayWhenReady = savedInstanceState.getBoolean(getString(R.string.isPlayWhenReady_key));
        }
        onStart();
    }

    /**
     * Once step is selected, set the video and step instruction
     */
    @Override
    public void onStart() {
        super.onStart();

        Steps recipeStep= Recipe.getRecipeByID(getContext(), MainActivity.recipeSelected).getStepsList().get(getShownInsdex());

        stepsTextView.setText(recipeStep.getDescription());
        Uri videoURL = Uri.parse(recipeStep.getVideoUrl());

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

            MediaSource mediaSource = new ExtractorMediaSource(videoURL, new DefaultDataSourceFactory(
                    getContext(), Util.getUserAgent(getContext(), "BakingApp")), new DefaultExtractorsFactory(), null, null);

            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(currentVideoPosition);
            mExoPlayer.setPlayWhenReady(isPlayWhenReady);
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
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

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
        mMediaSession.setCallback(new ExoPlayerFragment.MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);
    }

    /**
     * Initialize ExoPlayer.
     */
    private void initializePlayer() {
        if (mExoPlayer == null) {

            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        }
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
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause(){
        super.onPause();

        //save the player state before pausing
        currentVideoPosition = mExoPlayer.getCurrentPosition();
        isPlayWhenReady = mExoPlayer.getPlayWhenReady();

        mExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onResume() {
        super.onResume();
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        try {
            long videoCurrentPosition = mExoPlayer.getCurrentPosition();

            outState.putLong(getString(R.string.currentVideoPosition), videoCurrentPosition);
            outState.putBoolean(getString(R.string.isPlayWhenReady_key), isPlayWhenReady);

        } catch (NullPointerException e){
            Log.d(StepsActivity.class.getSimpleName(), "Unable to save videoPosition");
            e.printStackTrace();
        }
    }
}
