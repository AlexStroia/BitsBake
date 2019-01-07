package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.adapter.StepsAdapter;
import co.alexdev.bitsbake.databinding.FragmentRecipeDetailBinding;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import co.alexdev.bitsbake.viewmodel.BaseVM;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class RecipesDetailFragment extends BaseFragment {

    private StepsAdapter mStepsAdapter;
    private LinearLayoutManager mLayoutManager;
    private FragmentRecipeDetailBinding mBinding;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initView(container);
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

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void initView(ViewGroup container) {
        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.fragment_recipe_detail, container,
                false);
        rootView = mBinding.getRoot();
        vm = ViewModelProviders.of(this.getActivity()).get(BaseVM.class);

        mBinding.btnUpdateWidget.setOnClickListener(view -> vm.onWidgetUpdateClick());
    }

    private void initRecycler() {
        Bundle args = getArguments();
        String recipeKey = getString(R.string.recipe_id);
        if (args != null && args.containsKey(recipeKey)) {
            configureStepsAdapter();
            int id = args.getInt(recipeKey);
            configureIngredientsTextView(id);
        }
    }

    private void configureStepsAdapter() {
        mStepsAdapter = new StepsAdapter(new ArrayList<>());
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        mBinding.rvDetails.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mBinding.rvDetails.getContext(), mLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getActivity().getDrawable(R.color._black));
        mBinding.rvDetails.addItemDecoration(dividerItemDecoration);
        mBinding.rvDetails.setAdapter(mStepsAdapter);
    }

    private void configureIngredientsTextView(int id) {
        vm.setId(id);
        vm.getIngredientById().observe(this,
                ingredients -> {
                    String formattedIngredients = BitsBakeUtils.buildIngredientsTextView(ingredients);
                    mBinding.tvIngredients.setText(formattedIngredients);
                    if (ingredients.size() > 0) {
                        vm.setRecipeName(ingredients.get(0).getCake());
                        vm.setSharedPrefIngredientId(ingredients.get(0).getId());
                    }
                });

        vm.getStepsById().observe(this, steps -> mStepsAdapter.setList(steps));
    }
}
