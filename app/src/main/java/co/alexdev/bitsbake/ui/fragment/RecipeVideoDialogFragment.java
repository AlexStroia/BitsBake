package co.alexdev.bitsbake.ui.fragment;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
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
import timber.log.Timber;

public class RecipeVideoDialogFragment extends DialogFragment {

    private View rootView;
    private ExoPlayer mPlayer;
    private FragmentVideoDialogBinding mBinding;
    private Bundle arguments;
    private String recipe_url;
    private String recipeDescription;
    private Step step = null;
    private RecipeDetailSharedVM vm;

    @Override
    public void onResume() {
        if (vm.canDisplayVideo()) {
            if (vm.isTextValid(recipe_url)) initPlayer(Uri.parse(recipe_url));
            mPlayer.seekTo(vm.getExoPlayerPos());
            mPlayer.setPlayWhenReady(vm.isExoReadyToPlay());
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        if (Build.VERSION.SDK_INT >= 24) {
            if (mPlayer != null) {
                vm.setExoPlayerPos(mPlayer.getCurrentPosition());
                vm.setExoReadyToPlay(mPlayer.getPlayWhenReady());
                releasePlayer();
            }
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        if (Build.VERSION.SDK_INT < 23) {
            if (mPlayer != null) {
                vm.setExoPlayerPos(mPlayer.getCurrentPosition());
                vm.setExoReadyToPlay(mPlayer.getPlayWhenReady());
            }
            releasePlayer();
        }
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView(container);

        mBinding.exoplayer.setVisibility(vm.canDisplayVideo() ? View.VISIBLE : View.GONE);
        if (vm.canDisplayVideo()) initPlayer(Uri.parse(recipe_url));
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
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_video_dialog, container, false);
        getArgs();
        setStepsList();
        rootView = mBinding.getRoot();
        setBtnListeners();
    }

    private void setBtnListeners() {
        mBinding.tvDesc.setText(recipeDescription);
        mBinding.ibNext.setOnClickListener(view -> {
            vm.goToNext(vm.getSteps());
            vm.setStep(vm.getSteps().get(vm.getStepListPos()));
            showHideButtons();
            setLayout();
        });

        mBinding.ibPrev.setOnClickListener(view -> {
            vm.goToPrev();
            vm.setStep(vm.getSteps().get(vm.getStepListPos()));
            showHideButtons();
            setLayout();
        });
    }

    /*Show or hide buttons based on the boolean value */
    private void showHideButtons() {
        mBinding.ibPrev.setVisibility(vm.shouldShowPrevBtn() ? View.VISIBLE : View.INVISIBLE);
        mBinding.ibNext.setVisibility(vm.shouldShowNextBtn() ? View.VISIBLE : View.INVISIBLE);
    }

    private void setStepsList() {
        LiveData<List<Step>> stepsLiveData = vm.getStepsById();
        stepsLiveData.observe(this.getActivity(), steps -> {
            vm.setSteps(steps);
            showHideButtons();
            stepsLiveData.removeObservers(this);
        });
    }

    private void getArgs() {
        arguments = getArguments();
        String stepObjKey = getString(R.string.step_obj_key);
        String stepPosKey = getString(R.string.step_pos);

        if (arguments != null) {
            if (arguments.containsKey(stepObjKey) && arguments.containsKey(stepPosKey)) {
                step = arguments.getParcelable(stepObjKey);
                int position = arguments.getInt(stepPosKey);
                vm.setStep(step);
                vm.setStepListPos(position);
                setLayout();
            }
        }
    }

    private void setLayout() {
        if (vm.isTextValid(vm.getStep().getDescription())) {
            recipeDescription = vm.getStep().getDescription();
        }
        if (vm.isTextValid(vm.getStep().getVideoURL())) {
            prepareLayoutForVideoUrl();
            return;
        } else if (vm.isTextValid((vm.getStep().getThumbnailUrl()))) {
            prepareLayoutForThumbnailUrl();
            return;
        } else {
            prepareLayoutForNoVideoAndNoThumbnail();
            vm.setCanDisplayVideo(false);
        }
    }

    private void prepareLayoutForNoVideoAndNoThumbnail() {
        /*Means that there is no video and no image */
        mBinding.tvDesc.setText(vm.getStep().getDescription());
        mBinding.exoplayer.setVisibility(View.GONE);
        mBinding.ivRecipeImage.setVisibility(View.GONE);
        /*If it came here then we cannot display a video */
    }

    private void prepareLayoutForVideoUrl() {
        mBinding.exoplayer.setVisibility(View.VISIBLE);
        vm.setCanDisplayVideo(true);
        recipe_url = vm.getStep().getVideoURL();
        if (mPlayer != null) {
            mPlayer.prepare(buildMediaSource(Uri.parse(vm.getStep().getVideoURL())));
        }
        mBinding.tvDesc.setText(recipeDescription);
    }

    private void prepareLayoutForThumbnailUrl() {
        mBinding.ivRecipeImage.setVisibility(View.VISIBLE);
        vm.setCanDisplayVideo(false);
        Uri imageUri = Uri.parse(vm.getStep().getThumbnailUrl());
        mBinding.tvDesc.setText(recipeDescription);
        Picasso.get().load(imageUri).into(mBinding.ivRecipeImage);
    }
}
