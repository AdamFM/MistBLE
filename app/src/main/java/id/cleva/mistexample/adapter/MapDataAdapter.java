package id.cleva.mistexample.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.cleva.mistexample.R;
import id.cleva.mistexample.databinding.ItemMapBinding;
import id.cleva.mistexample.model.DataMaps;

public class MapDataAdapter extends RecyclerView.Adapter<MapDataAdapter.MapViewHolder> {
    private static final String TAG = "MapDataAdapter";

    private ArrayList<DataMaps> dataMaps;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, DataMaps obj, int position, String sAction);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public MapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMapBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_map, parent, false);
        return new MapViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MapViewHolder holder, int position) {
        DataMaps dataMap = dataMaps.get(position);
        holder.binding.setIMap(dataMap);

        holder.binding.layoutParent.setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, dataMap, position, "Click");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataMaps != null) {
            return dataMaps.size();
        } else {
            return 0;
        }
    }

    public void setDataList(ArrayList<DataMaps> dataList) {
        this.dataMaps = dataList;
        Log.i(TAG, "setDataList: " + dataList.size());
        notifyDataSetChanged();
    }

    public class MapViewHolder extends RecyclerView.ViewHolder {

        private ItemMapBinding binding;

        public MapViewHolder(@NonNull ItemMapBinding itemBinding) {
            super(itemBinding.getRoot());

            this.binding = itemBinding;
        }
    }
}
