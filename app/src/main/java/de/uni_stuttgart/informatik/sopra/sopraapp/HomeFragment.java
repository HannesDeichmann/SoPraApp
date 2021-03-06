package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.health.SystemHealthManager;
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
    public long duration = 0;
    public double percent = 0;
    public long progress = 0;
    public long allSeconds = 0;

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

        if (nextWaypointCounter != getRoute().getWaypoints().size()) {

            duration = (route.getWaypoints().get(nextWaypointCounter).getDuration().getSeconds());
            percent = (double) 100 / duration;
        }

        if(((PatrolActivity)getActivity()).getTimerRunning()){
            btnStartCountdownRef.setBackgroundResource(R.drawable.ic_pause_circle_filled_black_24dp);
            timeRunning = true;
        }else {
            btnStartCountdownRef.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
            timeRunning = false;
        }
        ((PatrolActivity)getActivity()).setFragmentRefreshListener(new PatrolActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh(String formattedTime, long allSeconds) {
                tvCountdownRef.setText(formattedTime);
                progress = (long) Math.ceil(percent* allSeconds);
                progressBar.setProgress(Math.toIntExact(progress));
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
            progressBar.setProgress(0);
            duration = (route.getWaypoints().get(nextWaypointCounter).getDuration().getSeconds());
            percent = (double) 100 / duration;


        }
    }
    public Route getRoute(){
        return this.route;
    }
    public void setRoute(Route route){
        this.route = route;
    }

}
