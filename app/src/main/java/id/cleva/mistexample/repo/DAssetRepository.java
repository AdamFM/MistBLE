package id.cleva.mistexample.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import id.cleva.mistexample.model.DataAsset;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.cleva.mistexample.MyApp.restApi;

public class DAssetRepository {

    private static final String TAG = "DAssetRepository";
    private ArrayList<DataAsset> dataMaps = new ArrayList<>();

    public MutableLiveData<List<DataAsset>> getMutableLiveData() {
        Log.e(TAG, "onResponse: START" );
        MutableLiveData<List<DataAsset>> mutableLiveData = new MutableLiveData<>();
        Call<List<DataAsset>> call = restApi.getAsset();
        call.enqueue(new Callback<List<DataAsset>>() {
            @Override
            public void onResponse(Call<List<DataAsset>> call, Response<List<DataAsset>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    dataMaps = (ArrayList<DataAsset>) response.body();
                    Log.e(TAG, "onResponse: " + response.body().size() );
                    mutableLiveData.setValue(dataMaps);
                } else {
                    Log.e(TAG, "onResponse: ELSE" );
                    mutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<DataAsset>> call, Throwable t) {
                Log.e(TAG, "onResponse: FAILURE" );
                Log.e(TAG, "onFailure: " + t.toString() );
                mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }
}
