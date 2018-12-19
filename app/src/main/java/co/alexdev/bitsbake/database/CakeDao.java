package co.alexdev.bitsbake.database;

import java.util.List;

import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;
import co.alexdev.bitsbake.model.response.Cake;

public interface CakeDao {

    List<Ingredients> getIngredients();

    List<Steps> getSteps();

    List<Cake> getCake();
}
