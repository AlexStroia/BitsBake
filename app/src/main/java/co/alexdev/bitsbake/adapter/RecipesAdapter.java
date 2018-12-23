package co.alexdev.bitsbake.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.bitsbake.R;
import co.alexdev.bitsbake.databinding.ItemRecipeLayoutBinding;
import co.alexdev.bitsbake.model.response.Recipe;
import co.alexdev.bitsbake.utils.Constants;
import co.alexdev.bitsbake.utils.Listeners;
import timber.log.Timber;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;
    private static Listeners.RecipeClickListener mRecipeClickListener;

    public RecipesAdapter(List<Recipe> recipes, Listeners.RecipeClickListener mRecipeClickListener) {
        this.recipes = recipes;
        this.mRecipeClickListener = mRecipeClickListener;
    }

    @NonNull
    @Override
    public RecipesAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemRecipeLayoutBinding binding = ItemRecipeLayoutBinding.inflate(inflater, parent, false);
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.RecipeViewHolder holder, int position) {
        holder.bind(recipes.get(position));
        Recipe recipe = recipes.get(position);
        setImage(recipe, holder.binding);
    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        return recipes.size();
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


    static class RecipeViewHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener {

        private ItemRecipeLayoutBinding binding;

        public RecipeViewHolder(ItemRecipeLayoutBinding itemRecipeLayoutBinding) {
            super(itemRecipeLayoutBinding.getRoot());
            this.binding = itemRecipeLayoutBinding;
            binding.clRecipe.setOnClickListener(this);
        }

        public void bind(Recipe recipe) {
            binding.tvRecipeName.setText(recipe.getName());
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mRecipeClickListener.onRecipeClick(position);
        }
    }
}
