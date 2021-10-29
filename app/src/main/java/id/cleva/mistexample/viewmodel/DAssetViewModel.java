package id.cleva.mistexample.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.cleva.mistexample.model.DataAsset;
import id.cleva.mistexample.repo.DAssetRepository;

public class DAssetViewModel extends ViewModel {

    private MutableLiveData<List<DataAsset>> mutableLiveData = new MutableLiveData<>();
    DAssetRepository dAssetRepository;

    public DAssetViewModel() {
        dAssetRepository = new DAssetRepository();
    }

    public void getData() {
        mutableLiveData = dAssetRepository.getMutableLiveData();
    }

    public LiveData<List<DataAsset>> getAllAsset() {
        return mutableLiveData;
    }
}
