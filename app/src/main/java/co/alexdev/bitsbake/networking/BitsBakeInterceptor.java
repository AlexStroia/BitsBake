package co.alexdev.bitsbake.networking;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class BitsBakeInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();
        Response response = chain.proceed(originalRequest);

        Timber.i(response.message());

        return null;
    }
}
