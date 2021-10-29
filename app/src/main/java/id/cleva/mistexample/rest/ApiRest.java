package id.cleva.mistexample.rest;

import java.util.List;

import id.cleva.mistexample.model.DataAsset;
import id.cleva.mistexample.model.DataMaps;
import id.cleva.mistexample.model.LoginData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRest {

    @POST("login")
    Call<String> loginResponse(@Body LoginData body);

    @GET("sites/1ef9e26c-dea8-4431-9a8f-7e17b26f7834/stats/assets")
    Call<List<DataAsset>> getAsset();

    @GET("sites/1ef9e26c-dea8-4431-9a8f-7e17b26f7834/maps")
    Call<List<DataMaps>> getMaps();
}
