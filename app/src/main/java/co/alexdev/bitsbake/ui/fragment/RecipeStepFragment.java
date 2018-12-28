package co.alexdev.bitsbake.ui.fragment;


import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.adapter.StepsAdapter;
import co.alexdev.bitsbake.databinding.FragmentRecipeStepBinding;
import co.alexdev.bitsbake.model.Step;
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
    }

    private void initRecycler() {

        Bundle args = getArguments();
        String recipeName = getString(R.string.recipe_id);
        if (args != null && args.containsKey(recipeName)) {
            configureStepAdapter();
        }

        /*Block the clicks on the recycler view*/
        mBinding.rvDetails.setOnTouchListener((view, motionEvent) -> true);
    }

    private void configureStepAdapter() {
        mStepAdapter = new StepsAdapter(new ArrayList<>());
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mBinding.rvDetails.setAdapter(mStepAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.rvDetails.getContext(), mLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getActivity().getDrawable(R.color._black));
        mBinding.rvDetails.addItemDecoration(dividerItemDecoration);
        mBinding.rvDetails.setLayoutManager(mLayoutManager);
    }
}
