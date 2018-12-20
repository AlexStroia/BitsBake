package co.alexdev.bitsbake.app;

import android.app.Application;
import android.content.IntentFilter;

import co.alexdev.bitsbake.BuildConfig;
import co.alexdev.bitsbake.receiver.NetworkReceiver;
import timber.log.Timber;

public class BitsBakeApp extends Application {

    public static final String INTENT_FILTER_STRING = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.d("Application launched");
    }
}
