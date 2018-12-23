package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.adapter.IngredientsAdapter;
import co.alexdev.bitsbake.adapter.StepsAdapter;
import co.alexdev.bitsbake.databinding.FragmentRecipeDetailBinding;
import co.alexdev.bitsbake.model.model.Ingredient;
import co.alexdev.bitsbake.model.model.Step;
import co.alexdev.bitsbake.utils.Constants;
import co.alexdev.bitsbake.viewmodel.MainViewModel;
import timber.log.Timber;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
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
        // Inflate the layout for this fragment

        initView(container);
        initRecycler();
        loadDataForRecycler();
        initBottomNavigationView();

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
        mIngredientsAdapter = new IngredientsAdapter(new ArrayList<>());
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mBinding.rvDetails.setLayoutManager(mLayoutManager);
        mBinding.rvDetails.setAdapter(mIngredientsAdapter);
    }

    private void loadDataForRecycler() {
        Bundle args = getArguments();
        String recipeName = getString(R.string.recipe_name);
        if (args != null && args.containsKey(recipeName)) {
            String name = args.getString(recipeName);
            switch (mRecyclerViewType) {
                case Constants.RECYCLER_INGREDIENT_LAYOUT:
                    canScroll = false;
                    LiveData<List<Ingredient>> ingredientsObserver = vm.getIngredientsByName(name);
                    ingredientsObserver.observe(this, ingredients -> {
                        initRecycler();
                        mIngredientsAdapter.setList(ingredients);
                    });
                    break;

                case Constants.RECYCLER_STEPS_LAYOUT:
                    LiveData<List<Step>> stepObserver = vm.getStepsByName(name);
                    stepObserver.observe(this, steps -> {
                        canScroll = true;
                        mStepsAdapter = new StepsAdapter(new ArrayList<>());
                        mBinding.rvDetails.setAdapter(mStepsAdapter);
                        Timber.d("Steps: " + steps);
                        mStepsAdapter.setList(steps);
                    });
                    break;
            }
            mBinding.rvDetails.setOnTouchListener((view, motionEvent) -> canScroll);
        }
    }

    private void initBottomNavigationView() {
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int menuID = menuItem.getItemId();
            switch (menuID) {
                case R.id.mnu_igredients:
                    mRecyclerViewType = Constants.RECYCLER_INGREDIENT_LAYOUT;
                    loadDataForRecycler();
                    break;

                case R.id.mnu_description:
                    mRecyclerViewType = Constants.RECYCLER_STEPS_LAYOUT;
                    loadDataForRecycler();
                    break;
            }
            return true;
        });
    }
}
