package co.alexdev.bitsbake.ui.fragment;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.adapter.RecipesAdapter;
import co.alexdev.bitsbake.databinding.FragmentRecipesBinding;
import co.alexdev.bitsbake.model.Recipe;
import co.alexdev.bitsbake.viewmodel.BaseVM;


public class RecipesFragment extends BaseFragment{

    private RecipesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<Recipe> mRecipes;
    private FragmentRecipesBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_recipes, container, false);
        final View rootView = mBinding.getRoot();
        vm = ViewModelProviders.of(this.getActivity()).get(BaseVM.class);

        initRecycler();
        return rootView;
    }

    private void initRecycler() {
        mLayoutManager = new LinearLayoutManager(this.getActivity());
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