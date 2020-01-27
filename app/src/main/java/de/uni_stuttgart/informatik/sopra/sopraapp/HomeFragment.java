package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private TextView tvCountdownRef;
    private TextView tvNextWaypointNameRef;
    private TextView tvNoteRef;
    private ImageButton btnStartCountdownRef;
    private ImageButton btnFinishRouteRef;
    private ImageButton btnCancelActiveRouteRef;
    public int nextWaypointCounter;
    public boolean timeRunning = true;
    private Route route;
    private ProgressBar progressBar;

    public static HomeFragment newInstance(Route route, Integer nextWaypointCounter){
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("route", route);
        bundle.putInt("nextWaypointCounter", nextWaypointCounter);
        fragment.setArguments(bundle);

        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        nextWaypointCounter = getArguments().getInt("nextWaypointCounter");
        route = (Route) getArguments().getSerializable("route");
        setRoute(route);

        tvCountdownRef = view.findViewById(R.id.tvCountdown);
        tvNoteRef = view.findViewById(R.id.tvNote);
        tvNextWaypointNameRef = view.findViewById(R.id.tvNextWaypointName);

        progressBar = view.findViewById(R.id.progressBar);
        btnStartCountdownRef = view.findViewById(R.id.btnStartCountdown);
        btnFinishRouteRef = view.findViewById(R.id.btnFinishRoute);
        btnCancelActiveRouteRef = view.findViewById(R.id.btnCancelActiveRoute);
        btnFinishRouteRef.setVisibility(View.INVISIBLE);

        ((PatrolActivity)getActivity()).setFragmentRefreshListener(new PatrolActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh(String formattedTime) {
                tvCountdownRef.setText(formattedTime);
                String[] s = formattedTime.split(":");
                long minutes = Long.valueOf(s[0].trim());
                long seconds = Long.valueOf(s[1].trim());
                long entireSeconds = minutes*60 + seconds;
                long duration = (route.getWaypoints().get(nextWaypointCounter).getDuration().getSeconds());
                progressBar.setProgress(Math.toIntExact(duration/entireSeconds));
            }
        });

        this.setupInformationFragment();

        btnFinishRouteRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PatrolActivity)getActivity()).finishRoute();
            }

        });

        btnStartCountdownRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PatrolActivity)getActivity()).startStop();
                if(timeRunning){
                    btnStartCountdownRef.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
                    timeRunning = false;
                }else{
                    btnStartCountdownRef.setBackgroundResource(R.drawable.ic_pause_circle_filled_black_24dp);
                    timeRunning = true;
                }

            }
        });

        btnCancelActiveRouteRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PatrolActivity)getActivity()).cancelActiveRoute();
                /**
                 * TODO Log Entry Cancelled Route
                 */
            }

        });
        return view;
    }

    public void setupInformationFragment() {

        if (nextWaypointCounter == getRoute().getWaypoints().size()) {
            btnFinishRouteRef.setVisibility(View.VISIBLE);
            btnStartCountdownRef.setVisibility(View.INVISIBLE);
            btnCancelActiveRouteRef.setVisibility(View.INVISIBLE);
            tvNextWaypointNameRef.setText("");
            tvNoteRef.setText("Please finish route");
        } else {
            RouteWaypoint nextWaypoint = getRoute().getWaypoints().get(nextWaypointCounter);
            tvNoteRef.setText(nextWaypoint.getWaypoint().getWaypointNote());
            tvNextWaypointNameRef.setText(nextWaypoint.getWaypoint().getWaypointName());
        }
    }
    public Route getRoute(){
        return this.route;
    }
    public void setRoute(Route route){
        this.route = route;
    }

}
