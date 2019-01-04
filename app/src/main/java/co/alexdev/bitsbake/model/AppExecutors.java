package co.alexdev.bitsbake.model;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

/** AppExecutors class used to do background work
 * @param LOCK used to lock the state of an object
 * @param diskIO - diskIO executor used for disk operations
 * */
public class AppExecutors {

    private static final Object LOCK = new Object();

    private final Executor diskIO;

    private final Executor networkIO;

    private final Executor mainThread;

    private static AppExecutors sExecutor;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static AppExecutors getInstance() {
        if (sExecutor == null) {
            synchronized (LOCK) {
                sExecutor = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sExecutor;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }

    public Executor getDiskIO() {
        return diskIO;
    }
}
