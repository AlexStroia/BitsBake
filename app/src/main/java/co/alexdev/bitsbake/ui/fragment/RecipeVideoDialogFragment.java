package co.alexdev.bitsbake.ui.fragment;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

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
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.FragmentVideoDialogBinding;
import co.alexdev.bitsbake.model.Step;
import co.alexdev.bitsbake.viewmodel.RecipeDetailSharedVM;

public class RecipeVideoDialogFragment extends DialogFragment implements Player.EventListener {

    private View rootView;
    private ExoPlayer mPlayer;
    private FragmentVideoDialogBinding mBinding;
    private Bundle arguments;
    private String recipe_url;
    private String recipeDescription;
    private String stepObjKey;
    private Step step = null;
    private RecipeDetailSharedVM vm;

    @Override
    public void onResume() {
        if (vm.isCanDisplayVideo()) {
            if (vm.isTextValid(recipe_url)) initPlayer(Uri.parse(recipe_url));
            mPlayer.seekTo(vm.getExoPlayerPos());
            mPlayer.setPlayWhenReady(vm.isExoReadyToPlay());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView(container);

        mBinding.exoplayer.setVisibility(vm.isCanDisplayVideo() ? View.VISIBLE : View.GONE);
        if (vm.isCanDisplayVideo()) loadData();
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
            mBinding.exoplayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
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
        vm = ViewModelProviders.of(this.getActivity()).get(RecipeDetailSharedVM.class);
        getArgs();

        setStepsList();

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_video_dialog, container, false);
        rootView = mBinding.getRoot();

        setBtnListeners();
    }

    private void setBtnListeners() {
        mBinding.tvDesc.setText(recipeDescription);
        mBinding.ibNext.setOnClickListener(view -> {
            vm.goToNext(vm.getSteps());
            vm.setStep(vm.getSteps().get(vm.getStepListPos()));
            mBinding.ibPrev.setVisibility(vm.shouldShowPrevBtn() ? View.VISIBLE : View.GONE);
            mBinding.ibNext.setVisibility(vm.shouldShowNextBtn() ? View.VISIBLE : View.INVISIBLE);

        });

        mBinding.ibPrev.setOnClickListener(view -> {
            vm.goToPrev();
            vm.setStep(vm.getSteps().get(vm.getStepListPos()));
            mBinding.ibPrev.setVisibility(vm.shouldShowPrevBtn() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void setStepsList() {
        LiveData<List<Step>> stepsLiveData = vm.getStepsById();
        stepsLiveData.observe(this.getActivity(), steps -> {
            vm.setSteps(steps);
            stepsLiveData.removeObservers(this);
        });
    }

    private void loadData() {
        if (vm.isCanDisplayVideo()) initPlayer(Uri.parse(recipe_url));
    }

    private void getArgs() {
        arguments = getArguments();
        setKeys();
        if (arguments != null) {
            if (arguments.containsKey(stepObjKey)) {
                step = arguments.getParcelable(stepObjKey);
                vm.setStep(step);
                validateFields();
            }
        }
    }

    private void validateFields() {
        if (vm.isTextValid(vm.getStep().getDescription())) {
            recipeDescription = vm.getStep().getDescription();
        }
        if (vm.isTextValid(vm.getStep().getVideoURL())) {
            prepareLayoutForVideoUrl();
            return;
        } else if (vm.isTextValid((vm.getStep().getThumbnailUrl()))) {
            prepareLayoutForThumbnailUrl();
            return;
        }
        vm.setCanDisplayVideo(false);
    }

    private void setKeys() {
        stepObjKey = getString(R.string.step_obj_key);
    }

    private void prepareLayoutForVideoUrl() {
        vm.setCanDisplayVideo(true);
        recipe_url = vm.getStep().getVideoURL();
    }

    private void prepareLayoutForThumbnailUrl() {
        vm.setCanDisplayVideo(false);
        Uri imageUri = Uri.parse(vm.getStep().getThumbnailUrl());
        mBinding.ivRecipeImage.setVisibility(View.VISIBLE);
        Picasso.get().load(imageUri).into(mBinding.ivRecipeImage);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray
            trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        vm.setExoPlayerPos(mPlayer.getCurrentPosition());
        vm.setExoReadyToPlay(playWhenReady);
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
