package co.alexdev.bitsbake.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

public class Constants {

    /*Networking Constants*/
    public static final int RESPONSE_LOADING = 0;
    public static final int RESPONSE_SUCCES = 1;
    public static final int RESPONSE_ERROR = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RESPONSE_LOADING, RESPONSE_SUCCES, RESPONSE_ERROR})
    public @interface NetworkStatus {
    }


    /*Recipes Constants*/
    public static final String BROWNIES = "Brownies";
    public static final String NUTELLA_PIE = "Nutella Pie";
    public static final String YELLOW_CAKE = "Yellow Cake";
    public static final String CHEESECAKE = "Cheesecake";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({BROWNIES, NUTELLA_PIE, YELLOW_CAKE, CHEESECAKE})
    public @interface RecipeCake {
    }

    /*RecyclerView Constants*/
    public static final int FRAGMENT_INGREDIENT_LAYOUT = 0;
    public static final int FRAGMENT_STEPS_LAYOUT = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FRAGMENT_INGREDIENT_LAYOUT, FRAGMENT_STEPS_LAYOUT})
    public @interface FragmentType {
    }
}
