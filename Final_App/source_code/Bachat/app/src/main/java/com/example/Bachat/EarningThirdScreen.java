package com.example.Bachat;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
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

import static com.example.Bachat.R.drawable.profile_screen;

public class EarningThirdScreen extends AppCompatActivity {
    private static final String TAG = "EarningThirdScreen";
    public String fromEarningSecond;
    public String fromThird;
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    static final int PICK_CONTACT = 1;
    String st;
    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;
    DatePicker picker;
    DatabaseHelper myDb;
    TextView toolbar_sub_title;
    public String category,currency;
    public String modeofpayment;
    public String amount;
    public String addedDate;
    public String addedNote;
    public String datePicked;
    public String mop;
    StringBuilder sb = new StringBuilder();
    StringBuilder sb1 = new StringBuilder();
    String contactName;
    int dayOfMonth;
    int monthofyear;
    int year;
    String notecheck;
    EditText editnote;
    String originalnotecheck = "-!@#$---";
    Button addnote;

    //defined for currency
    public static BreakIterator data;
    ArrayAdapter<CharSequence> currencyAdapter;
    Spinner currencySpinner;
    EditText editamount;
    List<String> keysList;
    List<String> RateList;
    String baseCurr;
    String toCurr;
    double BaseValue;
    public String currSelected;
    public static int posconversion;
    public static int negconversion;
    public static int posrates;
    public static int negrates;
    double convertedAmout;
    String resultrates = null;
    String resultconversion = null;
    RelativeLayout relativeLayout;
    //end of currency definition

    //new buttons for currency
    EditText addedContact;
    ImageButton remContacts;
    EditText addContacts;
    EditText editText;


