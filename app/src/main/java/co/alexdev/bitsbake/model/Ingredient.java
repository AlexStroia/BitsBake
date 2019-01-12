package co.alexdev.bitsbake.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Ingredient model class */
@Entity
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private int room_id;
    private double quantity;
    private String measure;
    private String cake;
    private String ingredient;
    private int id;

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getId() {
        return id;
    }

    public String getCake() {
        return cake;
    }

    public void setCake(String cake) {
        this.cake = cake;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Ingredient{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", cake='" + cake + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }
}
