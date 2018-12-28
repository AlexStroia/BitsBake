package co.alexdev.bitsbake.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import co.alexdev.bitsbake.model.model.Ingredient;
import co.alexdev.bitsbake.model.model.RecipeWithIngredientsAndSteps;
import co.alexdev.bitsbake.model.model.Step;
import co.alexdev.bitsbake.model.model.Recipe;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {

    /*Ingredients*/

    @Query("SELECT * FROM Ingredient")
    LiveData<List<Ingredient>> getIngredients();

    @Query("DELETE FROM INGREDIENT")
    void deleteIngredients();

    @Insert(onConflict = CASCADE)
    void insertIngredients(List<Ingredient> ingredients);


    /*Recipe */

    @Query("SELECT * FROM Recipe")
    LiveData<List<Recipe>> getRecipes();

    @Transaction
    @Query("SELECT * FROM Recipe WHERE id = :id")
    LiveData<RecipeWithIngredientsAndSteps> getRecipe(int id);

    @Transaction
    @Query("SELECT * FROM Recipe")
    LiveData<List<RecipeWithIngredientsAndSteps>> getRecipeList();

    @Insert(onConflict = CASCADE)
    void insertRecipes(List<Recipe> recipes);

    @Query("DELETE FROM RECIPE")
    void deleteRecipes();


    /*STEP*/

    @Query("DELETE FROM STEP")
    void deleteSteps();

    @Insert(onConflict = CASCADE)
    void insertSteps(List<Step> steps);


    @Query("SELECT * FROM Step")
    LiveData<List<Step>> getSteps();
}
