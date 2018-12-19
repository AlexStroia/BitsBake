package co.alexdev.bitsbake.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public class Constants {

    public static final int LOADING = 0;
    public static final int SUCCESS = 1;
    public static final int ERROR = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LOADING,SUCCESS,ERROR})
    public @interface NetworkStatus{}
}
