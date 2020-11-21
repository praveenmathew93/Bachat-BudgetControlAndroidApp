package com.example.Bachat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    View view;
    ImageButton namebutton;
    ImageButton securitybutton;
    ImageButton exportData;
    ImageButton importData;
    ImageButton help;
    ImageButton support;
    //ImageButton subcategorybutton;
    TextView nametext;
    TextView securitytext;
    TextView exporttext;
    TextView importtext;
    TextView helptext;
    TextView supportText;
    //TextView subcategorytext;


    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.import_to_screen);
        view = inflater.inflate(R.layout.settings, container, false);
        namebutton = view.findViewById(R.id.Name_Button);
        securitybutton = view.findViewById(R.id.Security_Button);
        //addProfilebutton = view.findViewById(R.id.addProfile);
        exportData = view.findViewById(R.id.export);
        importData = view.findViewById(R.id.importData);
        help = view.findViewById(R.id.help);
        support = view.findViewById(R.id.support);

        nametext = view.findViewById(R.id.Name);
        securitytext = view.findViewById(R.id.Security) ;
        //addProfiletext = view.findViewById(R.id.Security);
        exporttext = view.findViewById(R.id.export_text);
        importtext = view.findViewById(R.id.importData_text) ;
        helptext = view.findViewById(R.id.help_text);
        supportText = view.findViewById(R.id.support_text);

        namebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserScreen.class);
                startActivity(intent);
            }
        });

        nametext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUserScreen.class);
                startActivity(intent);
            }
        });
        securitybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditSecurityScreen.class);
                startActivity(intent);
            }
        });

        securitytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditSecurityScreen.class);
                startActivity(intent);
            }
        });

        exportData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExportActivity.class);
                startActivity(intent);
            }
        });

        exporttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExportActivity.class);
                startActivity(intent);
            }
        });


        importData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImportToScreen.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });

        importtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImportToScreen.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpMenuActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });

        helptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpMenuActivity.class);
                startActivity(intent);
               // Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SupportScreen.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });

        supportText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SupportScreen.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

    public void callParentMethod(){
        getActivity().onBackPressed();
    }
}
