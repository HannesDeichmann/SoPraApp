package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ProtocolAdapter extends RecyclerView.Adapter<ProtocolAdapter.ViewHolder> {
    ArrayList<String> adapterList;
    static DatabasePatrol databasePatrol;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvListItemText;
        ImageButton btnDeleteRef;
        ImageButton btnEditRef;

        public ViewHolder(View v) {
            super(v);
            tvListItemText = v.findViewById(R.id.tvRecyclerView);
            btnDeleteRef = v.findViewById(R.id.btnDeleteRecyclerView);
            btnEditRef = v.findViewById(R.id.btnEditRecyclerView);
            btnEditRef.setVisibility(View.INVISIBLE);

            btnDeleteRef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databasePatrol.deletePatrol(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public ProtocolAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.tvListItemText.setText(databasePatrol.getPatrol(position));
    }

    @Override
    public int getItemCount(){ return databasePatrol.getPatrolCount(); }
}

