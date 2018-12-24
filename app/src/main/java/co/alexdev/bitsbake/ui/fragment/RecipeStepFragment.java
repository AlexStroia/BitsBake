package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.adapter.StepsAdapter;
import co.alexdev.bitsbake.databinding.FragmentRecipeStepBinding;
import co.alexdev.bitsbake.model.model.Step;
import co.alexdev.bitsbake.viewmodel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepFragment extends BaseFragment {

    private View rootView;
    private FragmentRecipeStepBinding mBinding;
    private String recipeName;
    private StepsAdapter mStepAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        initView(container);
        return rootView;
    }

    private void initView(ViewGroup container) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_recipe_step, container, false);
        vm = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);

        initRecycler();

        rootView = mBinding.getRoot();
    }

    private void initRecycler() {
        Bundle args = getArguments();
        String recipeName = getString(R.string.recipe_name);
        if (args != null && args.containsKey(recipeName)) {
            String name = args.getString(recipeName);

            configureStepAdapter();
            LiveData<List<Step>> stepsObserver = vm.getStepsByName(name);
            stepsObserver.observe(this, steps -> mStepAdapter.setList(steps));
        }
    }

    private void configureStepAdapter() {
        mStepAdapter = new StepsAdapter(new ArrayList<>(), this.getActivity());
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mBinding.rvDetails.setAdapter(mStepAdapter);
        mBinding.rvDetails.setLayoutManager(mLayoutManager);
    }
}
