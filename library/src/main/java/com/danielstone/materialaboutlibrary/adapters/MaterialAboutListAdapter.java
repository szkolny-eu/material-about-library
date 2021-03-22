package com.danielstone.materialaboutlibrary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.danielstone.materialaboutlibrary.R;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.util.DefaultViewTypeManager;
import com.danielstone.materialaboutlibrary.util.ViewTypeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MaterialAboutListAdapter extends RecyclerView.Adapter<MaterialAboutListAdapter.MaterialAboutListViewHolder> {

    private Context context;

    private ViewTypeManager viewTypeManager;

    private List<MaterialAboutCard> data = new ArrayList<>();

    public MaterialAboutListAdapter() {
        setHasStableIds(true);
        this.viewTypeManager = new DefaultViewTypeManager();
    }

    public MaterialAboutListAdapter(ViewTypeManager customViewTypeManager) {
        setHasStableIds(true);
        this.viewTypeManager = customViewTypeManager;
    }

    @Override
    public MaterialAboutListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        if (viewGroup instanceof RecyclerView) {
            Context context = viewGroup.getContext();
            // viewType may be set to a style resource
            if (viewType != 0) {
                context = new ContextThemeWrapper(context, viewType);
            }
            View view = LayoutInflater.from(context).inflate(R.layout.mal_material_about_list_card, viewGroup, false);
            view.setFocusable(true);
            return new MaterialAboutListViewHolder(view);
        } else {
            throw new RuntimeException("Not bound to RecyclerView");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getTheme();
    }

    @Override
    public void onBindViewHolder(MaterialAboutListViewHolder holder, int position) {
        MaterialAboutCard card = data.get(position);

        if (holder.cardView instanceof CardView) {
            CardView cardView = (CardView) holder.cardView;

            int cardColor = card.getCardColor();
            if (cardColor != 0) {
                cardView.setCardBackgroundColor(cardColor);
            } else {
                cardView.setCardBackgroundColor(cardView.getCardBackgroundColor().getDefaultColor());
            }
        }

        CharSequence title = card.getTitle();
        int titleRes = card.getTitleRes();

        holder.title.setVisibility(View.VISIBLE);
        if (title != null) {
            holder.title.setText(title);
        } else if (titleRes != 0) {
            holder.title.setText(titleRes);
        } else {
            holder.title.setVisibility(View.GONE);
        }

        int titleColor = card.getTitleColor();

        if (holder.title.getVisibility() == View.VISIBLE) {
            if (titleColor != 0) {
                holder.title.setTextColor(titleColor);
            } else {
                holder.title.setTextColor(holder.title.getTextColors().getDefaultColor());
            }
        }

        if (card.getCustomAdapter() != null) {
            holder.useCustomAdapter(card.getCustomAdapter());
        } else {
            holder.useMaterialAboutItemAdapter();
            ((MaterialAboutItemAdapter) holder.adapter).setData(card.getItems());
        }
    }

    @Override
    public long getItemId(int position) {
        return UUID.fromString(data.get(position).getId()).getMostSignificantBits() & Long.MAX_VALUE;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<MaterialAboutCard> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    List<MaterialAboutCard> getData() {
        return data;
    }

    class MaterialAboutListViewHolder extends RecyclerView.ViewHolder {

        final View cardView;
        final TextView title;
        final RecyclerView recyclerView;
        RecyclerView.Adapter adapter;

        MaterialAboutListViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.mal_list_card);
            title = (TextView) view.findViewById(R.id.mal_list_card_title);
            recyclerView = (RecyclerView) view.findViewById(R.id.mal_card_recyclerview);
            adapter = new MaterialAboutItemAdapter(viewTypeManager);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
            recyclerView.setNestedScrollingEnabled(false);
        }

        public void useMaterialAboutItemAdapter() {
            if (!(adapter instanceof MaterialAboutItemAdapter)) {
                adapter = new MaterialAboutItemAdapter(viewTypeManager);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
        }

        public void useCustomAdapter(RecyclerView.Adapter newAdapter) {
            if (adapter instanceof MaterialAboutItemAdapter) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(newAdapter);
            }
        }
    }
}
