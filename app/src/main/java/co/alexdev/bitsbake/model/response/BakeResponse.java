package co.alexdev.bitsbake.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;

public class BakeResponse {

    @SerializedName("ingredients")
    private List<Ingredients> ingredients;

    @SerializedName("steps")
    private List<Steps> steps;

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "BakeResponse{" +
                "ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}
