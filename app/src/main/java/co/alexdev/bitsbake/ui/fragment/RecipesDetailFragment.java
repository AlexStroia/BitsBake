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
import co.alexdev.bitsbake.model.Ingredient;
import co.alexdev.bitsbake.utils.BitsBakeUtils;
import co.alexdev.bitsbake.viewmodel.RecipeDetailSharedVM;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class RecipesDetailFragment extends BaseFragment {

    private static final String SCROLL_VIEW_POS = "SCROLL_VIEW_POSITION";
    private StepsAdapter mStepsAdapter;
    private LinearLayoutManager mLayoutManager;
    private FragmentRecipeDetailBinding mBinding;
    private View rootView;
    private int[] scrollPosition;

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
        saveScrollViewState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        checkRecyclerViewState(savedInstanceState);
        checkScrollViewState(savedInstanceState);
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onResume() {
        if (recyclerViewState != null) mLayoutManager.onRestoreInstanceState(recyclerViewState);
        if (scrollPosition != null && scrollPosition.length == 2) {
            mBinding.nestedScroll.scrollTo(scrollPosition[0], scrollPosition[1]);
        }
        super.onResume();
    }

    private void initView(ViewGroup container) {
        mBinding = DataBindingUtil.inflate(
                getLayoutInflater(),
                R.layout.fragment_recipe_detail, container,
                false);
        rootView = mBinding.getRoot();
        vm = ViewModelProviders.of(this.getActivity()).get(RecipeDetailSharedVM.class);
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
        mBinding.rvDetails.addItemDecoration(dividerItemDecoration);
        mBinding.rvDetails.setAdapter(mStepsAdapter);
    }

    private void configureIngredientsTextView(int id) {
        vm.setId(id);
        vm.getIngredientById().observe(this,
                ingredients -> {
                    String formattedIngredients = BitsBakeUtils.buildIngredientsTextView(ingredients);
                    if (!vm.isFieldValid(ingredients, formattedIngredients)) return;
                    Ingredient ingredient = ingredients.get(0);
                    String cakeName = ingredient.getCake();
                    mBinding.tvIngredients.setText(formattedIngredients);
                    mBinding.tvCakeName.setText(cakeName);
                    vm.setRecipeName(cakeName);
                    vm.setSharedPrefIngredientId(ingredient.getId());
                });
        vm.getStepsById().observe(this, steps -> mStepsAdapter.setList(steps));
    }

    private void saveScrollViewState(@NonNull Bundle outState) {
        outState.putIntArray(SCROLL_VIEW_POS, new int[]{mBinding.nestedScroll.getScrollX(), mBinding.nestedScroll.getScrollY()});
    }

    private void checkScrollViewState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(SCROLL_VIEW_POS)) {
            scrollPosition = savedInstanceState.getIntArray(SCROLL_VIEW_POS);
        }
    }
}
