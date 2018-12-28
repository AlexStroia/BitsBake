package co.alexdev.bitsbake.networking;

import android.net.Uri;

import java.util.concurrent.TimeUnit;

import co.alexdev.bitsbake.networking.service.BitsBakeService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RetrofitClient {

    public static final String SCHEME = "https";
    public static final String AUTHORITY = "d17h27t6h515a5.cloudfront.net";
    public static final String PATH = "/topher/2017/May/59121517_baking/";
    private final int TIMEOUT_MILISECONDS = 5000;
    private Retrofit mRetrofit;
    private static RetrofitClient sRetrofitClient;

    private RetrofitClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(logginInterceptor -> Timber.d(logginInterceptor));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        BitsBakeInterceptor bitsBakeInterceptor = new BitsBakeInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .callTimeout(TIMEOUT_MILISECONDS, TimeUnit.MILLISECONDS)
                .addInterceptor(bitsBakeInterceptor).build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(getEndpoint().toString())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance() {

        if (sRetrofitClient == null) {
            sRetrofitClient = new RetrofitClient();
        }
        return sRetrofitClient;
    }

    private Uri getEndpoint() {
        return new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .path(PATH)
                .build();
    }

    public BitsBakeService getBakeService() {
        return mRetrofit.create(BitsBakeService.class);
    }
}
