package co.alexdev.bitsbake.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.adapter.RecipesAdapter;
import co.alexdev.bitsbake.databinding.FragmentRecipesBinding;
import co.alexdev.bitsbake.model.Recipe;
import co.alexdev.bitsbake.ui.activity.BaseActivity;
import co.alexdev.bitsbake.viewmodel.SharedVM;

public class RecipesFragment extends BaseFragment {

    private RecipesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Recipe> mRecipes;
    private FragmentRecipesBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_recipes, container, false);
        final View rootView = mBinding.getRoot();
        vm = ViewModelProviders.of(this.getActivity()).get(SharedVM.class);

        initRecycler();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        saveRecyclerViewState(outState, mLayoutManager);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        checkRecyclerViewState(savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onResume() {
        if (recyclerViewState != null) mLayoutManager.onRestoreInstanceState(recyclerViewState);
        super.onResume();
    }

    private void initRecycler() {
        boolean mTwoPane = ((BaseActivity) getActivity()).mTwoPane;
        mLayoutManager = (mTwoPane) ? new GridLayoutManager(this.getActivity(), 2) : new LinearLayoutManager(this.getActivity());

        mAdapter = new RecipesAdapter(new ArrayList<>());
        mBinding.rvRecipes.setLayoutManager(mLayoutManager);
        mBinding.rvRecipes.setAdapter(mAdapter);
        vm.getRecipes().observe(this.getActivity(),
                recipes -> {
                    mRecipes = recipes;
                    mAdapter.setRecipes(mRecipes);
                });
    }
}