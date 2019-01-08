package co.alexdev.bitsbake.ui.fragment;


import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.greenrobot.eventbus.Subscribe;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.FragmentBaseBinding;
import co.alexdev.bitsbake.events.OnRecipeStepClickEvent;
import co.alexdev.bitsbake.model.Step;
import co.alexdev.bitsbake.ui.activity.BaseActivity;
import co.alexdev.bitsbake.utils.Validator;
import co.alexdev.bitsbake.viewmodel.SharedVM;
import timber.log.Timber;

/*Base Fragment class which other fragments will extend*/
public class BaseFragment extends Fragment {

    SharedVM vm;
    private View rootView;
    private FragmentManager mFragmentManager;
    private FragmentBaseBinding mBinding;
    private String recipe_cake_id;
    private String step_key;
    private Bundle args;
    private boolean mTwoPane;

    /*Used to restore recyclerView position when configuration changes occurs */
    static final String RECYCLER_VIEW_POS = "RECYCLER_VIEW_POSITION";
    Parcelable recyclerViewState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(container);
        return rootView;
    }

    /*When this fragment is loaded, load RecipesDetailFragment with the specific args*/
    private void initView(ViewGroup container) {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_base, container, false);
        args = getArguments();

        recipe_cake_id = getString(R.string.recipe_id);
        if (args != null && args.containsKey(recipe_cake_id)) {
            mFragmentManager = getChildFragmentManager();
            rootView = mBinding.getRoot();

            RecipesDetailFragment recipesDetailFragment = new RecipesDetailFragment();
            recipesDetailFragment.setArguments(args);
            changeFragment(recipesDetailFragment);
        }

        boolean value = ((BaseActivity) getActivity()).mTwoPane;
        Timber.d("Value is: " + value);
    }

    private void reinitData() {
        recipe_cake_id = getString(R.string.recipe_id);
        step_key = getString(R.string.step_id);
        vm = ViewModelProviders.of(this.getActivity()).get(SharedVM.class);
        args = new Bundle();
    }

    protected void changeFragment(Fragment fragment) {
        if (fragment instanceof RecipeVideoDialogFragment) {
            if (mFragmentManager == null) mFragmentManager = getChildFragmentManager();

        /*Set the value for the moment when is on mTwoPane layout so we can change the behaviour of dialog fragment
        to show in the right of the screen not as a dialog*/
            mTwoPane = ((BaseActivity) getActivity()).mTwoPane;
            checkRecipeDialogPresentation(fragment);
        } else {
            mFragmentManager.beginTransaction().
                    setCustomAnimations(R.anim.fade_in, R.anim.fade_out).
                    replace(R.id.fragment_container, fragment).
                    commit();
        }
    }

    /*If is in landscape mode, show the dialog fragment in the right of the screen, else show it as a regular dialog fragment*/
    private void checkRecipeDialogPresentation(Fragment fragment) {
        if (mTwoPane) {
            mFragmentManager = getActivity().getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.fragment_video_container, fragment)
                    .commit();
        } else {
            ((RecipeVideoDialogFragment) fragment).show(mFragmentManager, null);
        }
    }

    /*When configuration changes are happening */
    protected void saveRecyclerViewState(@NonNull Bundle outState, RecyclerView.LayoutManager layoutManager) {
        recyclerViewState = layoutManager.onSaveInstanceState();
        outState.putParcelable(RECYCLER_VIEW_POS, recyclerViewState);
    }

    protected void checkRecyclerViewState(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(RECYCLER_VIEW_POS)) {
            recyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_POS);
        }
    }

    /*When this fragment is not anymore present and this EventBus is triggered and the fragment is not shown,
     * reinitialize the data */
    @Subscribe
    public void onRecipeStepClickEvent(OnRecipeStepClickEvent event) {
        reinitData();
        Step step = event.getStep();
        if (step != null) {
            if (Validator.isTextValid(event.getStep().getVideoURL())) {
                args.putString(recipe_cake_id, event.getStep().getVideoURL());
                RecipeVideoDialogFragment recipeVideoDialogFragment = new RecipeVideoDialogFragment();
                recipeVideoDialogFragment.setArguments(args);
                changeFragment(recipeVideoDialogFragment);
            }
        }
    }
}


