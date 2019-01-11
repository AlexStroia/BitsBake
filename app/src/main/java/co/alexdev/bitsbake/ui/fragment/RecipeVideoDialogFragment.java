package co.alexdev.bitsbake.ui.fragment;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.FragmentVideoDialogBinding;
import co.alexdev.bitsbake.utils.Validator;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeVideoDialogFragment extends DialogFragment implements Player.EventListener {

    private static final String EXO_POS = "exo_player_pos";
    private View rootView;
    private ExoPlayer mPlayer;
    private FragmentVideoDialogBinding mBinding;
    private Bundle arguments;
    private String recipe_url_key;
    private String recipe_desc_key;
    private String recipe_url;
    private String recipe_thumbnail_url_key;
    private String recipeDescription;
    private long mExoPos = 0;
    private boolean canDisplayVideo = false;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(recipe_url_key, recipe_url);
        outState.putLong(EXO_POS, mExoPos);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        getViewState(savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onResume() {
        if (canDisplayVideo) {
            if (Validator.isTextValid(recipe_url)) initPlayer(Uri.parse(recipe_url));
            mPlayer.seekTo(mExoPos);
            mPlayer.setPlayWhenReady(true);
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        if (Build.VERSION.SDK_INT >= 24) releasePlayer();
        super.onStop();
    }

    @Override
    public void onPause() {
        if (Build.VERSION.SDK_INT <= 23) releasePlayer();
        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getArgs();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView(container);

        mBinding.exoplayer.setVisibility(canDisplayVideo ? View.VISIBLE : View.GONE);
        if (canDisplayVideo) loadData();
        return rootView;
    }

    private void initPlayer(Uri mediaUri) {
        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(this.getActivity()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            mBinding.exoplayer.setPlayer(mPlayer);
            mPlayer.prepare(buildMediaSource(mediaUri));
            mPlayer.setPlayWhenReady(true);
            mPlayer.addListener(this);
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        String userAgent = Util.getUserAgent(this.getActivity(), getString(R.string.app_name));
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri);
    }

    private void initView(ViewGroup container) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_video_dialog, container, false);
        rootView = mBinding.getRoot();
        mBinding.tvDesc.setText(recipeDescription);
    }

    private void loadData() {
        if (canDisplayVideo) initPlayer(Uri.parse(recipe_url));
    }

    private void getArgs() {
        arguments = getArguments();
        setKeys();
        if (arguments != null) {
            if (arguments.containsKey(recipe_desc_key)) {
                recipeDescription = arguments.getString(recipe_desc_key);
            }
            if (arguments.containsKey(recipe_url_key)) {
                prepareLayoutForVideoUrl();
            } else if (arguments.containsKey(recipe_thumbnail_url_key)) {
                prepareLayoutForThumbnailUrl();
            }
        }
    }

    private void setKeys() {
        recipe_url_key = getString(R.string.recipe_id);
        recipe_desc_key = getString(R.string.recipe_desc_id);
        recipe_thumbnail_url_key = getString(R.string.recipe_cake_thumbnail_url);
    }

    private void prepareLayoutForVideoUrl() {
        canDisplayVideo = true;
        recipe_url = arguments.getString(recipe_url_key);
    }

    private void prepareLayoutForThumbnailUrl() {
        canDisplayVideo = false;
        Uri imageUri = Uri.parse(arguments.getString(recipe_thumbnail_url_key));
        mBinding.ivRecipeImage.setVisibility(View.VISIBLE);
        Picasso.get().load(imageUri).into(mBinding.ivRecipeImage);
    }

    private void getViewState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(recipe_url_key) && savedInstanceState.containsKey(EXO_POS)) {
            recipe_url = savedInstanceState.getString(recipe_url_key);
            mExoPos = savedInstanceState.getLong(EXO_POS);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        mExoPos = mPlayer.getCurrentPosition();
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
