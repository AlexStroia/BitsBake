package co.alexdev.bitsbake.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;

/*Response Class */
public class Cake {

    private int id;
    private String name;

    @SerializedName("ingredients")
    private List<Ingredients> ingredients;

    @SerializedName("steps")
    private List<Steps> steps;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "Cake{" +
                "ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}
