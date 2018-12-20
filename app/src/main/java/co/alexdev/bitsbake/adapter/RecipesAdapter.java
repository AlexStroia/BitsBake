package co.alexdev.bitsbake.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.alexdev.bitsbake.databinding.ItemRecipeLayoutBinding;
import co.alexdev.bitsbake.model.response.Recipe;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    public RecipesAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
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
    }

    @Override
    public int getItemCount() {
        if(recipes == null) return 0;
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private ItemRecipeLayoutBinding binding;

        public RecipeViewHolder(ItemRecipeLayoutBinding itemRecipeLayoutBinding) {
            super(itemRecipeLayoutBinding.getRoot());
            this.binding = itemRecipeLayoutBinding;
        }

        public void bind(Recipe recipe) {
            binding.tvRecipeName.setText(recipe.getName());
            binding.executePendingBindings();
        }
    }
}
