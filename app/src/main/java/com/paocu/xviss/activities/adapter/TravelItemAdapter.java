package com.paocu.xviss.activities.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paocu.xviss.R;
import com.paocu.xviss.model.Travel;

import java.util.List;

public class TravelItemAdapter extends RecyclerView.Adapter<TravelItemAdapter.ViewHolder>{

    private List<Travel> travelList;

    public TravelItemAdapter(List<Travel> travelList){
        this.travelList = travelList;
    }

    @NonNull
    @Override
    public TravelItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.travelitemlayout, parent, false ) );
    }

    @Override
    public void onBindViewHolder(@NonNull TravelItemAdapter.ViewHolder holder, int position) {
        Travel travel = travelList.get(position);
        holder.place.setText(travel.getDestiny());
        holder.date.setText(travel.getDueDate().toString());
        holder.title.setText(travel.getTitle());
    }

    @Override
    public int getItemCount() {
        return travelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView place;
        public TextView date;
        public Button deleteButton;


        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.traveltitle);
            place = (TextView) itemView.findViewById(R.id.travelplace);
            date = (TextView) itemView.findViewById(R.id.traveldate);
            deleteButton = (Button) itemView.findViewById(R.id.traveldeletebutton);
        }
    }
}
