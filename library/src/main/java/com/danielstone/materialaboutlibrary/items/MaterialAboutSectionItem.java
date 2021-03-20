package com.danielstone.materialaboutlibrary.items;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.danielstone.materialaboutlibrary.R;
import com.danielstone.materialaboutlibrary.holders.MaterialAboutItemViewHolder;
import com.danielstone.materialaboutlibrary.util.ViewTypeManager;

import static android.view.View.GONE;

public class MaterialAboutSectionItem extends MaterialAboutItem {

    private CharSequence text = null;
    private int textRes = 0;

    private MaterialAboutSectionItem(Builder builder) {
        super();
        this.text = builder.text;
        this.textRes = builder.textRes;
    }

    public MaterialAboutSectionItem(CharSequence text) {
        this.text = text;
    }

    public MaterialAboutSectionItem(int textRes) {
        this.textRes = textRes;
    }

    public static MaterialAboutItemViewHolder getViewHolder(View view) {
        return new MaterialAboutSectionItemViewHolder(view);
    }

    public static void setupItem(MaterialAboutSectionItemViewHolder holder, MaterialAboutSectionItem item, Context context) {

        CharSequence text = item.getText();
        int textRes = item.getTextRes();

        holder.text.setVisibility(View.VISIBLE);
        if (text != null) {
            holder.text.setText(text);
        } else if (textRes != 0) {
            holder.text.setText(textRes);
        } else {
            holder.text.setVisibility(GONE);
        }

        int pL = 0, pT = 0, pR = 0, pB = 0;
        if (Build.VERSION.SDK_INT < 21) {
            pL = holder.view.getPaddingLeft();
            pT = holder.view.getPaddingTop();
            pR = holder.view.getPaddingRight();
            pB = holder.view.getPaddingBottom();
        }

        if (Build.VERSION.SDK_INT < 21) {
            holder.view.setPadding(pL, pT, pR, pB);
        }
    }

    @Override
    public int getType() {
        return ViewTypeManager.ItemType.SECTION_ITEM;
    }

    @Override
    public String getDetailString() {
        return "MaterialAboutSectionItem{" +
                "text=" + text +
                ", textRes=" + textRes +
                '}';
    }

    public MaterialAboutSectionItem(MaterialAboutSectionItem item) {
        this.id = item.getId();
        this.text = item.getText();
        this.textRes = item.getTextRes();
    }

    @Override
    public MaterialAboutSectionItem clone() {
        return new MaterialAboutSectionItem(this);
    }

    public CharSequence getText() {
        return text;
    }

    public MaterialAboutSectionItem setText(CharSequence text) {
        this.textRes = 0;
        this.text = text;
        return this;
    }

    public int getTextRes() {
        return textRes;
    }

    public MaterialAboutSectionItem setTextRes(int textRes) {
        this.text = null;
        this.textRes = textRes;
        return this;
    }

    public static class MaterialAboutSectionItemViewHolder extends MaterialAboutItemViewHolder {
        public final View view;
        public final TextView text;

        MaterialAboutSectionItemViewHolder(View view) {
            super(view);
            this.view = view;
            text = (TextView) view.findViewById(R.id.mal_item_text);
        }
    }

    public static class Builder {

        private CharSequence text = null;
        @StringRes
        private int textRes = 0;

        public Builder text(CharSequence text) {
            this.text = text;
            this.textRes = 0;
            return this;
        }


        public Builder text(@StringRes int text) {
            this.textRes = text;
            this.text = null;
            return this;
        }

        public MaterialAboutSectionItem build() {
            return new MaterialAboutSectionItem(this);
        }
    }
}
