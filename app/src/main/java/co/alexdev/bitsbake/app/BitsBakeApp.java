package co.alexdev.bitsbake.app;

import android.app.Application;

import co.alexdev.bitsbake.BuildConfig;
import timber.log.Timber;

public class BitsBakeApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.d("Application launched");
    }
}
