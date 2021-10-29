package id.cleva.mistexample.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import id.cleva.mistexample.model.DataMaps;
import id.cleva.mistexample.model.LoginData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.cleva.mistexample.MyApp.restApi;

public class DMapRepository {

    private static final String TAG = "DMapRepository";
    private ArrayList<DataMaps> dataMaps = new ArrayList<>();

    private static DMapRepository dMapRepository;

    public static DMapRepository getInstance() {
        if (dMapRepository == null) {
            dMapRepository = new DMapRepository();
        }
        return dMapRepository;
    }

    public DMapRepository() {
        initRest();
    }

    public MutableLiveData<List<DataMaps>> getMutableLiveData() {
        Log.e(TAG, "onResponse: START");
        MutableLiveData<List<DataMaps>> mutableLiveData = new MutableLiveData<>();
        LoginData data = new LoginData("hermawan@telto.id", "Hermawan123");
        restApi.loginResponse(data).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> callz, Response<String> response) {

                Call<List<DataMaps>> call = restApi.getMaps();
                call.enqueue(new Callback<List<DataMaps>>() {
                    @Override
                    public void onResponse(Call<List<DataMaps>> call, Response<List<DataMaps>> response) {
                        if (response.body() != null && response.body().size() > 0) {
                            dataMaps = (ArrayList<DataMaps>) response.body();
                            Log.e(TAG, "onResponse: " + response.body().size());
                            mutableLiveData.setValue(dataMaps);
                        } else {
                            Log.e(TAG, "onResponse: ELSE");
                            mutableLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DataMaps>> call, Throwable t) {
                        Log.e(TAG, "onResponse: FAILURE");
                        Log.e(TAG, "onFailure: " + t.toString());
                        mutableLiveData.setValue(null);
                    }
                });
            }

            @Override
            public void onFailure(Call<String> callz, Throwable t) {

            }
        });

        return mutableLiveData;
    }


    private void initRest() {

    }
}
