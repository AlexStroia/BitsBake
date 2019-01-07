package co.alexdev.bitsbake.events;

/**
 * Event which triggers when a recipe is clicked
 *
 * @param recipeID  id of the recipe
 */

public class OnRecipeClickEvent {

    private int recipeId;

    public OnRecipeClickEvent(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }
}
