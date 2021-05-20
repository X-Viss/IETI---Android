package com.paocu.xviss.adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.paocu.xviss.R;
import com.paocu.xviss.model.util.Store;

import java.util.ArrayList;
import java.util.List;


public class InternAdapter extends RecyclerView.Adapter<InternAdapter.ViewHolder> {
    private ArrayList<Store> mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(TextView tv) {
            super(tv);
            mTextView = tv;
        }
    }

    public InternAdapter() {
        mDataSet = new ArrayList<Store>();
    }

    public void setDataSet(ArrayList<Store> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public InternAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_view, parent, false);

        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataSet.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}