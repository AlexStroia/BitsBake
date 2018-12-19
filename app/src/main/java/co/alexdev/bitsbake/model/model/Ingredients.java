package co.alexdev.bitsbake.model.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import co.alexdev.bitsbake.model.response.Cake;

@Entity
public class Ingredients {

    @PrimaryKey
    @ForeignKey(entity = Cake.class, parentColumns = "id", childColumns = "id")
    private int id;
    private String cake;
    private double quantity;
    private String measure;
    private String ingredient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCake() {
        return cake;
    }

    public void setCake(String cake) {
        this.cake = cake;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
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
