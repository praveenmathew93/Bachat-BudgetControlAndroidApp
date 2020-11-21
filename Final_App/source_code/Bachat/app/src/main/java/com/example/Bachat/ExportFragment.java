package com.example.Bachat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ExportFragment extends Fragment {
    View view;
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.import_to_screen);
        view = inflater.inflate(R.layout.import_to_screen, container, false);
        ImageView earnimport =(ImageView) view.findViewById(R.id.imageViewEarning);
        ImageView expenseimport =(ImageView) view.findViewById(R.id.imageViewExpense);
        TextView earnimport_1=(TextView) view.findViewById(R.id.textViewImportEarning);
        TextView expenseimport_1=(TextView) view.findViewById(R.id.textViewImportExpense);

        earnimport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), ExportActivity.class);// to exportactivity class
                startActivity(intent);
            }
        });
        expenseimport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), ImportScreenSDCardLogic.class);
                Intent intent = new Intent(getActivity(), ExportActivity.class);
                startActivity(intent);
            }
        });
        earnimport_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(), ExportActivity.class);
                startActivity(intent);
            }
        });
        expenseimport_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), ImportScreenSDCardLogic.class);
                Intent intent = new Intent(getActivity(), ExportActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
}
