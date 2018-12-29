package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.FragmentBaseBinding;
import co.alexdev.bitsbake.events.OnRecipeStepClickEvent;
import co.alexdev.bitsbake.model.Step;
import co.alexdev.bitsbake.viewmodel.BaseVM;

public class BaseFragment extends Fragment {

    BaseVM vm;
    private View rootView;
    private FragmentManager mFragmentManager;
    private FragmentBaseBinding mBinding;
    private String argsKey;
    private Bundle args;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initView(container);
        return rootView;
    }

    private void initView(ViewGroup container) {

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_base, container, false);
        args = getArguments();
        argsKey = getString(R.string.recipe_id);
        if (args != null && args.containsKey(argsKey)) {
            mFragmentManager = getChildFragmentManager();
            rootView = mBinding.getRoot();

            RecipesDetailFragment recipesDetailFragment = new RecipesDetailFragment();
            recipesDetailFragment.setArguments(args);
            changeFragment(recipesDetailFragment);
        }
    }

    @Override
    public void onStart() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();

    }

    public void changeFragment(Fragment fragment) {
        if (fragment instanceof RecipeVideoDialogFragment) {
            if (mFragmentManager == null) mFragmentManager = getChildFragmentManager();
            ((RecipeVideoDialogFragment) fragment).show(mFragmentManager, null);
        } else {
            mFragmentManager.beginTransaction().
                    replace(R.id.fragment_container, fragment).
                    commit();
        }
    }

    @Subscribe
    public void onRecipeStepClickEvent(OnRecipeStepClickEvent event) {

        Step step = event.getStep();
        if (step != null) {
            if (vm == null) vm = ViewModelProviders.of(this.getActivity()).get(BaseVM.class);
            RecipeVideoDialogFragment recipeVideoDialogFragment = new RecipeVideoDialogFragment();
            args.putInt(argsKey, step.getId());
            changeFragment(recipeVideoDialogFragment);
        } else {
            Toast.makeText(this.getActivity(), "Video Not", Toast.LENGTH_LONG).show();
        }
    }
}


