package id.cleva.mistexample.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.cleva.mistexample.model.DataMaps;
import id.cleva.mistexample.repo.DMapRepository;

public class DMapViewModel extends ViewModel {

    private MutableLiveData<List<DataMaps>> mutableLiveData;
    DMapRepository dMapRepository;

    public DMapViewModel() {
        dMapRepository = new DMapRepository();
        mutableLiveData = dMapRepository.getMutableLiveData();
    }

    public LiveData<List<DataMaps>> getAllMap() {
        return mutableLiveData;
    }
}
