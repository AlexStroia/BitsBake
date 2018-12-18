package co.alexdev.bitsbake.networking;

import android.net.Uri;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static final String SCHEME = "https";
    public static final String AUTHORITY = "d17h27t6h515a5.cloudfront.net";
    public static final String PATH_TOPHER = "topher";
    public static final String PATH_YEAR = "2017";
    public static final String PATH_MONTH = "May";
    public static final String PATH_TYPE = "59121517_baking";
    public static final String PATH_JSON = "baking.json";

    private Retrofit mRetrofit;
    private static RetrofitClient sRetrofitClient;

    private RetrofitClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {

            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        BitsBakeInterceptor bitsBakeInterceptor = new BitsBakeInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(bitsBakeInterceptor).build();

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
                .path(PATH_TOPHER)
                .path(PATH_YEAR)
                .path(PATH_MONTH)
                .build();
    }
}
