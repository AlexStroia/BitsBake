package co.alexdev.bitsbake.events;

import co.alexdev.bitsbake.ui.fragment.RecipeVideoDialogFragment;

public class IsTwoPaneEvent {

    private boolean isTwoPane;
    private RecipeVideoDialogFragment recipeVideoDialogFragment;

    public IsTwoPaneEvent(boolean isTwoPane, RecipeVideoDialogFragment recipeVideoDialogFragment) {
        this.isTwoPane = isTwoPane;
        this.recipeVideoDialogFragment = recipeVideoDialogFragment;
    }

    public boolean isTwoPane() {
        return isTwoPane;
    }

    public RecipeVideoDialogFragment getRecipeVideoDialogFragment() {
        return recipeVideoDialogFragment;
    }
}
