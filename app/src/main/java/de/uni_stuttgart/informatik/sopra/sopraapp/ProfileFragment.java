package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    EditText etChangePasswordRef;
    EditText etConfirmPasswordRef;
    Button btnChangePasswordRef;
    TextView tvUsernameRef;
    Button btnLogOutRef;
    DatabaseGuard guardDatabase;
    final static String secretKey = "dasistdiesichereverschluesselung";
    Guard loggedInGuard;

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
        guardDatabase = new DatabaseGuard(getActivity());
        loggedInGuard = (Guard) getArguments().getSerializable("guard");
        tvUsernameRef.setText(loggedInGuard.getForename() + " " + loggedInGuard.getSurname());
        btnChangePasswordRef = view.findViewById(R.id.btnChangePassword);
        etChangePasswordRef = view.findViewById(R.id.etChangePassword);
        etConfirmPasswordRef = view.findViewById(R.id.etConfirmPassword);
        btnLogOutRef = view.findViewById(R.id.btnLogOut);


        btnChangePasswordRef.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String changePassword = etChangePasswordRef.getText().toString();
                String confirmPassword = etConfirmPasswordRef.getText().toString();
                if(changePassword.equals(confirmPassword)){
                    loggedInGuard.setUserPassword(AesCrypto.encrypt(changePassword,secretKey));
                    guardDatabase.editGuard(loggedInGuard);
                    Toast.makeText(getContext(),"Password successfully changed",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"Passwords are not the same",Toast.LENGTH_SHORT).show();
                }
            }
        });

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

