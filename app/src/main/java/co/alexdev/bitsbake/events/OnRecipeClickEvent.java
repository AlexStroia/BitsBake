package co.alexdev.bitsbake.events;

public class OnRecipeClickEvent {

    private String recipeName;

    public OnRecipeClickEvent(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }
}
