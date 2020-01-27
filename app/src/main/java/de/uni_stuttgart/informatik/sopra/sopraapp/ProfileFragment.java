package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {


    TextView tvUsernameRef;
    Button btnLogOutRef;

    public static ProfileFragment newInstance(Guard guard){
        ProfileFragment fragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("guard", guard);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container,false);
        tvUsernameRef = view.findViewById(R.id.tvUsername);
        Guard loggedInGuard = (Guard) getArguments().getSerializable("guard");
        tvUsernameRef.setText(loggedInGuard.getForename() + " " + loggedInGuard.getSurname());
        btnLogOutRef = view.findViewById(R.id.btnLogOut);

        btnLogOutRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                ((GuardModeActivity)getActivity()).closeActivity();
            }
        });

        return view;

    }
}

