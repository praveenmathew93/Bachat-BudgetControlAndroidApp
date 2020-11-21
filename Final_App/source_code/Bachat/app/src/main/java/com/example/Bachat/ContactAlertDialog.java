package com.example.Bachat;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.Bachat.DatabaseHelper;
import com.example.Bachat.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ContactAlertDialog extends AppCompatDialogFragment {
    private static final String TAG = "ContactFilterDialog";

    public EditText contactName;
    public TextView endDate;
    public Button clear;
    DatabaseHelper myDb;
    private Bundle bundle = getArguments();
    public String nameOfContact;
    public String email = "No E-mail";
    public String phone = "No Numbers";
    int color;

    /*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            startDate.setText(savedInstanceState.getString("startingDate","Start Date"));
        }
    }
       @Override
 */
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        myDb = new DatabaseHelper(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_contact_display, null);



        Bundle bundle = this.getArguments();
        if (bundle != null) {
            nameOfContact = bundle.getString("Contact");
            phone = bundle.getString("Phone");
            email = bundle.getString("Email");
            if(nameOfContact == null)
            {
                nameOfContact = "No Name";
            }
            if(phone == null)
            {
                phone = "No Phone Number";
            }
            if(email == null)
            {
                email = "No Email";
            }
            Log.d(TAG, "onCreateDialog: values are: " + nameOfContact + " " + phone + " " + email);
        }


        builder.setView(view)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false);

        EditText textContactName =  view.findViewById(R.id.txtContactName);
        textContactName.setText(nameOfContact);

        //ListView for phone numbers
        ListView numberList = (ListView) view.findViewById(R.id.listNumbers);
        String[] bufferdata = phone.split(",");
        Log.d(TAG, "onCreateDialog: value of bufferdata: " + bufferdata);
        ArrayList<String> listNumbers= new ArrayList<>();
        for (int i = 0; i < bufferdata.length-1; i++)
        {
            Log.d(TAG, "onCreateDialog: value of bufferlist numbers " + bufferdata[i]);
            listNumbers.add(bufferdata[i]);
        }
        if(listNumbers.size()==0)listNumbers.add("No Phone Number");
        ArrayAdapter numAdapter = new ArrayAdapter(getActivity(),R.layout.contact_alert_list, listNumbers);
        numberList.setAdapter(numAdapter);

        //ListView for emails
        ListView emailList = (ListView) view.findViewById(R.id.listEmail);
        String[] bufferemail = email.split(",");
        Log.d(TAG, "onCreateDialog: value of bufferdata: " + bufferemail);
        ArrayList<String> listEmails= new ArrayList<>();
        for (int i = 0; i < bufferemail.length-1; i++)
        {
            Log.d(TAG, "onCreateDialog: value of bufferlist[i] emails" + bufferemail[i]);
            listEmails.add(bufferemail[i]);
        }
        Log.d(TAG, "onCreateDialog: Email size="+ listEmails.size()+" listNumbers size"+listNumbers.size());
        if(listEmails.size()==0)listEmails.add("No Email");
        ArrayAdapter emailAdapter = new ArrayAdapter(getActivity(),R.layout.contact_alert_list, listEmails);
        emailList.setAdapter(emailAdapter);


        return builder.create();
    }
}
