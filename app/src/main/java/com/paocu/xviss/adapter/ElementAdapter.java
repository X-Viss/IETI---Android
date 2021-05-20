package com.paocu.xviss.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.paocu.xviss.R;
import com.paocu.xviss.model.GeneritToUserRolWeatherOrCategory;
import com.paocu.xviss.model.util.Store;

import java.util.ArrayList;


public class ElementAdapter extends RecyclerView.Adapter<ElementAdapter.ElementViewHolder> {
    private ArrayList<GeneritToUserRolWeatherOrCategory> mDataSet;

    public ElementAdapter(ArrayList<GeneritToUserRolWeatherOrCategory> elements) {
        mDataSet = elements;
    }

    @Override
    public ElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new ElementViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ElementViewHolder holder, int position) {
        holder.mTextView.setText(mDataSet.get(position).getName());
        holder.mCheckBox.setChecked(mDataSet.get(position).isCheck());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ElementViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public CheckBox mCheckBox;
        public ElementViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textElement);
            mCheckBox = (CheckBox) v.findViewById(R.id.checker);
        }
    }
}