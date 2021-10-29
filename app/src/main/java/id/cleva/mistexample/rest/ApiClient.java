package id.cleva.mistexample.rest;


import android.content.Context;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiClient {

    public final static String BASE_URL = "https://api.mist.com/api/v1/";

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(Context c) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient clientz;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(new AddCookiesInterceptor(c)); // VERY VERY IMPORTANT
        builder.addInterceptor(new ReceivedCookiesInterceptor(c)); // VERY VERY IMPORTANT

        builder.addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);
        clientz = builder.build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientz)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .build();
    }

}
