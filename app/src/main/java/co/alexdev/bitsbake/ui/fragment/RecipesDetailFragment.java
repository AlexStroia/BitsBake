package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.adapter.StepsAdapter;
import co.alexdev.bitsbake.databinding.FragmentRecipeDetailBinding;
import co.alexdev.bitsbake.model.model.Ingredient;
import co.alexdev.bitsbake.model.model.Step;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import co.alexdev.bitsbake.viewmodel.MainViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

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

    private void initView(ViewGroup container) {

        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.fragment_recipe_detail, container,
                false);
        rootView = mBinding.getRoot();
        vm = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);
    }

    private void initRecycler() {
        Bundle args = getArguments();
        String recipeName = getString(R.string.recipe_name);
        if (args != null && args.containsKey(recipeName)) {
            String name = args.getString(recipeName);

            configureIngredientsAdapter();
            LiveData<List<Step>> stepsObserver = vm.getStepsByName(name);
            LiveData<List<Ingredient>> ingredientObserver = vm.getIngredientsByName(name);
            stepsObserver.observe(this, steps -> mStepsAdapter.setList(steps));
            ingredientObserver.observe(this, ingredients -> mBinding.tvIngredients.setText(BitsBakeUtils.buildIngredientsTextView(ingredients)));
        }
    }

    private void configureIngredientsAdapter() {
        mStepsAdapter = new StepsAdapter(new ArrayList<>());
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mBinding.rvDetails.setLayoutManager(mLayoutManager);
        mBinding.rvDetails.setAdapter(mStepsAdapter);
    }
}
