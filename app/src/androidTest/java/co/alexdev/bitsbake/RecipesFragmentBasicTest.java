package co.alexdev.bitsbake;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import co.alexdev.bitsbake.ui.activity.RecipeActivity;
import co.alexdev.bitsbake.ui.fragment.RecipesFragment;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RecipesFragmentBasicTest {

    @Rule public ActivityTestRule<RecipeActivity> mBaseActivityTest = new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void onRecipeFragmentChanged() {
        RecipesFragment recipesFragment = new RecipesFragment();
        mBaseActivityTest.getActivity()
                .getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, recipesFragment).
                commitAllowingStateLoss();
    }
}
