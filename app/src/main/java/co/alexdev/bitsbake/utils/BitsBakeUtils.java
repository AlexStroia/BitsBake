package co.alexdev.bitsbake.utils;

import java.util.ArrayList;
import java.util.List;

import co.alexdev.bitsbake.model.model.Ingredients;
import co.alexdev.bitsbake.model.model.Steps;
import co.alexdev.bitsbake.model.response.Cake;
import timber.log.Timber;

public class BitsBakeUtils {

    public static List<Cake> formatCakeForDb(List<Cake> cakes) {
        List<Cake> cakeList = new ArrayList<>();
        for (Cake cake : cakes) {
            Timber.d(cake.getName());
            /*Set the ingredients id same with the cake id*/
            for (Ingredients ingredients : cake.getIngredients()) {
                ingredients.setId(cake.getId());
                Timber.d(ingredients.getCake() + " | " + ingredients.getMeasure() + " | " + ingredients.getIngredient());
            }
            /*set steps id with cake id */
            for (Steps steps : cake.getSteps()) {
                steps.setId(cake.getId());
                Timber.d(steps.getDescription());
            }
            cakeList.add(cake);
        }
        return cakeList;
    }
}
