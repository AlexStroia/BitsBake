package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.FragmentRecipeStepBinding;
import co.alexdev.bitsbake.viewmodel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepFragment extends BaseFragment {

    private View rootView;
    private FragmentRecipeStepBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        initView(container);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView(ViewGroup container) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_recipe_step, container, false);
        vm = ViewModelProviders.of(this.getActivity()).get(MainViewModel.class);

        rootView = mBinding.getRoot();
    }
}
