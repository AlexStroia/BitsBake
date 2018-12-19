package co.alexdev.bitsbake.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public class Constants {

    public static final int RESPONSE_LOADING = 0;
    public static final int RESPONSE_SUCCES = 1;
    public static final int RESPONSE_ERROR = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({RESPONSE_LOADING, RESPONSE_SUCCES, RESPONSE_ERROR})
    public @interface NetworkStatus {
    }
}
