package com.example.Bachat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EditUserScreen extends AppCompatActivity{
    String updatedname;
    String updatedmail;
    String originalname;
    String originalmail;
    EditText editamount;
    DatabaseHelper myDb;

    //defined for currency
    public static BreakIterator data;
    List<String> keysList;
    List<String> RateList;
    ArrayAdapter<CharSequence> currencyAdapter;
    Spinner currencySpinner;
    String updatedCurr;
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
    //end of currency definition
    private static final String TAG = "EditUser";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_screen);
        final EditText editname = (EditText) findViewById(R.id.editTextEditName);
        final EditText editmail = (EditText) findViewById(R.id.editTextEditMail);
        Button confirm = (Button) findViewById(R.id.btnConfirm);


        myDb = new DatabaseHelper(this);

        Cursor res = myDb.getAllDataUser();
        StringBuffer sb= new StringBuffer();
        StringBuffer sb2= new StringBuffer();
        while (res.moveToNext())
        {
            sb.append(res.getString(0));
            sb2.append(res.getString(2));
        }

        //code for testing
        /*Button setprofile = (Button) findViewById(R.id.buttonSetProfile);
        Button addprofile = (Button) findViewById(R.id.buttonAddProfile);
        Button setprofiledefault = (Button) findViewById(R.id.ButtonSetProfileDefault);

        addprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.insertProfile("Office");
            }
        });

        setprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Before calling set profile, the current profile is " + HomeFragment.current_profile);
                myDb.setCurrentProfiles("Office");
                //Log.d(TAG, "onClick: the changed current profile is " + HomeFragment.current_profile);
            }
        });
        setprofiledefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Before calling set profile, the current profile is " + HomeFragment.current_profile);
                myDb.setCurrentProfiles("Default");
                //Log.d(TAG, "onClick: the changed current profile is " + HomeFragment.current_profile);
            }
        });*/

        //The following code is for the text spinner to enable the selection of the currency
        currencySpinner = (Spinner) findViewById(R.id.editCurrency);
        currencyAdapter = ArrayAdapter.createFromResource(this,R.array.Currencies,android.R.layout.simple_list_item_1);
        //Currencies is the list specified in strings.xml
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
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
                Boolean checkNetwork = haveNetworkConnection();
                if(!checkNetwork){
                    Toast.makeText(EditUserScreen.this, "Please connect your device to Internet inorder to change your Default Currency ", Toast.LENGTH_SHORT).show();
                    String compareCurrency = HomeFragment.default_currency;
                    int currencyPosition = currencyAdapter.getPosition(compareCurrency);
                    currencySpinner.setSelection(currencyPosition);
                    return;
                }
                String curr = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: currency value is :" + curr);
                updatedCurr = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(),mode+" chosen",Toast.LENGTH_LONG).show();
                currency = curr;
                currSelected = curr;
                Double passedConverted = convertedAmout;
                    //Conversion
                    if(!currSelected.equals(HomeFragment.default_currency)){
                        // this is the currency in which the user is currently entering amount

                        baseCurr= currency.substring(0,3);
                        Log.d(TAG, "onClick: baseCurr :" + baseCurr);
                        //this is the currency which the users selects as default currency for his use (assuming euro here, will be set by the user in the app , all his transactions will be in this currency by default)


                        toCurr = HomeFragment.default_currency.substring(0,3);
                        Log.d(TAG, "onClick: toCurrency  " + toCurr);

                        try {

                            Boolean check = haveNetworkConnection();
                            if (check) {
                                Log.d(" connection test output is : " +check,"; internet working" );
                                resultrates = loadConvTypes();
                                while (resultrates == null){

                                }

                                if (EditUserScreen.posrates== 1){
                                    Log.d("on result check for rates", "everything is ok, we fetched live rates");
                                }
                                else if (EditUserScreen.negrates == 1){
                                    Log.d("on result check for rates", "we are facing some issues getting results at the moment");
                                }
                            }
                            else{
                                Log.d( " connection test output is : " +check,  "; There seems to be an some connection issues,  please check that you are connected to the internet"  );
                                //some code for fetching old rates from the internal database for conversion.
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                //end of code for currency conversion
                myDb.setDefaultCurrency(currency);
                HomeFragment.default_currency = currency;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        //end testing here


        originalname = sb.toString();
        originalmail = sb2.toString();
        editname.setText(originalname);
        editmail.setText(originalmail);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedname = editname.getText().toString();
                updatedmail = editmail.getText().toString().toLowerCase();

                 if(updatedname.isEmpty()){
                    Toast.makeText(EditUserScreen.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                 }
                 else if(updatedmail.isEmpty()){
                    Toast.makeText(EditUserScreen.this, "Please enter an e-mail address", Toast.LENGTH_SHORT).show();
                 }
                 else if(!updatedmail.contains("@") || !updatedmail.contains(".")){
                     Toast.makeText(EditUserScreen.this, "Please enter a valid e-mail address", Toast.LENGTH_SHORT).show();
                 }
                 else{
                     updatedname = editname.getText().toString();
                     updatedmail = editmail.getText().toString().toLowerCase();

                     boolean res = myDb.updateDataUser(originalname,updatedname,updatedmail);
                     if(res){
                         Log.d(TAG, " userdata updated ");
                     }
                     else{
                         Log.d(TAG, "userdata update failed");
                     }
                     Intent intent = new Intent(EditUserScreen.this, MainActivity.class);
                     intent.putExtra("settings_screen", "settings screen");
                     startActivity(intent);
                 }
            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditUserScreen.this, MainActivity.class);
                intent.putExtra("settings_screen", "settings screen");
                startActivity(intent);
            }
        });

        FloatingActionButton fab_home= (FloatingActionButton)  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditUserScreen.this, MainActivity.class);
                startActivity(intent);

            }
        });


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

                EditUserScreen.this.runOnUiThread(new Runnable() {
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

   /* public String convertCurrency(final String toCurr, final double baseValue) throws IOException {
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
                EditUserScreen.this.runOnUiThread(new Runnable() {
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
    }*/

    public boolean setPosResponseConversion(){
        EditUserScreen.posconversion =1;
        return true;
    }
    public boolean setNegResponseConversion(){
        EditUserScreen.negconversion =1;
        return true;
    }
    public boolean resetPosResponseConversion(){
        EditUserScreen.posconversion=0;
        return true;
    }

    public boolean resetNegResponseConversion(){
        EditUserScreen.negconversion=0;
        return true;
    }

    public boolean setPosResponseRates(){
        EditUserScreen.posrates =1;
        return true;
    }
    public boolean setNegResponseRates(){
        EditUserScreen.negrates= 1;
        return true;
    }
    public boolean resetPosResponseRates(){
        EditUserScreen.posrates =0;
        return true;
    }
    public boolean resetNegResponseRates(){
        EditUserScreen.negrates =0;
        return true;
    }
    //end of code for currency conversion

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
}
