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
import id.cleva.mistexample.databinding.ItemAssetBinding;
import id.cleva.mistexample.model.DataAsset;

public class AssetDataAdapter extends RecyclerView.Adapter<AssetDataAdapter.AssetViewHolder> {
    private static final String TAG = "AssetDataAdapter";

    private ArrayList<DataAsset> dataAssets;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, DataAsset obj, int position, String sAction);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public AssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAssetBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_asset, parent, false);
        return new AssetViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AssetViewHolder holder, int position) {
        DataAsset dataAsset = dataAssets.get(position);
        holder.binding.setIAsset(dataAsset);

        holder.binding.layoutParent.setOnClickListener(view -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, dataAsset, position, "Click");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataAssets != null) {
            return dataAssets.size();
        } else {
            return 0;
        }
    }

    public void setDataList(ArrayList<DataAsset> dataList) {
        this.dataAssets = dataList;
        Log.i(TAG, "setDataList: " + dataList.size());
        notifyDataSetChanged();
    }

    public class AssetViewHolder extends RecyclerView.ViewHolder {

        private ItemAssetBinding binding;

        public AssetViewHolder(@NonNull ItemAssetBinding itemBinding) {
            super(itemBinding.getRoot());

            this.binding = itemBinding;
        }
    }
}
