package id.cleva.mistexample;

import android.app.Application;

import id.cleva.mistexample.model.LoginData;
import id.cleva.mistexample.rest.ApiClient;
import id.cleva.mistexample.rest.ApiRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApp extends Application {

    public static ApiRest restApi;
    public static MyApp mInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        restApi = ApiClient.getRetrofit(this).create(ApiRest.class);
//        initRest();


    }
    public static synchronized MyApp getInstance() {
        return mInstance;
    }


    private void initRest() {
        LoginData data = new LoginData("hermawan@telto.id", "Hermawan123");
        restApi.loginResponse(data).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
