package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    private DatabaseRoute databaseRoute;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNoteTextRef;
        public ImageButton btDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNoteTextRef = itemView.findViewById(R.id.routeItem);
            btDelete = itemView.findViewById(R.id.delete_button);

            btDelete.setOnClickListener(v -> {
                databaseRoute.deleteRoute(databaseRoute.getRouteById(getAdapterPosition()));
                notifyDataSetChanged();
            });
        }
    }

    public RouteAdapter ( DatabaseRoute databaseRoute) { this.databaseRoute = databaseRoute;}

    @Override
    public RouteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.tvNoteTextRef.setText("test");
        holder.tvNoteTextRef.setText(databaseRoute.getRouteFromPosition(position).toString());
    }
    @Override
    public int getItemCount(){
        SQLiteDatabase db = databaseRoute.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + DbContract.TABLE_NAME_ROUTE, null);
        return c.getCount();
    }
}
