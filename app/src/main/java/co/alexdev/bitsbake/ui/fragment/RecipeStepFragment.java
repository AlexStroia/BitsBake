package co.alexdev.bitsbake.ui.fragment;


import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.adapter.StepsAdapter;
import co.alexdev.bitsbake.databinding.FragmentRecipeStepBinding;
import co.alexdev.bitsbake.model.model.Step;
import co.alexdev.bitsbake.viewmodel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepFragment extends BaseFragment {

    private View rootView;
    private FragmentRecipeStepBinding mBinding;
    private StepsAdapter mStepAdapter;
    private LinearLayoutManager mLayoutManager;
    private SimpleExoPlayer mExoPlayer;
    private List<Step> steps = new ArrayList<>();
    private int recipePosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        initView(container);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView(ViewGroup container) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_recipe_step, container, false);
        vm = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);

        initRecycler();

        rootView = mBinding.getRoot();

        mBinding.btnNext.setOnClickListener(view -> {
            recipePosition = mLayoutManager.findLastVisibleItemPosition() + 1;
            mLayoutManager.scrollToPosition(mLayoutManager.findLastVisibleItemPosition() + 1);
            initializePlayer(vm.getVideoUrl(steps, recipePosition));
        });
    }

    private void initRecycler() {

        Bundle args = getArguments();
        String recipeName = getString(R.string.recipe_name);
        if (args != null && args.containsKey(recipeName)) {
            String name = args.getString(recipeName);

            configureStepAdapter();
            LiveData<List<Step>> stepsObserver = vm.getStepsByName(name);
            stepsObserver.observe(this, steps -> {
                this.steps = steps;
                int position = mLayoutManager.findFirstVisibleItemPosition();
                if (position == -1) {
                    position = 0;
                }
                String videoURL = steps.get(position).getVideoURL();
                initializePlayer(videoURL);
                mStepAdapter.setList(steps);
            });
        }

        /*Block the clicks on the recycler view*/
        mBinding.rvDetails.setOnTouchListener((view, motionEvent) -> true);
    }

    private void configureStepAdapter() {
        mStepAdapter = new StepsAdapter(new ArrayList<>());
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mBinding.rvDetails.setAdapter(mStepAdapter);
        mBinding.rvDetails.setLayoutManager(mLayoutManager);
    }

    private void initializePlayer(String videoURL) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getActivity(), trackSelector, loadControl);
            mBinding.exoplayer.setPlayer(mExoPlayer);

            //TODO CHECK DEPRECATED CODE
            String userAgent = Util.getUserAgent(this.getActivity().getApplicationContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURL),
                    new DefaultDataSourceFactory(this.getActivity(), userAgent),
                    new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }
}
