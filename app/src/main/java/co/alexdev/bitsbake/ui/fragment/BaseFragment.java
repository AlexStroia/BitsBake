package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.FragmentBaseBinding;
import co.alexdev.bitsbake.utils.Constants;
import co.alexdev.bitsbake.viewmodel.MainViewModel;

public class BaseFragment extends Fragment {

    MainViewModel vm;

    private View rootView;
    private FragmentManager mFragmentManager;
    private FragmentBaseBinding mBinding;
    private String recipeName;
    private String argsKey;
    private Bundle args;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initView(container);
        return rootView;
    }

    private void initView(ViewGroup container) {

        args = getArguments();
        argsKey = getString(R.string.recipe_name);
        if (args != null && args.containsKey(argsKey)) {
            recipeName = args.getString(argsKey);
        }

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_base, container, false);
        mFragmentManager = getChildFragmentManager();
        rootView = mBinding.getRoot();

        RecipesDetailFragment recipesDetailFragment = new RecipesDetailFragment();
        recipesDetailFragment.setArguments(args);
        changeFragment(recipesDetailFragment);

        initBottomNavigationView();
    }

    private void initBottomNavigationView() {
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            int menuID = menuItem.getItemId();
            args.putString(getString(R.string.recipe_name), recipeName);
            menuItem.setChecked(true);

            switch (menuID) {
                case R.id.mnu_igredients:
                    RecipesDetailFragment recipesDetailFragment = new RecipesDetailFragment();
                    recipesDetailFragment.setArguments(args);
                    changeFragment(recipesDetailFragment);
                    return false;

                case R.id.mnu_description:
                    RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
                    recipeStepFragment.setArguments(args);
                    changeFragment(recipeStepFragment);
                    break;
            }
            return true;
        });
    }

    public void changeFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().
                replace(R.id.fragment_container, fragment).
                commit();
    }
}

