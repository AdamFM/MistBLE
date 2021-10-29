package id.cleva.mistexample.fragment;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import id.cleva.mistexample.R;
import id.cleva.mistexample.activity.MainActivity;
import id.cleva.mistexample.adapter.AssetDataAdapter;
import id.cleva.mistexample.databinding.FragmentDetailMapBinding;
import id.cleva.mistexample.model.DataAsset;
import id.cleva.mistexample.model.DataMaps;
import id.cleva.mistexample.utils.Method;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.cleva.mistexample.MyApp.restApi;

public class DetailMapFragment extends Fragment {

    private static final String TAG = "DetailMapFragment";
    private static final String ARG_PARAM1 = "DATA";

    DataMaps dataMap;

    FragmentDetailMapBinding binding;

    MainActivity activity;

    AssetDataAdapter dataAdapter;

    private double scaleXFactor;
    private double scaleYFactor;
    private boolean scaleFactorCalled;
    private float floorImageLeftMargin;
    private float floorImageTopMargin;

    final Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            getAsset();
        }
    };

    public DetailMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataMap = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_detail_map, container, false);
        View v = binding.getRoot();
        binding.setFragment(this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null)
            activity = (MainActivity) getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Live " + dataMap.getName());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(binding.recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        dataAdapter = new AssetDataAdapter();
        dataAdapter.setOnItemClickListener((view, obj, position, sAction) -> {
            Log.i(TAG, "onActivityCreated: ");
        });

        binding.recyclerView.setAdapter(dataAdapter);

        Glide.with(binding.floorplanImage.getContext())
                .load(dataMap.getUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        binding.loadBar.setVisibility(View.GONE);
                        setupScaleFactorForFloorplan();
                        handler.postDelayed(runnable, 200);
                        return false;
                    }
                })
                .into(binding.floorplanImage);

    }

    private void getAsset() {
        Call<List<DataAsset>> call = restApi.getAsset();
        call.enqueue(new Callback<List<DataAsset>>() {
            @Override
            public void onResponse(Call<List<DataAsset>> call, Response<List<DataAsset>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    List<DataAsset> dataAssets = response.body();
                    ArrayList<DataAsset> dNew = new ArrayList<>();

                    binding.parent.removeAllViews();
                    binding.parent.requestLayout();
                    for (int i = 0; i < dataAssets.size(); i++) {

                        if (dataAssets.get(i).getMapId() != null) {
                            if (dataAssets.get(i).getMapId().equals(dataMap.getId())) {
                                dNew.add(dataAssets.get(i));
                                addAssetToMap(dataAssets.get(i));
                            }
                        }
                    }
                    dataAdapter.setDataList(dNew);
                } else {
                    Log.e(TAG, "onResponse: ELSE");
                }
            }

            @Override
            public void onFailure(Call<List<DataAsset>> call, Throwable t) {
                Log.e(TAG, "onResponse: FAILURE");
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });

        handler.postDelayed(runnable, 5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private void addAssetToMap(DataAsset dataAsset) {

        LinearLayout layout2 = new LinearLayout(activity);

        layout2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout2.setOrientation(LinearLayout.VERTICAL);
        layout2.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, getResources().getDisplayMetrics()));
        ImageView imageview = new ImageView(activity);
        imageview.setImageResource(R.drawable.bluetooth);

        // If scaleX and scaleY are not defined, check again
        if (!scaleFactorCalled && (scaleXFactor == 0 || scaleYFactor == 0)) {
            setupScaleFactorForFloorplan();
        }
        float leftMargin = (float) (floorImageLeftMargin + ((dataAsset.getX() * scaleXFactor) - (imageview.getWidth() / 2)));
        float topMargin = (float) (floorImageTopMargin + ((dataAsset.getY() * scaleXFactor) - (imageview.getHeight() / 2)));

//        imageview.setX(leftMargin);
//        imageview.setY(topMargin);
        layout2.setX(leftMargin);
        layout2.setY(topMargin);

        imageview.setLayoutParams(params);

        layout2.addView(imageview);

        TextView tv = new TextView(activity);
        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText(dataAsset.getName());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.setTextAppearance(R.style.AudioFileInfoOverlayText);
        } else {
            tv.setTextAppearance(activity, R.style.AudioFileInfoOverlayText);
        }

        layout2.addView(tv);

        binding.parent.addView(layout2);
    }

    //calculating the scale factors
    private void setupScaleFactorForFloorplan() {
        ViewTreeObserver vto = binding.floorplanImage.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                floorImageLeftMargin = binding.floorplanImage.getLeft();
                floorImageTopMargin = binding.floorplanImage.getTop();
                if (binding.floorplanImage.getDrawable() != null) {
                    scaleXFactor = (binding.floorplanImage.getWidth() / (double) binding.floorplanImage.getDrawable().getIntrinsicWidth());
                    scaleYFactor = (binding.floorplanImage.getHeight() / (double) binding.floorplanImage.getDrawable().getIntrinsicHeight());
                    scaleFactorCalled = true;
                }
            }
        });

    }
}