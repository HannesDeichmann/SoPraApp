package de.uni_stuttgart.informatik.sopra.sopraapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DurationDialog extends AppCompatDialogFragment {
    private EditText etduration;
    private DurationDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.duration_dialog, null);

        builder.setView(view).setTitle("Duration").setNegativeButton("cancel", (dialog, i) -> {

                }
        ).setPositiveButton("ok", (dialog, i) -> {
            String duration = etduration.getText().toString();
            listener.applyText(duration);
        });
        etduration = view.findViewById(R.id.durationInput);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DurationDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Duration DialogListener");
        }
    }

    public interface DurationDialogListener {
        void applyText(String duration);
    }
}
