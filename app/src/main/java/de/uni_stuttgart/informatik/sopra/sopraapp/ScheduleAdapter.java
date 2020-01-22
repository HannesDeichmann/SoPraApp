package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

import static de.uni_stuttgart.informatik.sopra.sopraapp.DbContract.DIVIDESTRING;
import static de.uni_stuttgart.informatik.sopra.sopraapp.ScheduleActivity.addRoutesFromDbToEmptyGuard;
import static de.uni_stuttgart.informatik.sopra.sopraapp.ScheduleActivity.tvSetTime;
import static de.uni_stuttgart.informatik.sopra.sopraapp.ScheduleActivity.routeStringList;
import static de.uni_stuttgart.informatik.sopra.sopraapp.ScheduleActivity.selectedGuard;
import static de.uni_stuttgart.informatik.sopra.sopraapp.ScheduleActivity.selectedRoute;
import static de.uni_stuttgart.informatik.sopra.sopraapp.ScheduleActivity.tvSelectedGuard;
import static de.uni_stuttgart.informatik.sopra.sopraapp.ScheduleActivity.tvSelectedRoute;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    ArrayList<String> adapterList;
    static DatabaseGuard databaseGuard;
    static DatabaseRoute databaseRoute;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvListItemText;
        ImageButton btnDeleteRef;
        ImageButton btnEditRef;

        public ViewHolder(View v) {
            super(v);
            tvListItemText = v.findViewById(R.id.tvRecyclerView);
            btnDeleteRef = v.findViewById(R.id.btnDeleteRecyclerView);
            btnEditRef = v.findViewById(R.id.btnEditRecyclerView);
            btnEditRef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] splitted = routeStringList.get(getAdapterPosition()).split(":");
                    int routeId = Integer.valueOf(splitted[3].trim());
                    int guardId = Integer.valueOf(splitted[0].trim());
                    String time =splitted[4].split(DIVIDESTRING)[1];
                    selectedGuard = databaseGuard.getGuardById(guardId);
                    selectedRoute = databaseRoute.getRouteById(routeId);
                    tvSetTime.setText(time);
                    tvSelectedRoute.setText(selectedRoute.getRouteName());
                    tvSelectedGuard.setText(selectedGuard.getUserId() + ": " + selectedGuard.getForename());
                    selectedGuard = createGuardWithoutDeletedRoute(selectedGuard,time);
                    databaseGuard.addGuardRoute(selectedGuard);
                    routeStringList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
            btnDeleteRef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] splitted = routeStringList.get(getAdapterPosition()).split(":");
                    int routeId = Integer.valueOf(splitted[3].trim());
                    int guardId = Integer.valueOf(splitted[0].trim());
                    String time = routeStringList.get(getAdapterPosition()).split(DIVIDESTRING)[1];
                    selectedGuard = databaseGuard.getGuardById(guardId);;
                    selectedRoute = databaseRoute.getRouteById(routeId);
                    selectedGuard.setGuardRouteList(new ArrayList<GuardRoute>());
                    addRoutesFromDbToEmptyGuard(selectedGuard);
                    selectedGuard = createGuardWithoutDeletedRoute(selectedGuard,time);
                    databaseGuard.addGuardRoute(selectedGuard);
                    routeStringList.remove(getAdapterPosition());

                    tvSetTime.setText("");
                    tvSelectedRoute.setText("");
                    tvSelectedGuard.setText("");
                    selectedGuard = null;
                    selectedRoute = null;
                    notifyDataSetChanged();
                }
            });
        }
    }

    private Guard createGuardWithoutDeletedRoute(Guard selectedGuard, String time){
        ArrayList<GuardRoute> tempList = new ArrayList<>();
        for(GuardRoute gr:selectedGuard.getGuardRouteList()){
            if(!(gr.getRoute().getRouteId().equals(selectedRoute.getRouteId())
                    && gr.getTime().equals(time))){
                tempList.add(gr);
            }
        }
        selectedGuard.setGuardRouteList(tempList);
        return selectedGuard;
    }

    public ScheduleAdapter(ArrayList<String> adapterList) {
        this.adapterList = adapterList;
    }

    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.tvListItemText.setText(adapterList.get(position).toString());
    }

    @Override
    public int getItemCount(){
        return adapterList.size();
    }
}