    Button noteHelp;
    EditText mop_icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(getCustomTheme(getIntent().getStringExtra("fromEarningSecond")));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.earning_third_screen);
        myDb = new DatabaseHelper(this);

        relativeLayout = findViewById(R.id.MainRelativeLayout);
        relativeLayout.setBackgroundColor(getColor(getColor(getIntent().getStringExtra("fromEarningSecond"))));


        editText = findViewById(R.id.Date);
        Drawable drawable = getDrawable(R.drawable.ic_calendar);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, getResources().getColor(getColor(getIntent().getStringExtra("fromEarningSecond")),getTheme()));
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        editText.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,null,null,null);
        final Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.YEAR, myCalendar.get(Calendar.YEAR));
        myCalendar.set(Calendar.MONTH, myCalendar.get(Calendar.MONTH));
        myCalendar.set(Calendar.DAY_OF_MONTH, myCalendar.get(Calendar.DAY_OF_MONTH));
        String textt=myCalendar.get(Calendar.DAY_OF_MONTH) + " " + getMonth(myCalendar.get(Calendar.MONTH)) + " "+ myCalendar.get(Calendar.YEAR);
        editText.setText(textt);
        dayOfMonth =  myCalendar.get(Calendar.DAY_OF_MONTH);
        monthofyear = myCalendar.get(Calendar.MONTH)+1;
        year = myCalendar.get(Calendar.YEAR);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int pyear, int pmonthOfYear,
                                  int pdayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, pyear);
                myCalendar.set(Calendar.MONTH, pmonthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, pdayOfMonth);
                String textt= pdayOfMonth + " " + getMonth(pmonthOfYear) + " "+ pyear;
                editText.setText(textt);
                dayOfMonth =  pdayOfMonth;
                monthofyear = pmonthOfYear+1;
                year = pyear;

                //updateLabel();
            }

        };




        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EarningThirdScreen.this,getCustomDateTheme(getIntent().getStringExtra("fromEarningSecond")),date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarningThirdScreen.this, EarningSecondScreen.class);
                startActivity(intent);
            }
        });



        toolbar_sub_title = findViewById(R.id.toolbar_sub_title);
        toolbar_sub_title.setText(getIntent().getStringExtra("fromEarningSecond") );
        //relativeLayout = findViewById(R.id.Parent_rel_layout);
        //relativeLayout.setBackgroundColor(R.drawable.add_expense);


        /*getSupportActionBar().setTitle("Income");
        getSupportActionBar().setSubtitle("Add Details to "+getIntent().getStringExtra("fromEarningSecond"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        Log.d(TAG, "onCreate: values of contact, sb, and sb1 are: "+ contactName + " "+ sb + " "+ sb1 + " ");

        /*Button convert = (Button) findViewById(R.id.btnConvert);

        convert.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                EditText amountEdit = (EditText) findViewById(R.id.editTextAmount2);
                if(currSelected.equals(HomeFragment.default_currency)) {
                    try{
                        convertedAmout = Float.parseFloat(amountEdit.getText().toString());
                        Toast.makeText(EarningThirdScreen.this, "The entered currency has been converted to " + convertedAmout + " " + HomeFragment.default_currency.substring(0, 3), Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText(EarningThirdScreen.this, "You haven't entered an amount yet", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    convertedAmout = Float.parseFloat(amountEdit.getText().toString());
                    Toast.makeText(EarningThirdScreen.this, "The entered currency has been converted to " + convertedAmout + " " + HomeFragment.default_currency.substring(0, 3), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EarningThirdScreen.this, "The entered currency has been converted to "+ convertedAmout + " " + HomeFragment.default_currency.substring(0,3), Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "onClick: :"+ currSelected + " and converted amount is " + convertedAmout + " and the actual amount is "+ amountEdit.getText().toString());

                }

        });*/

        //code for custom notes
        addnote =(Button) findViewById(R.id.btnAddNote);
        addnote.setTextColor(getResources().getColor(getColor(getIntent().getStringExtra("fromEarningSecond")),getTheme()));
        editnote = (EditText) findViewById(R.id.editTextNote2);
        editnote.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editnote.setRawInputType(InputType.TYPE_CLASS_TEXT);
        setEditTextCursorColor(editnote,getResources().getColor(getColor(getIntent().getStringExtra("fromEarningSecond")),getTheme()));
        Drawable drawable_note = getDrawable(R.drawable.ic_note);
        drawable_note = DrawableCompat.wrap(drawable_note);
        DrawableCompat.setTint(drawable_note, getResources().getColor(getColor(getIntent().getStringExtra("fromEarningSecond")),getTheme()));
        DrawableCompat.setTintMode(drawable_note, PorterDuff.Mode.SRC_IN);
        editnote.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable_note,null,null,null);

        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notecheck = editnote.getText().toString();
                originalnotecheck = notecheck;
                String b= originalnotecheck.replaceAll("`"," ");
                String c= b.replaceAll("~"," ");
                String d = c.replaceAll("_"," ");


                //String emptystring = ".";
                //SpannableStringBuilder spannablestring = new SpannableStringBuilder(notecheck);
                SpannableStringBuilder spannablestring = new SpannableStringBuilder(d);
                StyleSpan normalspan = new StyleSpan(Typeface.NORMAL);
                StyleSpan boldspan = new StyleSpan(Typeface.BOLD);
                StyleSpan italicsspan = new StyleSpan(Typeface.ITALIC);
                UnderlineSpan underlinespan = new UnderlineSpan();

                //spannablestring.setSpan(normalspan,0,emptystring.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

                //spannablestring.insert(emptystring.length(),notecheck);

                spannablestring.setSpan(normalspan,0,spannablestring.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

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

        //The following code is for note description dialog box
        noteHelp = findViewById(R.id.btnHelp);
        noteHelp.setBackgroundColor(getResources().getColor(getColor(getIntent().getStringExtra("fromEarningSecond")),getTheme()));
        noteHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        //The following code is for the text spinner to enable the selection of the mode of payment
        final Spinner modeSpinner = (Spinner) findViewById(R.id.spnModes);
        ArrayAdapter<CharSequence> modeAdapter = ArrayAdapter.createFromResource(this,R.array.Modeofpayment,R.layout.mop_spinner_layout);
        //ModeofPayment is the list specified in strings.xml
        modeAdapter.setDropDownViewResource(R.layout.mop_spinner_drop_down);
        modeSpinner.setAdapter(modeAdapter);
        modeSpinner.getBackground().setColorFilter(getResources().getColor(getColor(getIntent().getStringExtra("fromEarningSecond")),getTheme()), PorterDuff.Mode.SRC_ATOP);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                String mode = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(),mode+" chosen",Toast.LENGTH_LONG).show();
                mop = mode;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mop_icon = findViewById(R.id.mop_icon);
        Drawable drawable_mop = getDrawable(R.drawable.ic_payment);
        drawable_mop = DrawableCompat.wrap(drawable_mop);
        DrawableCompat.setTint(drawable_mop, getResources().getColor(getColor(getIntent().getStringExtra("fromEarningSecond")),getTheme()));
        DrawableCompat.setTintMode(drawable_mop, PorterDuff.Mode.SRC_IN);
        mop_icon.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable_mop,null,null,null);

        mop_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modeSpinner.performClick();

            }
        });

        //The following code is for the text spinner to enable the selection of the currency
        currencySpinner = (Spinner) findViewById(R.id.spnCurrency);
        currencyAdapter = ArrayAdapter.createFromResource(this,R.array.Currencies,R.layout.currency_spinner_layout);
        //Currencies is the list specified in strings.xml
        currencyAdapter.setDropDownViewResource(R.layout.curreny_spinner_drop_down_layout);
        currencySpinner.setAdapter(currencyAdapter);
        String compareValue = HomeFragment.default_currency;
        if (compareValue != null) {
            int spinnerPosition = currencyAdapter.getPosition(compareValue);
            currencySpinner.setSelection(spinnerPosition);
        }
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                String curr = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(),mode+" chosen",Toast.LENGTH_LONG).show();
                currency = curr;
                currSelected = curr;
                Double passedConverted = convertedAmout;
                editamount = (EditText) findViewById(R.id.editTextAmount2);
                setEditTextCursorColor(editamount,getResources().getColor(R.color.primarytextbetadarkbackground,getTheme()));
                String amountcheck = editamount.getText().toString();

                Log.d(TAG, "onItemSelected: before the ifs: " + curr);
                if (amountcheck.length() == 0) {
                    if(curr.equals(HomeFragment.default_currency)){
                        currSelected = curr;
                    }
                    if(!curr.equals(HomeFragment.default_currency)){
                        Toast.makeText(EarningThirdScreen.this, "Please enter amount before selecting a currency", Toast.LENGTH_SHORT).show();
                    }

                    String compareValue = HomeFragment.default_currency;
                    if (compareValue != null) {
                        int spinnerPosition = currencyAdapter.getPosition(compareValue);
                        currencySpinner.setSelection(spinnerPosition);
                    }
                }
                else if(Float.parseFloat(amountcheck) >= 99999999f)
                {
                    Toast.makeText(EarningThirdScreen.this, "Please enter amount which is smaller than 99999999", Toast.LENGTH_SHORT).show();
                }
                else if(Float.parseFloat(amountcheck) < 0f)
                {
                    Toast.makeText(EarningThirdScreen.this, "Please enter amount which is greater than 0", Toast.LENGTH_SHORT).show();
                }
                else if(Float.parseFloat(amountcheck) >= 99999999f || Float.parseFloat(amountcheck) < 0f)
                {

                }
                else if(Float.parseFloat(amountcheck) < 99999999f  && Float.parseFloat(amountcheck) >= 0f  && !(amountcheck.length() == 0))
                    //Conversion
                    if(!currSelected.equals(HomeFragment.default_currency)){
                        // this is the currency in which the user is currently entering amount

                        baseCurr= currency.substring(0,3);
                        Log.d(TAG, "onClick: baseCurr :" + baseCurr);
                        //this is the currency which the users selects as default currency for his use (assuming euro here, will be set by the user in the app , all his transactions will be in this currency by default)


                        toCurr = HomeFragment.default_currency.substring(0,3);
                        Log.d(TAG, "onClick: toCurrency  " + toCurr);
                        //this is the amount of money he has entered as being spent in the base currency
                        BaseValue = Float.parseFloat(editamount.getText().toString());

                        try {

                            Boolean check = haveNetworkConnection();
                            if (check) {
                                Log.d(" connection test output is : " +check,"; internet working" );
                                resultrates = loadConvTypes();
                                while (resultrates == null){

                                }

                                if (EarningThirdScreen.posrates== 1){
                                    Log.d("on result check for rates", "everything is ok, we fetched live rates");
                                }
                                else if (EarningThirdScreen.negrates == 1){
                                    Log.d("on result check for rates", "we are facing some issues getting results at the moment");
                                }
                            }
                            else{
                                Log.d( " connection test output is : " +check,  "; There seems to be an some connection issues,  please check that you are connected to the internet"  );
                                //some code for fetching old rates from the internal database for conversion.
                                Cursor curRes = myDb.getOfflineRate(currSelected);
                                String rate="0";
                                while (curRes.moveToNext())
                                {
                                    rate = curRes.getString(0);
                                }
                                amount = editamount.getText().toString();
                                Log.d(TAG, "onItemSelected: If there is no net, values of converted amount, and rate are: " + amount + " and " + rate);
                                convertedAmout = Double.parseDouble(amount) / Double.parseDouble(rate);
                                Toast.makeText(EarningThirdScreen.this, "No network connection. Using offline rate of 1 " + HomeFragment.default_currency + " = " + Double.parseDouble(rate) + " " + currSelected, Toast.LENGTH_LONG).show();

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(//!edtEuroValue.getText().toString().isEmpty()//
                                true)
                        {

                            //define a spinner to choose the currency for payment, toCurr
                            // get the value of amount from the edit tet for amount, BaseValue

                            //String toCurr = toCurrency.getSelectedItem().toString();
                            //double BaseValue = Double.valueOf(edtEuroValue.getText().toString());
                            //Toast.makeText(MainActivity.this, "Please Wait..", Toast.LENGTH_SHORT).show();
                            try {
                                resultconversion=convertCurrency(toCurr, BaseValue);
                                while (resultconversion == null){

                                }
                                if (EarningThirdScreen.posconversion == 1){
                                    Log.d("on result check", "everything is ok, values converted from live rates");
                                }
                                else if (EarningThirdScreen.negconversion == 1){
                                    Log.d("on result check", "we are facing some issues getting results at the moment, and now will proceed to use saved values from the database");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(EarningThirdScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(EarningThirdScreen.this, "Please Enter a Value to Convert..", Toast.LENGTH_SHORT).show();
                        }

                    }

                //end of code for currency conversion


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //code for contacts
        addContacts =  findViewById(R.id.AddContact);
        Drawable drawable_add_con = getDrawable(R.drawable.ic_add_contact);
        drawable_add_con = DrawableCompat.wrap(drawable_add_con);
        DrawableCompat.setTint(drawable_add_con, getResources().getColor(getColor(getIntent().getStringExtra("fromEarningSecond")),getTheme()));
        DrawableCompat.setTintMode(drawable_add_con, PorterDuff.Mode.SRC_IN);
        addContacts.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable_add_con,null,null,null);
        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestContactPermission();
            }
        });

        addedContact = findViewById(R.id.RemoveContact);

        Drawable drawable_added = getDrawable(R.drawable.ic_contact);
        drawable_added = DrawableCompat.wrap(drawable_added);
        DrawableCompat.setTint(drawable_added, getResources().getColor(getColor(getIntent().getStringExtra("fromEarningSecond")),getTheme()));
        DrawableCompat.setTintMode(drawable_added, PorterDuff.Mode.SRC_IN);
        addedContact.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable_added,null,null,null);
        addedContact.setVisibility(View.INVISIBLE);

        addedContact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Opening the dialog: ");
                Bundle bundle = new Bundle();
                Log.d(TAG, "Values are:  "+ contactName + " " + sb + " " + sb1);
                bundle.putString("Contact", contactName);
                bundle.putString("Phone", sb.toString());
                bundle.putString("Email", sb1.toString());

                Log.d(TAG, "onClick: Bundle value is "+ bundle);

                ContactAlertDialog dialog = new ContactAlertDialog();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "ContactFilterDialog");

            }
        });

        remContacts =  findViewById(R.id.buttonRemContact);
        remContacts.setVisibility(View.INVISIBLE);
        remContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contactName != null){
                    contactName = "";
                    sb.setLength(0);
                    sb.append("");
                    sb1.setLength(0);
                    sb1.append("");
                    Toast.makeText(EarningThirdScreen.this, "The contact has been removed", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(EarningThirdScreen.this, "You haven't added a contact", Toast.LENGTH_SHORT).show();
                }
                addedContact.setText("Add Contact");
                addedContact.setVisibility(View.INVISIBLE);
                addContacts.setVisibility(View.VISIBLE);
                remContacts.setVisibility(View.INVISIBLE);

            }
        });
        //end of code for contacts


        FloatingActionButton addButton = findViewById(R.id.btnAddTransaction);
        addButton.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),getColor(getIntent().getStringExtra("fromEarningSecond"))));
        addButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                TextView theTextViewDate = (TextView) findViewById(R.id.textViewDate);
                //picker = (DatePicker) findViewById(R.id.editTextDate2);
                TextView theTextViewNote = (TextView) findViewById(R.id.textViewNote);
                Log.d(TAG, "onClick: converted amount is "+ convertedAmout);


                TextView theTextViewAmount = (TextView) findViewById(R.id.textViewAmount);
                EditText editamount = (EditText) findViewById(R.id.editTextAmount2);


                //Check for contact
                if(contactName == null || contactName.equals("")){
                    contactName = "";
                    sb.setLength(0);
                    sb.append("");
                    sb1.setLength(0);
                    sb1.append("");
                }
                //myDb.storeEarningContacts(contactName,sb.toString(), sb1.toString());

                /*String date = picker.getYear() + "/" + (picker.getMonth() + 1) + "/" + picker.getDayOfMonth();


                dayOfMonth = picker.getDayOfMonth();
                if(String.valueOf(dayOfMonth).length() == 1){

                    datePicked = picker.getYear() + "/" + (picker.getMonth() + 1) + "/0" + picker.getDayOfMonth();
                    Log.d(TAG, "onClick: inside dayOfMonth loop: "+ datePicked);
                }else
                {datePicked = picker.getYear() + "/" + (picker.getMonth() + 1) + "/" + picker.getDayOfMonth();}*/
                datePicked = year + "/" + monthofyear + "/" + dayOfMonth;
                String notecheck = editnote.getText().toString();
                String note;
                String checker = editnote.getText().toString();

                if (checker.length() == 0) {
                    note = "Not Set";
                }
                else {
                    if(originalnotecheck.equals("-!@#$---")){
                        note = notecheck;
                    }
                    else{
                        note = originalnotecheck;
                    }

                }

                String amountcheck = editamount.getText().toString();

                if (amountcheck.length() == 0) {
                    Toast.makeText(EarningThirdScreen.this, "Please enter amount", Toast.LENGTH_SHORT).show();
                }
                else if(Float.parseFloat(amountcheck) >= 99999999f)
                {
                    Toast.makeText(EarningThirdScreen.this, "Please enter amount which is smaller than 99999999", Toast.LENGTH_SHORT).show();
                }
                else if(Float.parseFloat(amountcheck) < 0f)
                {
                    Toast.makeText(EarningThirdScreen.this, "Please enter amount which is greater than 0", Toast.LENGTH_SHORT).show();
                }
                else if(Float.parseFloat(amountcheck) >= 99999999f || Float.parseFloat(amountcheck) < 0f)
                {

                }
                else if(Integer.parseInt(amountcheck) < 99999999  && Integer.parseInt(amountcheck) >= 0  && !(amountcheck.length() == 0)){

                    amount = amountcheck;
                    fromThird = getIntent().getStringExtra("fromEarningSecond");
                    String toadd = fromThird + "," + mop + "," + amount + "," + datePicked + "," + note;
                    Log.d(TAG, "onClick: toadd is " + toadd);
                    String result[] = toadd.split(",");
                    category = result[0];
                    modeofpayment = result[1];
                    amount = result[2];
                    addedDate = result[3];
                    addedNote = result[4];
                    Log.d(TAG, "onClick: sending to db: " + note);
                    if(convertedAmout == 0){
                        convertedAmout = Double.valueOf(amount);
                        Log.d(TAG,"Default Amount" + convertedAmout);
                    }
                    Log.d(TAG, "onClick: contact name to be added from Expense is " + contactName);
                    Log.d(TAG, "onClick: contact number and email to be added from Expense is " + sb.toString() + " and " + sb1.toString());
                    boolean isInserted = myDb.storeEarningTransaction(category, modeofpayment, amount, addedDate, addedNote, currency, contactName, sb.toString(), sb1.toString(), convertedAmout);
                    if (isInserted = true)
                        Toast.makeText(EarningThirdScreen.this, "data inserted", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(EarningThirdScreen.this, "not inserted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EarningThirdScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        /*FloatingActionButton fab_home= (FloatingActionButton)  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EarningThirdScreen.this, MainActivity.class);
                startActivity(intent);

            }
        });*/
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

                EarningThirdScreen.this.runOnUiThread(new Runnable() {
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
                EarningThirdScreen.this.runOnUiThread(new Runnable() {
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
        EarningThirdScreen.posconversion =1;
        return true;
    }
    public boolean setNegResponseConversion(){
        EarningThirdScreen.negconversion =1;
        return true;
    }
    public boolean resetPosResponseConversion(){
        EarningThirdScreen.posconversion=0;
        return true;
    }

    public boolean resetNegResponseConversion(){
        EarningThirdScreen.negconversion=0;
        return true;
    }

    public boolean setPosResponseRates(){
        EarningThirdScreen.posrates =1;
        return true;
    }
    public boolean setNegResponseRates(){
        EarningThirdScreen.negrates= 1;
        return true;
    }
    public boolean resetPosResponseRates(){
        EarningThirdScreen.posrates =0;
        return true;
    }
    public boolean resetNegResponseRates(){
        EarningThirdScreen.negrates =0;
        return true;
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
                        addContacts.setVisibility(View.INVISIBLE);
                        addedContact.setVisibility(View.VISIBLE);
                        addedContact.setText(contactName);
                        remContacts.setVisibility(View.VISIBLE);

                    }
                }
                break;
        }
    }


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
