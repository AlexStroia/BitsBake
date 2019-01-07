package co.alexdev.bitsbake.utils;

import android.text.TextUtils;

import java.util.List;

import co.alexdev.bitsbake.model.Ingredient;

public class Validator {

    public static boolean validate(List<Ingredient> ingredients, String text) {
        return isArrayValid(ingredients) && isTextValid(text);
    }

    private static Boolean isArrayValid(List<Ingredient> ingredients) {
        return ingredients != null && ingredients.size() > 0;
    }

    public static Boolean isTextValid(String text) {
        return !TextUtils.isEmpty(text);
    }
}
