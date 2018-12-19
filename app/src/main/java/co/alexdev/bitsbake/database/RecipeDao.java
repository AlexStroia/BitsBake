package co.alexdev.bitsbake.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;
import co.alexdev.bitsbake.model.response.Recipe;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM ingredients")
    LiveData<List<Ingredients>> getIngredients();

    @Query("SELECT * FROM Steps")
    LiveData<List<Steps>> getSteps();

    @Query("SELECT * FROM Recipe")
    LiveData<List<Recipe>> getRecipes();

    @Insert(onConflict = REPLACE)
    void insertRecipes(List<Recipe> recipes);

    @Insert(onConflict = REPLACE)
    void insertIngredients(List<Ingredients> ingredients);

    @Insert(onConflict = REPLACE)
    void insertSteps(List<Steps> steps);
}
