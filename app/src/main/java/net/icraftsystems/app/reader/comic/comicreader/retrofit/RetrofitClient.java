package net.icraftsystems.app.reader.comic.comicreader.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit instance;
    public static Retrofit getInstance(){

        if (instance == null)

            //localhost on the emulater changes to 10.0.2.2
            instance = new Retrofit.Builder().baseUrl("https://192.168.1.164:3000/")
                           .addConverterFactory(GsonConverterFactory.create())
                           .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        return instance;
    }


}
