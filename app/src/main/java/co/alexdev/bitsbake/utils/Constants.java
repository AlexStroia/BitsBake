package co.alexdev.bitsbake.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public class Constants {

    public static final int NETWORK_SUCCES = 0;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NETWORK_SUCCES, NETWORK_ERROR, NETWORK_LOADING})
    public @interface Status {}
}
