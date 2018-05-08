package com.kirandeep.ivote.retrofitBase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abc on 07-05-2018.
 */

public class ApiClient {


    private Retrofit.Builder builder;
    private static String auth;
    private static ApiClient instance;

    private ApiClient(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();


        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        //builder.addHeader("Authorization", getAuth());
                        //builder.addHeader("Authorization", "Basic Ytpi");
                        builder.addHeader("Accept", "application/json");
                        Request request = builder.build();
                        return chain.proceed(request);
                    }
                })
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        builder = new Retrofit.Builder();
        builder.client(httpClient);
        builder.addConverterFactory(GsonConverterFactory.create(gson));
    }

    public static ApiClient with(){
        if(instance == null){
            instance = new ApiClient();
        }
        return instance;
    }

    public ApiClient baseUrl(String baseUrl){
        this.builder.baseUrl(baseUrl);
        return this;
    }

    public Retrofit build(){
        return this.builder.build();
    }

    /*private static String getAuth(){
        if(auth == null) {
            return Credentials.basic(BuildConfig.serviceUsername, BuildConfig.servicePassword);
        }
        return auth;
    }*/

    public static void setAuth(String authToken) {
        auth = authToken;
    }
}
