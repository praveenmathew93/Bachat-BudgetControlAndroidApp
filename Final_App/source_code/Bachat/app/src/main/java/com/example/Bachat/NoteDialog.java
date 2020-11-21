package com.example.Bachat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.Bachat.DatabaseHelper;
import com.example.Bachat.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NoteDialog extends AppCompatDialogFragment{
    private static final String TAG = "NoteDialog";



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


    LayoutInflater inflater = getActivity().getLayoutInflater();
    View view = inflater.inflate(R.layout.notes_helper_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
          builder.setView(view)
            //.setTitle("Date Filters ")

            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {


        }
    });

    return builder.create();
}
}
