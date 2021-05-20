package com.paocu.xviss.activities.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paocu.xviss.ElementosListActivity;
import com.paocu.xviss.MainActivity;
import com.paocu.xviss.R;
import com.paocu.xviss.model.Travel;

import java.text.SimpleDateFormat;
import java.util.List;

public class TravelItemAdapter extends RecyclerView.Adapter<TravelItemAdapter.ViewHolder>{

    private List<Travel> travelList;
    private Context context;
    private ListItemsListener listItemsListener;

    public TravelItemAdapter(List<Travel> travelList, Context context){
        this.travelList = travelList;
        this.context = context;
    }

    @NonNull
    @Override
    public TravelItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.travelitemlayout, parent, false ) );
    }

    @Override
    public void onBindViewHolder(@NonNull TravelItemAdapter.ViewHolder holder, int position) {
        Travel travel = travelList.get(position);
        holder.travel = travel;
        System.out.println(travel);
        holder.context = context;
        holder.place.setText("ss");
        holder.date.setText(travel.getDueDate() == null ? "Sin fecha programada" : new SimpleDateFormat("dd/MM/yyyy").format(travel.getDueDate()));
        holder.title.setText(travel.getTitle());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(position);
                listItemsListener.onClickDelete(position);
            }
        });


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
        public Travel travel;
        public Context context;


        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.traveltitle);
            place = (TextView) itemView.findViewById(R.id.travelplace);
            date = (TextView) itemView.findViewById(R.id.traveldate);
            deleteButton = (Button) itemView.findViewById(R.id.traveldeletebutton);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("EDIT" + travel);
                    //TODO ADD EDIT TRAVEL INTENT
                    Intent editIntent = new Intent(context, ElementosListActivity.class);
                    editIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    System.out.println(travel.getTravelId());
                    editIntent.putExtra("TRAVEL_ID", travel.getTravelId());
                    context.startActivity(editIntent);
                }
            });
        }
    }

    public ListItemsListener getListItemsListener() {
        return listItemsListener;
    }

    public void setListItemsListener(ListItemsListener listItemsListener) {
        this.listItemsListener = listItemsListener;
    }
}
