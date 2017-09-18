package com.robby.baking_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.robby.baking_app.entity.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeIngredientActivity}
 * on handsets.
 *
 * @author Robby Tan
 */
public class RecipeStepFragment extends Fragment {

    @BindView(R.id.tv_step_description)
    TextView tvStepDescription;
    @BindView(R.id.epv_step_video)
    SimpleExoPlayerView recipeStepVideo;
    private SimpleExoPlayer player;

    private RecipeStep recipeStep;
    private long playerPosition;

    public RecipeStepFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(this.getActivity().getResources()
                .getString(R.string.send_parcelable_recipe_step))) {
            recipeStep = this.getArguments().getParcelable(this.getActivity()
                    .getResources().getString(R.string.send_parcelable_recipe_step));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveVideoCurrentPosition();
        player.setPlayWhenReady(false);
        player.release();
        player = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
        recipeStepVideo.setPlayer(player);
        player.setPlayWhenReady(true);
        loadVideoCurrentPosition();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(getResources().getString(R.string.restore_state), playerPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            player.seekTo(savedInstanceState.getLong(getResources().getString(R.string.restore_state)));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        tvStepDescription.setText(recipeStep.getDescription());
        recipeStepVideo.setPlayer(player);
        if (TextUtils.isEmpty(recipeStep.getVideoURL())) {
            recipeStepVideo.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.initializePlayer();
        return inflater.inflate(R.layout.step_detail, container, false);
    }

    private void initializePlayer() {
        if (player == null) {
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this.getActivity(),
                    Util.getUserAgent(this.getActivity(), "Baking App"), bandwidthMeter);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource videoSource = new ExtractorMediaSource(
                    Uri.parse(recipeStep.getVideoURL()).buildUpon().build(),
                    dataSourceFactory, extractorsFactory, null, null);

            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            player = ExoPlayerFactory.newSimpleInstance(this.getActivity(), trackSelector);
            player.prepare(videoSource);
            player.setPlayWhenReady(true);
        }
    }

    private void saveVideoCurrentPosition() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(
                getResources().getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        playerPosition = player.getCurrentPosition();
        editor.putLong(getResources().getString(R.string.shared_pref_key_video_position),
                player.getCurrentPosition());
        editor.apply();
    }

    private void loadVideoCurrentPosition() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(
                getResources().getString(R.string.shared_pref_name),
                Context.MODE_PRIVATE);
        player.seekTo(sharedPreferences.getLong(getResources().getString(
                R.string.shared_pref_key_video_position), 0));
    }
}
