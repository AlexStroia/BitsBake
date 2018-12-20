package co.alexdev.bitsbake.model.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import co.alexdev.bitsbake.model.response.Recipe;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
public class Ingredients extends BaseObservable {

    @PrimaryKey
    @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "id")
    private int id;
    private String cake;
    private double quantity;
    private String measure;
    private String ingredient;

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getCake() {
        return cake;
    }

    public void setCake(String cake) {
        this.cake = cake;
    }

    @Bindable
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
        notifyPropertyChanged(BR.quantity);
    }

    @Bindable
    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
        notifyPropertyChanged(BR.measure);
    }

    @Bindable
    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
        notifyPropertyChanged(BR.ingredient);
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "id=" + id +
                ", cake='" + cake + '\'' +
                ", quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }
}
