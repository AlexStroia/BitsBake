package co.alexdev.bitsbake.ui.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.adapter.RecipesAdapter;
import co.alexdev.bitsbake.databinding.FragmentRecipesBinding;
import co.alexdev.bitsbake.model.response.Recipe;
import co.alexdev.bitsbake.viewmodel.MainViewModel;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipesFragment extends BaseFragment {

    private RecipesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private FragmentRecipesBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_recipes, container, false);
        final View rootView = mBinding.getRoot();
        vm = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);

        initRecycler();
        return rootView;
    }

    private void initRecycler() {
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new RecipesAdapter(new ArrayList<>());
        mBinding.rvRecipes.setLayoutManager(mLayoutManager);
        mBinding.rvRecipes.setAdapter(mAdapter);
        vm.getRecipes().observe(this.getActivity(), recipes -> {
            mAdapter.setRecipes(recipes);
            for (Recipe recipe : recipes) {
                Timber.d(recipe.getName());
            }
        });
    }
}
