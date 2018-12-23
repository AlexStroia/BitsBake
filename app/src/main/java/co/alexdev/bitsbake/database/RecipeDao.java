package co.alexdev.bitsbake.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import co.alexdev.bitsbake.model.model.Ingredient;
import co.alexdev.bitsbake.model.model.Step;
import co.alexdev.bitsbake.model.response.Recipe;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM Ingredient")
    LiveData<List<Ingredient>> getIngredients();

    @Query("SELECT * FROM Step")
    LiveData<List<Step>> getSteps();

    @Query("SELECT * FROM Recipe")
    LiveData<List<Recipe>> getRecipes();

    @Query("SELECT * FROM Ingredient where cake = :name")
    LiveData<List<Ingredient>> getIngredientsByName(String name);

    @Query("SELECT * FROM STEP where cake = :name")
    LiveData<List<Step>> getStepByName(String name);

    @Insert(onConflict = REPLACE)
    void insertRecipes(List<Recipe> recipes);

    @Insert(onConflict = REPLACE)
    void insertIngredients(List<Ingredient> ingredients);

    @Insert(onConflict = REPLACE)
    void insertSteps(List<Step> steps);

    @Delete
    void deleteFromFavorite(Recipe recipe);

    @Insert(onConflict = REPLACE)
    void markAsFavorie(Recipe recipe);
}
