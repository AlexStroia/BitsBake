package co.alexdev.bitsbake;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import co.alexdev.bitsbake.ui.activity.BaseActivity;
import co.alexdev.bitsbake.ui.fragment.RecipesFragment;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RecipesFragmentBasicTest {

    @Rule public ActivityTestRule<BaseActivity> mBaseActivityTest = new ActivityTestRule<>(BaseActivity.class);

    @Test
    public void onRecipeFragmentChanged() {
        RecipesFragment recipesFragment = new RecipesFragment();
        mBaseActivityTest.getActivity()
                .getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, recipesFragment).
                commitAllowingStateLoss();
    }

    @Test
    public void onRecyclerViewClick() {

    }
}
