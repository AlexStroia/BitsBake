package co.alexdev.bitsbake.utils;

public class Listeners {

    public interface RecipeClickListener {
        void onRecipeClick(int position);
    }

    public interface RecipeStepListener {
        void onRecipeStepClick(int position);
    }
}
