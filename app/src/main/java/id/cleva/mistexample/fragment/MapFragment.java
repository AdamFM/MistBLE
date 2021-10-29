package id.cleva.mistexample.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;

import id.cleva.mistexample.MyApp;
import id.cleva.mistexample.R;
import id.cleva.mistexample.adapter.MapDataAdapter;
import id.cleva.mistexample.databinding.FragmentMapBinding;
import id.cleva.mistexample.model.DataMaps;
import id.cleva.mistexample.model.LoginData;
import id.cleva.mistexample.viewmodel.DMapViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.cleva.mistexample.MyApp.restApi;
import static id.cleva.mistexample.utils.Method.SDKTOKEN;

public class MapFragment extends Fragment {
    private static final String TAG = "MapFragment";

    FragmentMapBinding binding;

    MyApp mainApplication;

    MapDataAdapter dataAdapter;

    DMapViewModel dMapViewModel;

    private static final String SDK_TOKEN = "sdkToken";

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_map, container, false);
        View v = binding.getRoot();
        binding.setFragment(this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null)
            mainApplication = (MyApp) getActivity().getApplication();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.recyclerView.setLayoutManager(new GridLayoutManager(mainApplication, 2));
        binding.recyclerView.setHasFixedSize(true);

        dMapViewModel = new ViewModelProvider(requireActivity()).get(DMapViewModel.class);

        dataAdapter = new MapDataAdapter();
        dataAdapter.setOnItemClickListener((view, obj, position, sAction) -> {

            Bundle bundle = new Bundle();
            bundle.putParcelable("DATA", obj);
            bundle.putString("title", obj.getName());
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_mapFragment_to_detailMapFragment, bundle);
        });
        binding.recyclerView.setAdapter(dataAdapter);
        binding.containedButton.setOnClickListener(view -> {

            Bundle bundle = new Bundle();
            bundle.putString(SDK_TOKEN, SDKTOKEN);
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_mapFragment_to_mapNotifFragment, bundle);
        });
        initRest();
        getMap();
    }

    private void initRest() {
        LoginData data = new LoginData("hermawan@telto.id", "Hermawan123");
        restApi.loginResponse(data).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                dMapViewModel.getMaps();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void getMap() {
        dMapViewModel.getAllMap().observe(requireActivity(), dataMaps -> {
            if (dataMaps != null) {
                Log.i(TAG, "getMap: " + dataMaps.size());
                dataAdapter.setDataList((ArrayList<DataMaps>) dataMaps);
            }
        });
    }
}