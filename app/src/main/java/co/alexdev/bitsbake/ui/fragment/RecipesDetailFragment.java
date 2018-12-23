package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.adapter.IngredientsAdapter;
import co.alexdev.bitsbake.databinding.FragmentRecipeDetailBinding;
import co.alexdev.bitsbake.viewmodel.MainViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipesDetailFragment extends BaseFragment {

    private IngredientsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private FragmentRecipeDetailBinding mBinding;
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
        mAdapter = new IngredientsAdapter(new ArrayList<>());
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mBinding.rvDetails.setLayoutManager(mLayoutManager);
        mBinding.rvDetails.setAdapter(mAdapter);
    }

    private void loadDataForRecycler() {
        Bundle args = getArguments();
        String recipeName = getString(R.string.recipe_name);
        if (args != null && args.containsKey(recipeName)) {
            String name = args.getString(recipeName);
            vm.getIngredientsByCake(name).observe(this, ingredients -> mAdapter.setList(ingredients));
        }
    }

    private void initBottomNavigationView() {
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int menuID = menuItem.getItemId();
            switch (menuID) {
                case R.id.mnu_igredients:

                    break;

                case R.id.mnu_description:
                    changeFragment(new DescriptionFragment());

                    break;
            }

            return true;
        });
    }
}
