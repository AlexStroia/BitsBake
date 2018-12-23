package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.adapter.IngredientsAdapter;
import co.alexdev.bitsbake.adapter.StepsAdapter;
import co.alexdev.bitsbake.databinding.FragmentRecipeDetailBinding;
import co.alexdev.bitsbake.model.model.Ingredient;
import co.alexdev.bitsbake.model.model.Step;
import co.alexdev.bitsbake.utils.Constants;
import co.alexdev.bitsbake.viewmodel.MainViewModel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class RecipesDetailFragment extends BaseFragment {

    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;
    private LinearLayoutManager mLayoutManager;
    private FragmentRecipeDetailBinding mBinding;
    private @Constants.RecyclerType
    int mRecyclerViewType = Constants.RECYCLER_INGREDIENT_LAYOUT;
    private boolean canScroll = false;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initView(container);
        initRecycler();
        initBottomNavigationView();

        mBinding.btnNext.setOnClickListener(view -> mLayoutManager.scrollToPosition(mLayoutManager.findLastVisibleItemPosition() + 1));

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
            switch (mRecyclerViewType) {
                case Constants.RECYCLER_INGREDIENT_LAYOUT:
                    configureIngredientsAdapter();
                    canScroll = false;
                    LiveData<List<Ingredient>> ingredientsObserver = vm.getIngredientsByName(name);
                    ingredientsObserver.observe(this, ingredients -> mIngredientsAdapter.setList(ingredients));
                    break;

                case Constants.RECYCLER_STEPS_LAYOUT:
                    configureStepsAdapter();
                    canScroll = true;
                    LiveData<List<Step>> stepObserver = vm.getStepsByName(name);
                    stepObserver.observe(this, steps -> mStepsAdapter.setList(steps));
                    break;
            }
            mBinding.rvDetails.setOnTouchListener((view, motionEvent) -> canScroll);
        }
    }

    private void configureIngredientsAdapter() {
        mIngredientsAdapter = new IngredientsAdapter(new ArrayList<>());
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mBinding.rvDetails.setLayoutManager(mLayoutManager);
        mBinding.rvDetails.setAdapter(mIngredientsAdapter);
    }

    private void configureStepsAdapter() {
        mStepsAdapter = new StepsAdapter(new ArrayList<>(), this.getActivity());
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mBinding.rvDetails.setLayoutManager(mLayoutManager);
        mBinding.rvDetails.setAdapter(mStepsAdapter);
    }

    private void initBottomNavigationView() {
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int menuID = menuItem.getItemId();
            mRecyclerViewType = (menuID == R.id.mnu_igredients) ? Constants.RECYCLER_INGREDIENT_LAYOUT : Constants.RECYCLER_STEPS_LAYOUT;
            initRecycler();
            return true;
        });
    }

    private void releasePlayer() {
        //TODO - release player
    }
}
