package co.alexdev.bitsbake.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import co.alexdev.bitsbake.model.Ingredient;
import co.alexdev.bitsbake.model.Step;
import co.alexdev.bitsbake.model.Recipe;

import static androidx.room.ForeignKey.CASCADE;

@Dao
public interface RecipeDao {

    /*Ingredients*/

    @Query("SELECT * FROM Ingredient")
    LiveData<List<Ingredient>> getIngredients();

    @Query("SELECT * FROM Ingredient where id = :id")
    LiveData<List<Ingredient>> getIngredientById(int id);

    @Query("DELETE FROM INGREDIENT")
    void deleteIngredients();

    @Insert(onConflict = CASCADE)
    void insertIngredients(List<Ingredient> ingredients);


    /*Recipe */

    @Query("SELECT * FROM Recipe")
    LiveData<List<Recipe>> getRecipes();

    @Query("SELECT * FROM Recipe WHERE id = :id")
    LiveData<Recipe> getRecipeById(int id);

    @Insert(onConflict = CASCADE)
    void insertRecipes(List<Recipe> recipes);

    @Query("DELETE FROM RECIPE")
    void deleteRecipes();


    /*STEP*/

    @Query("DELETE FROM STEP")
    void deleteSteps();

    @Insert(onConflict = CASCADE)
    void insertSteps(List<Step> steps);

    @Query("SELECT * FROM Step where id = :id")
    LiveData<List<Step>> getStepById(int id);


    @Query("SELECT * FROM Step")
    LiveData<List<Step>> getSteps();

}
