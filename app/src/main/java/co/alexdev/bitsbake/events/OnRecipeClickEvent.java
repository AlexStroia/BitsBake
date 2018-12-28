package co.alexdev.bitsbake.events;

public class OnRecipeClickEvent {

    private int recipeId;

    public OnRecipeClickEvent(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }
}
