package co.alexdev.bitsbake.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

/**Constants class used to define some types
 * @param RESPONSE_LOADING - is set when the networking connectivity begins to load
 * @param RESPONSE_SUCCES - is set when the network connectivity is defined as a succes
 * @param RESPONSE_ERROR - when the connectivity is not made
 *
 * @param BROWNIES - used to set the recipe type to Brownies
 * @param NUTELLA_PIE - used to set the recipe type to Nutella Pie
 * @param YELLOW_CAKE - used to set the recipe type to yellow cake
 * @param CHEESECAKE - used to set the recipe type to cheesecake*/
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
}
