package co.alexdev.bitsbake.model.model;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import co.alexdev.bitsbake.model.response.Recipe;

public class RecipeWithIngredientsAndSteps {

    @Embedded
    public Recipe recipe;

    @Relation(parentColumn = "id", entityColumn = "id", entity = Ingredient.class)
    public List<Ingredient> ingredients;

    @Relation(parentColumn = "id",entityColumn = "id", entity = Step.class)
    public List<Step> steps;
}
