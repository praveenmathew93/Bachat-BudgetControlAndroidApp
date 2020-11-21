package com.example.Bachat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class EarningEditUpdateScreen extends AppCompatActivity {
    DatabaseHelper myDb;
    private static final String TAG = "EarningEditScreen";
    public String fromShowAll;
    public String id;
    String[] splitstring;

    public FloatingActionButton updateButton;
    public String updatedea;
    public String updateded;
    public String updatedn;
    DatePickerDialog picker;

    public Button addnote;
    public String notecheck = "-!@#$---";
    String originalnotecheck = "-!@#$---";
    String noteFromDb;

    //Additional
    public String updatedMop;
    Spinner editSpinnerMOP;
    StringBuffer currEntered;
    StringBuffer modeEntered;
    String updatedCurr;
    StringBuffer emailEntered;
    StringBuffer phoneEntered;
    StringBuffer conEntered;
    String contactName;
    StringBuilder sb = new StringBuilder();
    StringBuilder sb1 = new StringBuilder();
    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;
    static final int PICK_CONTACT = 1;
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    EditText editContact;

    //new buttons and variables for currency
    EditText addedContact;
    ImageButton buttonRemContact;
    EditText addContacts;
    String phone;
    String email;

    //defined for currency
    public static BreakIterator data;
    List<String> keysList;
    List<String> RateList;
    ArrayAdapter<CharSequence> currencyAdapter;
    Spinner currencySpinner;
    EditText editamount;
    String baseCurr;
    String toCurr;
    double BaseValue;
    public String currSelected;
    public String currency;
    public static int posconversion;
    public static int negconversion;
    public static int posrates;
    public static int negrates;
    double convertedAmout=0f;
    String resultrates = null;
    String resultconversion = null;
    String editTextValue;
    String originalSpinner;
    //end of currency definition

    Button noteHelp;
    EditText mop_icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
        fromShowAll =getIntent().getStringExtra("toeditfromearningshowall");
        splitstring= fromShowAll.split(",");
        setTheme(getCustomTheme(splitstring[1]));
        Log.d(TAG, "onCreate: EditScreen created");
        setContentView(R.layout.earning_edit_update_screen);
        // TextView editid =(TextView) findViewById(R.id.textViewEditId);
        TextView editcategory =(TextView) findViewById(R.id.textViewEditCategory);
        editamount = (EditText) findViewById(R.id.editTextEditAmount);
        final EditText editdate = (EditText) findViewById(R.id.editTextEditDate);
        final EditText editnote = (EditText) findViewById(R.id.editTextEditNote);
        //Contact button logic
        addContacts =  findViewById(R.id.btnAddContact);
        addedContact =  findViewById(R.id.btnAddedContact);
        buttonRemContact =  findViewById(R.id.btnRemContact);
        noteHelp = findViewById(R.id.btnHelp);
        currencySpinner = (Spinner) findViewById(R.id.spnCurr);
        mop_icon = findViewById(R.id.mop_icon);
        editSpinnerMOP = (Spinner) findViewById(R.id.spnEditMOP);
        addnote = findViewById(R.id.btnEditAddNote);





        id= splitstring[0];

        ////********Category Start********////

        editcategory.setText(splitstring[1]);

        ////********Category End********////


        ////********Amount and Currency Start********////
        editamount.setText(splitstring[2]);
        editamount.setSelection(editamount.getText().length());
        setEditTextCursorColor(editamount,getResources().getColor(R.color.primarytextbetadarkbackground,getTheme()));
        //To set currency
        Cursor resCurr = myDb.getEarningCurrencyEntered(splitstring[0]);
        currEntered= new StringBuffer();
        while (resCurr.moveToNext())
        {
            currEntered.append(resCurr.getString(0));
        }

        //The following code is for the text spinner to enable the selection of the currency

        currencyAdapter = ArrayAdapter.createFromResource(this,R.array.Currencies,R.layout.currency_spinner_layout);
        //Currencies is the list specified in strings.xml
        currencyAdapter.setDropDownViewResource(R.layout.curreny_spinner_drop_down_layout);
        currencySpinner.setAdapter(currencyAdapter);
        String compareValueCurr = currEntered.toString();
        if (compareValueCurr != null) {
            int spinnerPosition = currencyAdapter.getPosition(compareValueCurr);
            currencySpinner.setSelection(spinnerPosition);
        }
        originalSpinner = compareValueCurr;
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                updatedCurr = parent.getItemAtPosition(position).toString();
                if (!updatedCurr.equals(originalSpinner) && updatedCurr.equals(HomeFragment.default_currency)){
                    if(editamount.getText().toString().equals("")){
                        convertedAmout = 0;
                    }else{
                        convertedAmout = Double.valueOf(editamount.getText().toString());
                    }
                    return;
                }
                if (updatedCurr.equals(HomeFragment.default_currency)){
                    if(editamount.getText().toString().equals("")){
                        convertedAmout = 0;
                    }else{
                        convertedAmout = Double.valueOf(editamount.getText().toString());
                    }
                    return;
                }

                String curr = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(parent.getContext(),mode+" chosen",Toast.LENGTH_LONG).show();
                    currency = curr;
                    currSelected = curr;
                    Double passedConverted = convertedAmout;
                    editamount = (EditText) findViewById(R.id.editTextEditAmount);
                    String amountcheck = editamount.getText().toString();
                    Log.d(TAG, "onItemSelected: before the ifs: " + curr);
                    if (amountcheck.length() == 0) {
                        if (curr == HomeFragment.default_currency) {
                            currSelected = curr;
                        }
                        if (!curr.equals(HomeFragment.default_currency)) {
                            Toast.makeText(EarningEditUpdateScreen.this, "Please enter amount before selecting a currency", Toast.LENGTH_SHORT).show();
                        }
                        String compareValue = HomeFragment.default_currency;
                        if (compareValue != null) {
                            int spinnerPosition = currencyAdapter.getPosition(compareValue);
                            currencySpinner.setSelection(spinnerPosition);
                        }
                    } else if (Float.parseFloat(amountcheck) >= 99999999f) {
                        Toast.makeText(EarningEditUpdateScreen.this, "Please enter amount which is smaller than 99999999", Toast.LENGTH_SHORT).show();
                    } else if (Float.parseFloat(amountcheck) < 0f) {
                        Toast.makeText(EarningEditUpdateScreen.this, "Please enter amount which is greater than 0", Toast.LENGTH_SHORT).show();
                    } else if (Float.parseFloat(amountcheck) >= 99999999f || Float.parseFloat(amountcheck) < 0f) {

                    } else if (Float.parseFloat(amountcheck) < 99999999f && Float.parseFloat(amountcheck) >= 0f && !(amountcheck.length() == 0))
                        //Conversion
                        if (!currSelected.equals(HomeFragment.default_currency)) {
                            // this is the currency in which the user is currently entering amount

                            baseCurr = currency.substring(0, 3);
                            Log.d(TAG, "onClick: baseCurr :" + baseCurr);
                            //this is the currency which the users selects as default currency for his use (assuming euro here, will be set by the user in the app , all his transactions will be in this currency by default)


                            toCurr = HomeFragment.default_currency.substring(0, 3);
                            Log.d(TAG, "onClick: toCurrency  " + toCurr);
                            //this is the amount of money he has entered as being spent in the base currency
                            BaseValue = Float.parseFloat(editamount.getText().toString());

                            try {

                                Boolean check = haveNetworkConnection();
                                if (check) {
                                    Log.d(" connection test output is : " + check, "; internet working");
                                    resultrates = loadConvTypes();
                                    while (resultrates == null) {

                                    }

                                    if (EarningEditUpdateScreen.posrates == 1) {
                                        Log.d("on result check for rates", "everything is ok, we fetched live rates");
                                    } else if (EarningEditUpdateScreen.negrates == 1) {
                                        Log.d("on result check for rates", "we are facing some issues getting results at the moment");
                                    }
                                } else {
                                    Log.d(" connection test output is : " + check, "; There seems to be an some connection issues,  please check that you are connected to the internet");
                                    //some code for fetching old rates from the internal database for conversion.
                                    Cursor curRes = myDb.getOfflineRate(currSelected);
                                    String rate = "0";
                                    while (curRes.moveToNext()) {
                                        rate = curRes.getString(0);
                                    }
                                    updatedea = editamount.getText().toString();
                                    Log.d(TAG, "onItemSelected: If there is no net, values of converted amount, and rate are: " + updatedea + " and " + rate);
                                    convertedAmout = Double.parseDouble(updatedea) / Double.parseDouble(rate);
                                    Toast.makeText(EarningEditUpdateScreen.this, "No network connection. Using offline rate of 1 " + HomeFragment.default_currency + " = " + Double.parseDouble(rate) + " " + currSelected, Toast.LENGTH_LONG).show();


                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (//!edtEuroValue.getText().toString().isEmpty()//
                                    true) {

                                //define a spinner to choose the currency for payment, toCurr
                                // get the value of amount from the edit tet for amount, BaseValue

                                //String toCurr = toCurrency.getSelectedItem().toString();
                                //double BaseValue = Double.valueOf(edtEuroValue.getText().toString());
                                //Toast.makeText(MainActivity.this, "Please Wait..", Toast.LENGTH_SHORT).show();
                                try {
                                    resultconversion = convertCurrency(toCurr, BaseValue);
                                    while (resultconversion == null) {

                                    }
                                    if (EarningEditUpdateScreen.posconversion == 1) {
                                        Log.d("on result check", "everything is ok, values converted from live rates");
                                    } else if (EarningEditUpdateScreen.negconversion == 1) {
                                        Log.d("on result check", "we are facing some issues getting results at the moment, and now will proceed to use saved values from the database");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(EarningEditUpdateScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EarningEditUpdateScreen.this, "Please Enter a Value to Convert..", Toast.LENGTH_SHORT).show();
                            }

                        }

                //end of code for currency conversion
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ////********Amount and Currency End********////







        ////********Date Start********////
        Drawable drawable_date = getDrawable(R.drawable.ic_calendar);
        drawable_date = DrawableCompat.wrap(drawable_date);
        DrawableCompat.setTint(drawable_date, getResources().getColor(getColor(splitstring[1]),getTheme()));
        DrawableCompat.setTintMode(drawable_date, PorterDuff.Mode.SRC_IN);
        editdate.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable_date,null,null,null);

        editdate.setInputType(InputType.TYPE_NULL);
        String[] date = splitstring[3].split("/");
        String month = getMonth(Integer.parseInt(date[1])-1);
        editdate.setText(date[2]+"-"+month+"-"+date[0]);

        editdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(EarningEditUpdateScreen.this,getCustomDateTheme(splitstring[1]),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                if(String.valueOf(dayOfMonth).length() == 1){
                                    String date;
                                    if(dayOfMonth<=9) date= "0" + dayOfMonth;
                                    else date = String.valueOf(dayOfMonth);
                                    editTextValue = year+"/"+(monthOfYear+1)+"/"+ date;
                                    Log.d(TAG, "onClick: inside dayOfMonth loop: "+ editdate.getText());
                                    editdate.setText( date + "-" +  getMonth(monthOfYear) + "-" +  year );
                                }else {
                                    String date;
                                    if(dayOfMonth<=9) date= "0" + dayOfMonth;
                                    else date = String.valueOf(dayOfMonth);
                                    editTextValue = year+"/"+(monthOfYear+1)+"/"+ date;
                                    Log.d(TAG, "onClick: inside dayOfMonth loop: "+ editdate.getText());
                                    editdate.setText( date + "-" +  getMonth(monthOfYear) + "-" +  year );
                                }
                            }
                        }, year, month, day);
                picker.show();
            }
        });



        ////********Date End********////


        ////********Mode of Payment Start********////

        editSpinnerMOP.getBackground().setColorFilter(getResources().getColor(getColor(splitstring[1]),getTheme()), PorterDuff.Mode.SRC_ATOP);
        Cursor resMode = myDb.getEarningMop(splitstring[0]);
        modeEntered= new StringBuffer();
        while (resMode.moveToNext())
        {
            modeEntered.append(resMode.getString(0));
        }

        //Spinner setting for editing mode of payment

        ArrayAdapter<CharSequence> modeAdapter = ArrayAdapter.createFromResource(this,R.array.Modeofpayment,R.layout.mop_spinner_layout);

        //ModeofPayment is the list specified in strings.xml
        modeAdapter.setDropDownViewResource(R.layout.mop_spinner_drop_down);
        editSpinnerMOP.setAdapter(modeAdapter);
        String compareValueMOP = modeEntered.toString();
        if (compareValueMOP != null) {
            int spinnerPosition = modeAdapter.getPosition(compareValueMOP);
            editSpinnerMOP.setSelection(spinnerPosition);
        }

        Drawable drawable_mop = getDrawable(R.drawable.ic_payment);
        drawable_mop = DrawableCompat.wrap(drawable_mop);
        DrawableCompat.setTint(drawable_mop, getResources().getColor(getColor(splitstring[1]),getTheme()));
        DrawableCompat.setTintMode(drawable_mop, PorterDuff.Mode.SRC_IN);
        mop_icon.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable_mop,null,null,null);

        mop_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSpinnerMOP.performClick();

            }
        });
        ////********Mode of Payment End********////



        ////********Note Start********////


        editnote.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editnote.setRawInputType(InputType.TYPE_CLASS_TEXT);
        setEditTextCursorColor(editnote,getResources().getColor(getColor(splitstring[1]),getTheme()));
        Drawable drawable_note = getDrawable(R.drawable.ic_note);
        drawable_note = DrawableCompat.wrap(drawable_note);
        DrawableCompat.setTint(drawable_note, getResources().getColor(getColor(splitstring[1]),getTheme()));
        DrawableCompat.setTintMode(drawable_note, PorterDuff.Mode.SRC_IN);
        editnote.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable_note,null,null,null);
        addnote.setTextColor(getResources().getColor(getColor(splitstring[1]),getTheme()));

        //Note description dialog box
        noteHelp.setBackgroundColor(getResources().getColor(getColor(splitstring[1]),getTheme()));
        noteHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        //code for custom notes
        Cursor res = myDb.getNoteEarning(splitstring[0]);
        StringBuffer note= new StringBuffer();
        while (res.moveToNext())
        {
            note.append(res.getString(0));
        }

        noteFromDb= note.toString();
        Log.d(TAG, "noteFromDb is: " + noteFromDb);
        String b= noteFromDb.replaceAll("`"," ");
        String c= b.replaceAll("~"," ");
        String d = c.replaceAll("_"," ");


        SpannableStringBuilder spannablestring = new SpannableStringBuilder(d);
        StyleSpan normalspan = new StyleSpan(Typeface.NORMAL);
        StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
        StyleSpan italicsspan = new StyleSpan(Typeface.ITALIC);
        UnderlineSpan underlinespan = new UnderlineSpan();

        spannablestring.setSpan(normalspan,0,spannablestring.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        int boldstart = noteFromDb.indexOf("`");
        int boldend = noteFromDb.indexOf("`",boldstart+1);

        if (boldstart != -1 && boldend != -1){
            String bold= noteFromDb.substring(boldstart+1,boldend);
            //spannablestring.replace(boldstart,boldend+1,bold);
            spannablestring.setSpan(boldspan,boldstart,boldend,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        int italicsstart = noteFromDb.indexOf("~");
        int italicsend = noteFromDb.indexOf("~",italicsstart+1);

        if(italicsstart != -1 && italicsend != -1 ){
            String italics = noteFromDb.substring(italicsstart+1,italicsend);
            //spannablestring.replace(italicsstart,italicsend+1,italics);
            spannablestring.setSpan(italicsspan,italicsstart,italicsend,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }


        int underlinestart = noteFromDb.indexOf("_");
        int underlineend = noteFromDb.indexOf("_",underlinestart+1);

        if (underlinestart != -1 && underlineend != -1){
            String underline= noteFromDb.substring(underlinestart+1,underlineend);
            //spannablestring.replace(underlinestart,underlineend+1,underline);
            spannablestring.setSpan(underlinespan,underlinestart,underlineend,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if(noteFromDb.equals("Not Set")) editnote.setText("");
        else editnote.setText(spannablestring);
        Log.d(TAG, "" + editnote + " " +  spannablestring );


        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notecheck = editnote.getText().toString();
                String b= notecheck.replaceAll("`"," ");
                String c= b.replaceAll("~"," ");
                String d = c.replaceAll("_"," ");
                originalnotecheck =d;
                Log.d(TAG, "notecheck is: " + notecheck);


                //String emptystring = ".";
                //SpannableStringBuilder spannablestring = new SpannableStringBuilder(notecheck);
                SpannableStringBuilder spannablestring = new SpannableStringBuilder(d);
                StyleSpan normalspan = new StyleSpan(Typeface.NORMAL);
                StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
                StyleSpan italicsspan = new StyleSpan(Typeface.ITALIC);
                UnderlineSpan underlinespan = new UnderlineSpan();

                //spannablestring.setSpan(normalspan,0,emptystring.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

                //spannablestring.insert(emptystring.length(),notecheck);

                spannablestring.setSpan(normalspan,0,spannablestring.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                int boldstart = notecheck.indexOf("`");
                int boldend = notecheck.indexOf("`",boldstart+1);

                if (boldstart != -1 && boldend != -1){
                    String bold= notecheck.substring(boldstart+1,boldend);
                    //spannablestring.replace(boldstart,boldend+1,bold);
                    spannablestring.setSpan(boldspan,boldstart,boldend,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }


                int italicsstart = notecheck.indexOf("~");
                int italicsend = notecheck.indexOf("~",italicsstart+1);

                if(italicsstart != -1 && italicsend != -1 ){
                    String italics = notecheck.substring(italicsstart+1,italicsend);
                    //spannablestring.replace(italicsstart,italicsend+1,italics);
                    spannablestring.setSpan(italicsspan,italicsstart,italicsend,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                }


                int underlinestart = notecheck.indexOf("_");
                int underlineend = notecheck.indexOf("_",underlinestart+1);

                if (underlinestart != -1 && underlineend != -1){
                    String underline= notecheck.substring(underlinestart+1,underlineend);
                    //spannablestring.replace(underlinestart,underlineend+1,underline);
                    spannablestring.setSpan(underlinespan,underlinestart,underlineend,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }


                editnote.setText(spannablestring);
            }

        });
        ////********Note End********////





        ////********Contact Start********////
        Drawable drawable_add = getDrawable(R.drawable.ic_add_contact);
        drawable_add = DrawableCompat.wrap(drawable_add);
        DrawableCompat.setTint(drawable_add, getResources().getColor(getColor(splitstring[1]),getTheme()));
        DrawableCompat.setTintMode(drawable_add, PorterDuff.Mode.SRC_IN);
        addContacts.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable_add,null,null,null);

        Drawable drawable_added = getDrawable(R.drawable.ic_contact);
        drawable_added = DrawableCompat.wrap(drawable_added);
        DrawableCompat.setTint(drawable_added, getResources().getColor(getColor(splitstring[1]),getTheme()));
        DrawableCompat.setTintMode(drawable_added, PorterDuff.Mode.SRC_IN);
        addedContact.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable_added,null,null,null);


        //To set contactname

        Cursor resCon = myDb.getEarningContactName(splitstring[0]);
        conEntered= new StringBuffer();
        while (resCon.moveToNext())
        {
            conEntered.append(resCon.getString(0));
            Log.d(TAG, "onCreate: inside while loop value of contact entered is: " + conEntered);
        }

        //editContact = (EditText) findViewById(R.id.editTextContact);
        contactName = conEntered.toString();
        if(contactName.equals(""))
        {
            //editContact.setHint("Click here to add a contact");
            addContacts.setVisibility(View.VISIBLE);
            addedContact.setVisibility(View.INVISIBLE);
            buttonRemContact.setVisibility(View.INVISIBLE);
        }else{
            //editContact.setText(conEntered.toString());
            contactName = conEntered.toString();
            addContacts.setVisibility(View.INVISIBLE);
            addedContact.setVisibility(View.VISIBLE);
            addedContact.setText(contactName);
        }

        //To Add a contact
        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestContactPermission();
                /*Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);*/

            }
        });

        addedContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Opening the dialog: ");
                Bundle bundle = new Bundle();
                Log.d(TAG, "Values are:  "+ contactName + " " + sb + " " + sb1);
                bundle.putString("Contact", contactName);
                bundle.putString("Phone", phone);
                bundle.putString("Email", email);

                Log.d(TAG, "onClick: Bundle value is "+ bundle);

                ContactAlertDialog dialog = new ContactAlertDialog();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "ContactFilterDialog");
            }
        });


        //To Remove Contact

        buttonRemContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contactName != null){
                    contactName = "";
                    sb.setLength(0);
                    sb.append("");
                    sb1.setLength(0);
                    sb1.append("");
                    Toast.makeText(EarningEditUpdateScreen.this, "The contact has been removed", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EarningEditUpdateScreen.this, "You haven't added a contact", Toast.LENGTH_SHORT).show();
                }
                addedContact.setText("Add Contact");
                addedContact.setVisibility(View.INVISIBLE);
                addContacts.setVisibility(View.VISIBLE);
                buttonRemContact.setVisibility(View.INVISIBLE);
            }
        });


        //To set contactphone
        Cursor resPhone = myDb.getEarningContactPhone(splitstring[0]);
        phoneEntered= new StringBuffer();
        while (resPhone.moveToNext())
        {
            phoneEntered.append(resPhone.getString(0));
            phone = phoneEntered.toString();
        }


        //To set contactemail
        Cursor resEmail = myDb.getEarningContactEmail(splitstring[0]);
        emailEntered= new StringBuffer();
        while (resEmail.moveToNext())
        {
            emailEntered.append(resEmail.getString(0));
            email = emailEntered.toString();
        }



        ////********Contact End********////


        updateButton = findViewById(R.id.btnUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedea = editamount.getText().toString();
                updatedMop = editSpinnerMOP.getSelectedItem().toString();
                //updateded = editdate.getText().toString();
                String[] tempupdatedd = (editdate.getText().toString()).split("-");
                if(editTextValue==null){
                    editTextValue = tempupdatedd [2]+ "/" + getMonth(tempupdatedd [1]) + "/" + tempupdatedd [0];
                }

                updateded = editTextValue;//editdate.getText().toString();

                if(!editnote.getText().toString().trim().equals(noteFromDb) && !editnote.getText().toString().trim().equals(notecheck.trim()) && !editnote.getText().toString().trim().equals(originalnotecheck.trim()))
                {
                    Log.d(TAG, "inside if");

                    if(editnote.getText().toString().trim().length()>0){
                        updatedn = editnote.getText().toString().trim();
                    }
                    else{
                        updatedn = "Not Set";
                    }
                }

                else if(!notecheck.equals("-!@#$---") && !notecheck.trim().equals(noteFromDb)){
                    Log.d(TAG, "inside else if");
                    updatedn = notecheck.trim();
                }

                else{
                    Log.d(TAG, "inside else");
                    updatedn = noteFromDb;
                }


                if(updatedea.length() == 0)
                {
                    Toast.makeText(EarningEditUpdateScreen.this, "Please enter amount ", Toast.LENGTH_SHORT).show();
                }
                else if(Float.parseFloat(updatedea) >= 99999999f)
                {
                    Toast.makeText(EarningEditUpdateScreen.this, "Please enter amount which is smaller than 99999999", Toast.LENGTH_SHORT).show();
                }
                else if(Float.parseFloat(updatedea) < 0f)
                {
                    Toast.makeText(EarningEditUpdateScreen.this, "Please enter amount which is greater than 0", Toast.LENGTH_SHORT).show();
                }

                else if(Float.parseFloat(updatedea) >=0f && Float.parseFloat(updatedea) < 99999999f  && !(updatedea.length() == 0))
                {
                    UpdateData();
                }
                //Intent intent =new Intent(EarningEditScreen.this, EarningShowAllScreen.class);
                //startActivity(intent);
            }
        });

        /*FloatingActionButton fab_home= (FloatingActionButton)  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EarningEditUpdateScreen.this, MainActivity.class);
                startActivity(intent);

            }
        });*/

        ImageButton back =  findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarningEditUpdateScreen.this, TransactionHistory.class);
                intent.putExtra("open_view_all_income", "Open view all income screen");
                startActivity(intent);
            }
        });

    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(EarningEditUpdateScreen.this, TransactionHistory.class);
        intent.putExtra("open_view_all_income", "Open view all income screen");
        startActivity(intent);

    }



    //methods for contacts
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                   /* if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        try {
                            if (hasPhone.equalsIgnoreCase("1")) {
                                Cursor phones = getContentResolver().query(
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                        null, null);
                                phones.moveToFirst();
                                String cNumber = phones.getString(phones.getColumnIndex("data1"));
                                System.out.println("number is:" + cNumber);
                                Log.d(TAG, "number is:" + cNumber);
                                //txtphno.setText("Phone Number is: "+cNumber);
                            }
                            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            //txtname.setText("Name is: "+name);
                            System.out.println("name is:" + name);
                            Log.d(TAG, "name is:" + name);
                        }*/

                    while(c.moveToNext())
                    {
                        String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        contactName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        try{
                            //the below cursor will give you details for multiple contacts
                            Cursor emailCursor = getBaseContext().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                    new String[]{id}, null);
                            // continue till this cursor reaches to all emails  which are associated with a contact in the contact list
                            while (emailCursor.moveToNext())
                            {
                                int emailType         = emailCursor.getInt(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                                //String isStarred        = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                                String email    = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                //you will get all emails according to it's type as below switch case.
                                //Logs.e will print the phone number along with the name in DDMS. you can use these details where ever you want.

                                switch (emailType)
                                {
                                    case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                                        sb1.append(email+ " , ");
                                        Log.e(  " TYPE_MOBILE", " TYPE_MOBILE" + email + " ,");
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                        sb1.append(email+ " , ");
                                        Log.e(" TYPE_HOME", "TYPE_HOME " + email+ " ,");
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                        sb1.append(email+ " , ");
                                        Log.e(" TYPE_WORK", " TYPE_WORK" + email+ " ,");
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
                                        sb1.append(email+ " , ");
                                        Log.e(" TYPE_OTHER", " TYPE_OTHER" + email+ " ,");
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM:
                                        sb1.append(email+ " , ");
                                        Log.e(" TYPE_CUSTOM", " TYPE_CUSTOM" + email+ " ,");
                                        break;

                                }

                            }


                            emailCursor.close();
                        }

                        catch (Exception ex)
                        {
                            Log.d(TAG, "onActivityResult: inside catch block ," + ex.getMessage());
                            //st.getMessage();
                        }
                        Log.d("allnumbers", " " + sb.toString());
                        Log.d("contact name is: ", " "+ contactName );


                        if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                            try{
                                //the below cursor will give you details for multiple contacts
                                Cursor pCursor = getBaseContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                        new String[]{id}, null);
                                // continue till this cursor reaches to all phone numbers which are associated with a contact in the contact list
                                while (pCursor.moveToNext())
                                {
                                    int phoneType         = pCursor.getInt(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                                    //String isStarred        = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                                    String phoneNo    = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    //you will get all phone numbers according to it's type as below switch case.
                                    //Logs.e will print the phone number along with the name in DDMS. you can use these details where ever you want.

                                    switch (phoneType)
                                    {
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(  " TYPE_MOBILE", " TYPE_MOBILE" + phoneNo + " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_HOME", "TYPE_HOME " + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_WORK", " TYPE_WORK" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_WORK_MOBILE", " TYPE_WORK_MOBILE" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_OTHER", " TYPE_OTHER" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_RADIO:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_Radio", " TYPE_Radio" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_TTY_TDD", " TYPE_TTY_TDD" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_WORK_PAGER", " TYPE_WORK_PAGER" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_FAX_WORK", " TYPE_FAX_WORK" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_FAX_HOME", " TYPE_FAX_HOME" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_PAGER:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_PAGER", " TYPE_PAGER" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_CUSTOM", " TYPE_CUSTOM" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_CALLBACK", " TYPE_CALLBACK" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_CAR:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_CAR", " TYPE_CAR" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_COMPANY_MAIN", " TYPE_COMPANY_MAIN" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_ISDN:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_ISDN", " TYPE_ISDN" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_MAIN", " TYPE_MAIN" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MMS:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_MMS", " TYPE_MMS" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_OTHER_FAX", " TYPE_OTHER_FAX" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_TELEX:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_TELEX", " TYPE_TELEX" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_ASSISTANT", " TYPE_ASSISTANT" + phoneNo+ " ,");
                                            break;
                                        default:
                                            break;
                                    }

                                }


                                pCursor.close();
                            }

                            catch (Exception ex)
                            {
                                Log.d(TAG, "onActivityResult: inside catch block ," + ex.getMessage());
                                //st.getMessage();
                            }

                        Log.d("contact name is: ", " "+ contactName );
                        Log.d("allnumbers", " " + sb.toString());
                        Log.d("allemails", " " + sb1.toString());
                        phone = sb.toString();
                        email = sb1.toString();

                        addContacts.setVisibility(View.INVISIBLE);
                        addedContact.setVisibility(View.VISIBLE);
                        addedContact.setText(contactName);
                        buttonRemContact.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }

    //Methods for adding contact

    private void getContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);

    }



    public void requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Read Contacts permission");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Please enable access to contacts.");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {android.Manifest.permission.READ_CONTACTS}
                                    , PERMISSIONS_REQUEST_READ_CONTACTS);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.READ_CONTACTS},
                            PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            } else {
                getContacts();
            }
        } else {
            getContacts();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                } else {
                    Toast.makeText(this, "You have disabled a contacts permission", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    private void AccessContact()
    {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("Read Contacts");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_MULTIPLE_PERMISSIONS);
            return;
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);

            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(EarningEditUpdateScreen.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    //Method for checking if internet connection is available
    private boolean haveNetworkConnection() {
        Log.d("haveNetworkConnection:", " stared");
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        Log.d( "haveNetworkConnection:","now returning values");
        return haveConnectedWifi || haveConnectedMobile;
    }

    //methods for currency conversion
    public String loadConvTypes() throws IOException {
        String url;
        //This is the url for all the currencies compared to base currency
        String url1 = "https://api.exchangeratesapi.io/latest?base="+ baseCurr;
        //This is the url for conversion rates to app specific converison (we want conversion rates against toCurr)
        //By app specific i mean that , as we will provide conversion functionality to only a select few currencies in out spinner, we d not need to fetch data for all the currencies
        Log.d(TAG, "loadConvTypes: toCurr before if" + toCurr);
        if(!(toCurr.equals("EUR"))){
            url = "https://api.exchangeratesapi.io/latest?base="+ toCurr+"&&symbols=USD,GBP,INR,EUR";
        }else{
            url = "https://api.exchangeratesapi.io/latest?&&symbols=USD,GBP,INR";
        }


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                String mMessage = e.getMessage().toString();
                //Log.w("failure Response", mMessage);
                //Toast.makeText(EarningSecondScreen.this, mMessage, Toast.LENGTH_SHORT).show();
                setNegResponseRates();
                resetPosResponseRates();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String mMessage = response.body().string();

                EarningEditUpdateScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject obj = new JSONObject(mMessage);
                            JSONObject  b = obj.getJSONObject("rates");
                            Iterator keysToCopyIterator = b.keys();
                            keysList = new ArrayList<String>();
                            RateList = new ArrayList<String>();

                            while (keysToCopyIterator.hasNext()) {
                                String key = (String) keysToCopyIterator.next();
                                //Log.d(TAG, "string key is:" + key);
                                String RateToCopy = b.getString(key);
                                //Log.d(TAG, "string rate to copy is: " + RateToCopy);

                                keysList.add(key);
                                RateList.add(RateToCopy);
                            }
                            //code to print out the available currencies
                            //Log.d("key list size"," : " + keysList.size());
                            for(int i = 0; i < keysList.size(); i++) {
                                Log.d("", " " + i+ ". " + keysList.get(i)+ ", rate is: " + RateList.get(i));
                                myDb.insertConversionRates(keysList.get(i),RateList.get(i));
                            }
                            setPosResponseRates();
                            resetNegResponseRates();
                            // ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, keysList);
                            //toCurrency.setAdapter(spinnerArrayAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }

        });
        return "complete";
    }

    public String convertCurrency(final String toCurr, final double baseValue) throws IOException {
        String url;
        //This is the url for all the currencies compared to base currency
        String url1 = "https://api.exchangeratesapi.io/latest?base="+ baseCurr;
        //This is the url for conversion rates to app specific converison (we want conversion rates against toCurr)
        //By app specific i mean that , as we will provide conversion functionality to only a select few currencies in out spinner, we d not need to fetch data for all the currencies
        if(!toCurr.equals("EUR")){
            url = "https://api.exchangeratesapi.io/latest?base="+ toCurr+"&&symbols=USD,GBP,INR,EUR";
        }else{
            url = "https://api.exchangeratesapi.io/latest?&&symbols=USD,GBP,INR";
        }
        Log.d(TAG, "convertCurrency: toCurrency is " + toCurr);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                String mMessage = e.getMessage().toString();
                //Log.w("failure Response", mMessage);
                //Toast.makeText(EarningSecondScreen.this, mMessage, Toast.LENGTH_SHORT).show();
                setNegResponseConversion();
                resetPosResponseConversion();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                final String mMessage = response.body().string();
                EarningEditUpdateScreen.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject obj = new JSONObject(mMessage);
                            JSONObject  b = obj.getJSONObject("rates");
                            String val = b.getString(baseCurr);

                            double output = baseValue/Double.valueOf(val);

                            //logging the value of conversion
                            Log.d("converting amount", ": " + baseValue + " from : " + baseCurr + " to user's default currency , which is : " +toCurr);
                            Log.d("converted output is", " = " + output);
                            convertedAmout = output;
                            setPosResponseConversion();
                            resetNegResponseConversion();

                            //textView.setText(String.valueOf(output));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        return "complete";
    }

    public boolean setPosResponseConversion(){
        EarningEditUpdateScreen.posconversion =1;
        return true;
    }
    public boolean setNegResponseConversion(){
        EarningEditUpdateScreen.negconversion =1;
        return true;
    }
    public boolean resetPosResponseConversion(){
        EarningEditUpdateScreen.posconversion=0;
        return true;
    }

    public boolean resetNegResponseConversion(){
        EarningEditUpdateScreen.negconversion=0;
        return true;
    }

    public boolean setPosResponseRates(){
        EarningEditUpdateScreen.posrates =1;
        return true;
    }
    public boolean setNegResponseRates(){
        EarningEditUpdateScreen.negrates= 1;
        return true;
    }
    public boolean resetPosResponseRates(){
        EarningEditUpdateScreen.posrates =0;
        return true;
    }
    public boolean resetNegResponseRates(){
        EarningEditUpdateScreen.negrates =0;
        return true;
    }
    //end of code for currency conversion



    public void UpdateData() {
        // updateButton.setOnClickListener(
        //   new View.OnClickListener() {
        // @Override
        //public void onClick(View v) {
        phone = sb.toString();
        email = sb1.toString();

        //some code for fetching old rates from the internal database for conversion.
        if (currencySpinner.getSelectedItem().toString().equals(HomeFragment.default_currency)){
            if(editamount.getText().toString().equals("")){
                convertedAmout = 0;
            }else{
                convertedAmout = Double.valueOf(editamount.getText().toString());
            }
        }
        if (!currencySpinner.getSelectedItem().toString().equals(HomeFragment.default_currency))
        {
            Cursor curRes = myDb.getOfflineRate(currSelected);
            String rate = "0";
            while (curRes.moveToNext()) {
                rate = curRes.getString(0);
            }
            updatedea = editamount.getText().toString();
            Log.d(TAG, "onItemSelected: If there is no net, values of converted amount, and rate are: " + updatedea + " and " + rate);
            convertedAmout = Double.parseDouble(updatedea) / Double.parseDouble(rate);

            Log.d(TAG, "onClick: converted amount is ########### " + convertedAmout);
        }


        if(convertedAmout == 0){
            convertedAmout = Double.valueOf(updatedea);
            Log.d(TAG,"Default Amount" + convertedAmout);
        }
        Log.d(TAG, "UpdateData: The value of the finally converted amount is: "+ convertedAmout);
        boolean isUpdate = myDb.updateDataEarning(id,splitstring[1],updatedMop,updatedea,updateded,updatedn,updatedCurr,contactName,sb.toString(), sb1.toString(), convertedAmout);
        Log.d(TAG, "update string is : "+updatedea+" "+updateded);
        if(isUpdate == true) {
            Toast.makeText(EarningEditUpdateScreen.this, "Data Update", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EarningEditUpdateScreen.this, TransactionHistory.class);
            intent.putExtra("open_view_all_income", "Open view all income screen");
            startActivity(intent);
        }
        else {
            Toast.makeText(EarningEditUpdateScreen.this, "Data not Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EarningEditUpdateScreen.this, TransactionHistory.class);
            intent.putExtra("open_view_all_income", "Open view all income screen");
            startActivity(intent);
        }
        //}
        //  }
        //);
    }

    //private method to set default value for MOP spinner
    public int getIndex(Spinner spinner, String myString) {
        Log.d(TAG, "getIndex: CompareValue passing in" + myString);
        Log.d(TAG, "getIndex: Spinner value getting from position 2 " + spinner.getItemAtPosition(2));
        Log.d(TAG, "getIndex: Spinner value getting from position 2 converted to string " + spinner.getItemAtPosition(2).toString());
        Log.d(TAG, "getIndex: Comparison result " + spinner.getItemAtPosition(2).toString().equalsIgnoreCase(myString));
        for (int i = 0; i < spinner.getCount(); i++) {
            String adjust = " " + spinner.getItemAtPosition(i).toString();
            if (adjust.equalsIgnoreCase(myString)) {
                Log.d(TAG, "getIndex: Value will be " + i);
                return i;
            }
        }

        return 0;
    }

    //Method for noteDialog
    public void openDialog() {
        NoteDialog noteDialog = new NoteDialog();
        noteDialog.show(getSupportFragmentManager(), "NoteDialog");
    }

    public String getMonth(int month){
        switch (month){
            case 0: return "Jan";
            case 1: return "Feb";
            case 2: return "Mar";
            case 3: return "Apr";
            case 4: return "May";
            case 5: return "Jun";
            case 6: return "Jul";
            case 7: return "Aug";
            case 8: return "Sep";
            case 9: return "Oct";
            case 10: return "Nov";
            case 11: return "Dec";
        }
        return "Month";
    }


    public int getMonth(String month){
        switch (month){
            case "Jan": return 1;
            case "Feb": return 2;
            case "Mar": return 3;
            case  "Apr": return 4;
            case "May": return 5;
            case "Jun": return  6;
            case "Jul": return 7;
            case "Aug" : return 8;
            case "Sep": return 9;
            case "Oct": return 10;
            case "Nov": return 11;
            case "Dec": return 12;
        }
        return 1;
    }

    private int getCustomTheme(String Category){
        switch (Category) {
            case "Salary":
                return R.style.Salary;
            case "Business":
                return R.style.Business;
            case "Gifts":
                return R.style.Gifts;
            case "Bonus":
                return R.style.Bonus;
            case "Interest":
                return  R.style.Interest;
            case "Cashback":
                return  R.style.Cashback;
            case "Refund":
                return R.style.Refund;
            case "Donation":
                return R.style.Donation;
            case "Insurance":
                return R.style.Insurance;
            case "Credit Points":
                return R.style.CreditPoints;
            case "Extra Income":
                return R.style.ExtraIncome;
            case "Others":
                return R.style.Others;
            default:
                return R.style.BachatAppTheme;
        }
    }


    private int getCustomDateTheme(String Category){
        switch (Category) {
            case "Salary":
                return R.style.SalaryDatePickerDialogTheme;
            case "Business":
                return R.style.BusinessDatePickerDialogTheme;
            case "Gifts":
                return R.style.GiftsDatePickerDialogTheme;
            case "Bonus":
                return R.style.BonusDatePickerDialogTheme;
            case "Interest":
                return  R.style.InterestDatePickerDialogTheme;
            case "Cashback":
                return  R.style.CashbackDatePickerDialogTheme;
            case "Refund":
                return R.style.RefundDatePickerDialogTheme;
            case "Donation":
                return R.style.DonationDatePickerDialogTheme;
            case "Insurance":
                return R.style.InsuranceDatePickerDialogTheme;
            case "Credit Points":
                return R.style.CreditPointsDatePickerDialogTheme;
            case "Extra Income":
                return R.style.ExtraIncomeDatePickerDialogTheme;
            case "Others":
                return R.style.OthersDatePickerDialogTheme;
            default:
                return R.style.MyDatePickerDialogTheme;
        }
    }



    private int getColor(String Category){
        switch (Category) {
            case "Salary":
                return R.color.salary;
            case "Business":
                return R.color.business;
            case "Gifts":
                return R.color.gifts;
            case "Bonus":
                return R.color.bonus;
            case "Interest":
                return  R.color.interest;
            case "Cashback":
                return  R.color.cashback;
            case "Refund":
                return R.color.refund;
            case "Donation":
                return R.color.donation;
            case "Insurance":
                return R.color.insurance;
            case "Credit Points":
                return R.color.creditpoints;
            case "Extra Income":
                return R.color.extraincome;
            case "Others":
                return R.color.other_i;
            default:
                return R.color.other;
        }
    }


    public static void setEditTextCursorColor(EditText editText, int color) {
        try {
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(editText);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(editText);

            final Field fSelectHandleLeft = editor.getClass().getDeclaredField("mSelectHandleLeft");
            final Field fSelectHandleRight = editor.getClass().getDeclaredField("mSelectHandleRight");
            final Field fSelectHandleCenter = editor.getClass().getDeclaredField("mSelectHandleCenter");
            fSelectHandleLeft.setAccessible(true);
            fSelectHandleRight.setAccessible(true);
            fSelectHandleCenter.setAccessible(true);



            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(editText.getContext(), drawableResId);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);


            // Set the drawables
            if(Build.VERSION.SDK_INT >= 28){//set differently in Android P (API 28)
                field = editor.getClass().getDeclaredField("mDrawableForCursor");
                field.setAccessible(true);
                field.set(editor, drawable);
            }else {
                Drawable[] drawables = {drawable, drawable};
                field = editor.getClass().getDeclaredField("mCursorDrawable");
                field.setAccessible(true);
                field.set(editor, drawables);
            }

            //optionally set the "selection handle" color too
            //setEditTextHandleColor(editText, color);
        } catch (Exception ignored) {}

    }

}
