package com.danielstone.materialaboutlibrary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danielstone.materialaboutlibrary.holders.MaterialAboutItemViewHolder;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItem;
import com.danielstone.materialaboutlibrary.util.DefaultViewTypeManager;
import com.danielstone.materialaboutlibrary.util.ViewTypeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MaterialAboutItemAdapter extends RecyclerView.Adapter<MaterialAboutItemViewHolder> {

    private ViewTypeManager viewTypeManager;

    private Context context;

    private List<MaterialAboutItem> data = new ArrayList<>();

    MaterialAboutItemAdapter() {
        setHasStableIds(true);
        this.viewTypeManager = new DefaultViewTypeManager();
    }

    MaterialAboutItemAdapter(ViewTypeManager customViewTypeManager) {
        setHasStableIds(true);
        this.viewTypeManager = customViewTypeManager;
    }

    @NonNull
    @Override
    public MaterialAboutItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        if (!(viewGroup instanceof RecyclerView)) {
            throw new RuntimeException("Not bound to RecyclerView");
        }

        int layoutId = viewTypeManager.getLayout(viewType);

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
        view.setFocusable(true);

        return viewTypeManager.getViewHolder(viewType, view);
    }

    @Override
    public void onBindViewHolder(MaterialAboutItemViewHolder holder, int position) {
        viewTypeManager.setupItem(getItemViewType(position), holder, data.get(position), context);
    }

    @Override
    public long getItemId(int position) {
        return UUID.fromString(data.get(position).getId()).getMostSignificantBits() & Long.MAX_VALUE;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    public void setData(ArrayList<MaterialAboutItem> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    public List<MaterialAboutItem> getData() {
        return data;
    }
}
