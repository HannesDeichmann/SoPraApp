package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;


public class WaypointFragment extends DialogFragment {
    private DialogInterface.OnClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DialogInterface.OnClickListener) {
            listener = (DialogInterface.OnClickListener) context;
        }
    }
    //TODO
    // -Erstelle Wegpunkte in Datei abspeichern
    // -Alle Attribute als EditText-Feld im Dialog anzeigen lassen und auslesen
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.New_Waypoint);
        builder.setCancelable(true);
        final TextView tvName = new TextView(this.getContext());
        tvName.setText("Name");
        builder.setView(tvName);
        final EditText etName = new EditText(this.getContext());
        builder.setView(etName);
        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etName.getText().toString();
                Waypoint waypoint = new Waypoint(name, 1);
            }
        });
        return builder.create();

    }
}


