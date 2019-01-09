package co.alexdev.bitsbake.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.ItemRecipeLayoutBinding;
import co.alexdev.bitsbake.model.Recipe;
import co.alexdev.bitsbake.utils.Constants;
import timber.log.Timber;

public class MasterRecipesAdapter extends BaseAdapter {

    private static List<Recipe> recipes;

    public MasterRecipesAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }


    @Override
    public int getCount() {
        if (recipes == null) return 0;
        return recipes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    private void setImage(Recipe recipe, ItemRecipeLayoutBinding binding) {
        @Constants.RecipeCake String recipeName = recipe.getName();
        String imageUri = "";
        String recipeUri = recipe.getImage();

        switch (recipeName) {
            case Constants.BROWNIES:
                imageUri = (!recipeUri.equals("")) ? recipeUri : String.valueOf(R.drawable.brownies);
                Timber.d("Brownies");
                break;

            case Constants.CHEESECAKE:
                imageUri = (!recipeUri.equals("")) ? recipeUri : String.valueOf(R.drawable.cheesecake);
                Timber.d("Cheesecake");
                break;

            case Constants.YELLOW_CAKE:
                imageUri = (!recipeUri.equals("")) ? recipeUri : String.valueOf(R.drawable.yellow_cake);
                Timber.d("Yellow Cake");
                break;

            case Constants.NUTELLA_PIE:
                imageUri = (!recipeUri.equals("")) ? recipeUri : String.valueOf(R.drawable.nutella_pie);
                Timber.d("Nutella Pie");
                break;
        }
        Picasso.get().load(Integer.valueOf(imageUri))
                .into(binding.ivRecipe);
    }
}
