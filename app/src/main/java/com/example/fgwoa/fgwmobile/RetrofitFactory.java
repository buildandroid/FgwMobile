package com.example.fgwoa.fgwmobile;



import android.support.v7.appcompat.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private RetrofitFactory() {
    }

    public static Retrofit getRetorfit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // add your other interceptors …

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30000, TimeUnit.MILLISECONDS);
        builder.readTimeout(30000,TimeUnit.MILLISECONDS);

        if (BuildConfig.DEBUG) {
            // add logging as last interceptor
            builder.addInterceptor(logging);
        }

        OkHttpClient httpClient =  builder.build();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://210.74.194.125:8082")
                .client(httpClient)
                .build();
    }

}
