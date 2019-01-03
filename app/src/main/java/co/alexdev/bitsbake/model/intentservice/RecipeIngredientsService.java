package co.alexdev.bitsbake.model.intentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import co.alexdev.bitsbake.model.Ingredient;
import co.alexdev.bitsbake.repo.BitsBakeRepository;
import co.alexdev.bitsbake.utils.SharedPrefManager;
import timber.log.Timber;

public class RecipeIngredientsService extends IntentService {

    public static final String ACTION_GET_INGREDIENT = "co.alexdev.bitsbake.model.service.ACTION_GET_INGREDIENT";

    public RecipeIngredientsService(String name) {
        super(name);
    }

    public RecipeIngredientsService() {
        super("RecipeIngredientsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase(ACTION_GET_INGREDIENT)) {
                handleActionGetIngredient(getApplicationContext());
            }
        }
    }

    /*Handle the query to observe the ingredients list*/
    private void handleActionGetIngredient(Context context) {
        int ingredientRecipeId = SharedPrefManager.getRecipeId(context);
      //  if (ingredientRecipeId == 0) {
            //set the text of the recipe to recipe text not defined
    //    } else {
            LiveData<List<Ingredient>> ingredientList = BitsBakeRepository.getInstance(context).
                    getIngredientById(ingredientRecipeId);
            ingredientList.observeForever(new Observer<List<Ingredient>>() {
                @Override
                public void onChanged(List<Ingredient> ingredients) {
                    ingredientList.removeObserver(this);
                    Timber.d("Ingredients: " + ingredients.toString());
                }
            });
        }
  //  }

    /*Start the service*/
    public static void startActionGetIngredient(Context context) {
        Intent intent = new Intent(context, RecipeIngredientsService.class);
        intent.setAction(ACTION_GET_INGREDIENT);
        context.startService(intent);
    }
}
