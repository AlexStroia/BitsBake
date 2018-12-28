package co.alexdev.bitsbake.model.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import co.alexdev.bitsbake.model.model.Ingredient;
import co.alexdev.bitsbake.model.model.Step;

/*Response Class */
@Entity
public class Recipe extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @Ignore
    @SerializedName("ingredients")
    private List<Ingredient> ingredients;
    @Ignore
    @SerializedName("steps")
    private List<Step> steps;
    private boolean isFavorite;
    private int servings;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    @Bindable
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Bindable
    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
        notifyPropertyChanged(BR.servings);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredient=" + ingredients +
                ", steps=" + steps +
                ", isFavorite=" + isFavorite +
                ", servings=" + servings +
                ", image='" + image + '\'' +
                '}';
    }
}
