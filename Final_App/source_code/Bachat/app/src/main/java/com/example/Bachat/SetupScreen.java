package com.example.Bachat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.scwang.wave.MultiWaveHeader;
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


public class SetupScreen extends AppCompatActivity {

    private static int SETUP_SCREEN = 999999999;
    private static final String TAG = "SetupScreen";
    public String store;
    DatabaseHelper myDb;
    Spinner questionSpinner;

    //defined for currency
    public static BreakIterator data;
    List<String> keysList;
    List<String> RateList;
    ArrayAdapter<CharSequence> currencyAdapter;
    ArrayAdapter<CharSequence> questionAdapter;
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
    MultiWaveHeader waveHeader;
    //end of currency definition

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started user screen");
        myDb= new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.setup_screen);

        Log.d(TAG, "onCreate:  put layout of setupscreen");
        final TextInputLayout username =  findViewById(R.id.editTextEditName);
        final TextInputLayout usermail =  findViewById(R.id.editTextEditMail);
        final TextInputLayout userpassword =  findViewById(R.id.editTextEditPassword);
        final TextInputLayout userpasswordconfirm =  findViewById(R.id.editTextEditPasswordConfirm);
        final EditText userquestion = findViewById(R.id.editTextEditQuestion);
        final TextInputLayout useranswer = findViewById(R.id.editTextEditAnswer);
        FloatingActionButton proceed =  findViewById(R.id.btnConfirm);
        waveHeader =findViewById(R.id.MultiWave);

       /* waveHeader.setStartColor(R.color.startgradientprofile);
        waveHeader.setCloseColor(R.color.endgradientprofile);
        waveHeader.setColorAlpha(.5f);
        waveHeader.setWaveHeight(50);
        waveHeader.setGradientAngle(360);
        waveHeader.setProgress(.8f);
        waveHeader.setVelocity(1f);
        waveHeader.setScaleY(-1f);
        waveHeader.setWaves("PairWave");
        waveHeader.start();
        waveHeader.stop();
        waveHeader.isRunning();*/


        //The following code is for the text spinner to enable the selection of the currency
        currencySpinner = (Spinner) findViewById(R.id.spnCurrency);
        currencyAdapter = ArrayAdapter.createFromResource(this,R.array.Currencies,R.layout.spinner_layout_light);
        //Currencies is the list specified in strings.xml
        currencyAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout_light);

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
                updatedCurr = parent.getItemAtPosition(position).toString();
                Boolean checkNetwork = haveNetworkConnection();
                if(!checkNetwork){
                    /*Toast.makeText(SetupScreen.this, "Please connect your device to the Internet inorder to set your Default Currency ", Toast.LENGTH_SHORT).show();
                    String compareCurrency = HomeFragment.default_currency;
                    int currencyPosition = currencyAdapter.getPosition(compareCurrency);
                    currencySpinner.setSelection(currencyPosition);*/
                    if (updatedCurr.substring(0, 3).equals("INR")){
                        Log.d(TAG, "onItemSelected: inside networkcheck: "+ updatedCurr.substring(0,3));
                        myDb.insertConversionRates("EUR","0.012");
                        myDb.insertConversionRates("USD","0.013");
                        myDb.insertConversionRates("GBP","0.011");
                    }else if(updatedCurr.substring(0, 3).equals("EUR")){
                        Log.d(TAG, "onItemSelected: inside networkcheck: "+ updatedCurr.substring(0,3));
                        myDb.insertConversionRates("INR","84.01");
                        myDb.insertConversionRates("USD","1.12");
                        myDb.insertConversionRates("GBP","0.90");
                    }else if(updatedCurr.substring(0, 3).equals("USD")){
                        Log.d(TAG, "onItemSelected: inside networkcheck: "+ updatedCurr.substring(0,3));
                        myDb.insertConversionRates("INR","74.68");
                        myDb.insertConversionRates("EUR","0.89");
                        myDb.insertConversionRates("GBP","0.80");
                    }else if(updatedCurr.substring(0, 3).equals("GBP")){
                        Log.d(TAG, "onItemSelected: inside networkcheck: "+ updatedCurr.substring(0,3));
                        myDb.insertConversionRates("INR","93.23");
                        myDb.insertConversionRates("USD","1.25");
                        myDb.insertConversionRates("EUR","1.11");
                    }
                    Log.d(TAG, "onItemSelected: inside networkcheck outside ifs: "+ updatedCurr.substring(0,3));
                    myDb.setDefaultCurrency(updatedCurr);
                    HomeFragment.default_currency = updatedCurr;
                    return;

                }
                String curr = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemSelected: currency value is :" + curr);

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


                    toCurr = "EUR";
                    Log.d(TAG, "onClick: toCurrency  " + toCurr);

                    try {

                        Boolean check = haveNetworkConnection();
                        if (check) {
                            Log.d(" connection test output is : " +check,"; internet working" );
                            resultrates = loadConvTypes();
                            while (resultrates == null){

                            }

                            if (SetupScreen.posrates== 1){
                                Log.d("on result check for rates", "everything is ok, we fetched live rates");
                            }
                            else if (SetupScreen.negrates == 1){
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

        userquestion.setVisibility(View.INVISIBLE);
        questionSpinner = (Spinner) findViewById(R.id.spnQuestion);
        questionAdapter = ArrayAdapter.createFromResource(this,R.array.userQuestions,R.layout.spinner_layout_light);
        //userQuestions is the list specified in strings.xml
        questionAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout_light);
        questionSpinner.setAdapter(questionAdapter);
        questionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem().toString().equals("Write your own Question...")){
                    questionSpinner.setVisibility(View.INVISIBLE);
                    userquestion.setText("");
                    userquestion.setVisibility(View.VISIBLE);
                }else{
                    userquestion.setText(questionSpinner.getSelectedItem().toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });










        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "";//username.getEditText().getText().toString();
                String password = ""; //userpassword.getText().toString();
                String confirmpassword = "";//userpasswordconfirm.getText().toString();
                String question = "";//userquestion.getText().toString();
                String answer = "";//useranswer.getText().toString();
                String mail = "";//usermail.getText().toString();

                if (username.getEditText().getText().toString().trim().isEmpty())
                {
                    //Toast.makeText(SetupScreen.this, "please enter your name", Toast.LENGTH_SHORT).show();
                    username.setError("Username cannot be empty");
                }
                else if(username.getEditText().getText().toString().length() > 8) username.setError("Username too long");
                else name = username.getEditText().getText().toString();


                if (usermail.getEditText().getText().toString().trim().isEmpty())
                {
                    //Toast.makeText(SetupScreen.this, "please enter your e-mail", Toast.LENGTH_SHORT).show();
                    usermail.setError("E-Mail cannot be empty");
                }else mail = usermail.getEditText().getText().toString();

                if (userpassword.getEditText().getText().toString().isEmpty())
                {
                    //Toast.makeText(SetupScreen.this, "please enter a password", Toast.LENGTH_SHORT).show();
                    userpassword.setError("Password cannot be empty");
                }else password = userpassword.getEditText().getText().toString();
                if (userpasswordconfirm.getEditText().getText().toString().trim().isEmpty())
                {
                    //Toast.makeText(SetupScreen.this, "please confirm your password", Toast.LENGTH_SHORT).show();
                    userpasswordconfirm.setError("Password cannot be empty");
                }else confirmpassword = userpasswordconfirm.getEditText().getText().toString();
                if (userquestion.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(SetupScreen.this, "please select a question of your choice", Toast.LENGTH_SHORT).show();
                    //Question_2.setError("Question cannot be empty");
                    //Question.setError("Question cannot be empty");
                }else question = userquestion.getText().toString();
                if (useranswer.getEditText().getText().toString().trim().isEmpty())
                {
                    //Toast.makeText(SetupScreen.this, "please answer the selected question", Toast.LENGTH_SHORT).show();
                    useranswer.setError("Please answer the security question");
                }else answer = useranswer.getEditText().getText().toString();

                if(!password.equals(confirmpassword)) {

                    //Toast.makeText(SetupScreen.this, "password and confirm password do not match", Toast.LENGTH_SHORT).show();

                    userpasswordconfirm.setError("Passwords did not match");

                }
                if(name.toLowerCase().isEmpty() || mail.toLowerCase().isEmpty() || password.isEmpty() || confirmpassword.isEmpty() || question.isEmpty() || answer.isEmpty() || !password.equals(confirmpassword)) {


                }
                else if(!mail.contains("@") || !mail.contains(".")){
                    //Toast.makeText(SetupScreen.this, "Please enter a valid e-mail address", Toast.LENGTH_SHORT).show();
                    usermail.setError("Please enter a valid e-mail address");
                }
                else{
                    String mailtolower=mail.toLowerCase();

                boolean inserted = myDb.insertDataUser(name,password, mailtolower, question, answer);
                Log.d(TAG, "onClick: " + inserted);
                Intent intent = new Intent(SetupScreen.this,MainActivity.class);
                startActivity(intent);
                finish();}

            }
        });
        new Handler().postDelayed(new Runnable () {
            @Override
            public void run() {
                Intent intent = new Intent(SetupScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SETUP_SCREEN);


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

                SetupScreen.this.runOnUiThread(new Runnable() {
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
                SetupScreen.this.runOnUiThread(new Runnable() {
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
        SetupScreen.posconversion =1;
        return true;
    }
    public boolean setNegResponseConversion(){
        SetupScreen.negconversion =1;
        return true;
    }
    public boolean resetPosResponseConversion(){
        SetupScreen.posconversion=0;
        return true;
    }

    public boolean resetNegResponseConversion(){
        SetupScreen.negconversion=0;
        return true;
    }

    public boolean setPosResponseRates(){
        SetupScreen.posrates =1;
        return true;
    }
    public boolean setNegResponseRates(){
        SetupScreen.negrates= 1;
        return true;
    }
    public boolean resetPosResponseRates(){
        SetupScreen.posrates =0;
        return true;
    }
    public boolean resetNegResponseRates(){
        SetupScreen.negrates =0;
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
