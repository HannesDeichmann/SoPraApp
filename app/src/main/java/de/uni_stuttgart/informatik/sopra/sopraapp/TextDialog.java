package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Verena Käfer on 13.11.2017.
 */

public class TextDialog extends DialogFragment {

    private DialogInterface.OnClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DialogInterface.OnClickListener){
            listener = (DialogInterface.OnClickListener) context;
        }
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.activity_main, null);
        TextView tvHelloRef = (TextView) v.findViewById(R.id.tvLogin);

        builder.setTitle(R.string.app_name);
        String message = tvHelloRef.getText().toString();
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("ok", listener);
        return builder.create();
    }
}
