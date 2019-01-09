package co.alexdev.bitsbake;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import co.alexdev.bitsbake.ui.activity.BaseActivity;
import co.alexdev.bitsbake.ui.fragment.RecipesDetailFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RecipeDetailFragmentTests {

    @Rule
    public ActivityTestRule<BaseActivity> mBaseActivityTest = new ActivityTestRule<>(BaseActivity.class);

    @Test
    public void onUpdateWidgetButtonClick() {
        RecipesDetailFragment recipesFragment = new RecipesDetailFragment();
        mBaseActivityTest.getActivity()
                .getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, recipesFragment).
                commitAllowingStateLoss();

        onView(withId(R.id.btn_update_widget)).perform(click());
    }

    @Test
    public void ingredientsTextViewText() {
        RecipesDetailFragment recipesFragment = new RecipesDetailFragment();
        mBaseActivityTest.getActivity()
                .getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, recipesFragment).
                commitAllowingStateLoss();
        onView(withId(R.id.tv_ingredientsName)).check(matches(withText(R.string.ingredient)));
    }

    @Test
    public void scrollToPositionStepsRecycler() {
        RecipesDetailFragment recipesFragment = new RecipesDetailFragment();
        mBaseActivityTest.getActivity()
                .getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, recipesFragment).
                commitAllowingStateLoss();

        onView(withId(R.id.rv_details)).perform(closeSoftKeyboard());
    }
}
