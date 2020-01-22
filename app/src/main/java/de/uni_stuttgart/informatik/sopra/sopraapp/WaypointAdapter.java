package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WaypointAdapter extends RecyclerView.Adapter<WaypointAdapter.ViewHolder> {
    static DatabaseWaypoint databaseWaypoint;
    WaypointListActivity waypointListActivity;
    ArrayList<String> adapterList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvListItemText;
        ImageButton btnDeleteRef;
        ImageButton btnEditRef;

        public ViewHolder(View v) {
            super(v);
            tvListItemText = v.findViewById(R.id.tvRecyclerView);
            btnDeleteRef = v.findViewById(R.id.btnDeleteRecyclerView);
            btnEditRef = v.findViewById(R.id.btnEditRecyclerView);

            btnEditRef.setOnClickListener(v1 -> {
                WaypointActivity.newWaypoint = false;
                Intent intent = new Intent(waypointListActivity, WaypointActivity.class);
                waypointListActivity.startActivity(intent);
            });

            btnDeleteRef.setOnClickListener(v12 -> {
                databaseWaypoint.deleteWaypoint(getAdapterPosition());
                notifyDataSetChanged();
            });
        }
    }
    public WaypointAdapter(WaypointListActivity waypointListActivity, ArrayList<String> list){
        this.waypointListActivity = waypointListActivity;
        this.adapterList = list;
    }

    @Override
    public WaypointAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.tvListItemText.setText(adapterList.get(position));
    }

    @Override
    public int getItemCount(){ return adapterList.size(); }
}

