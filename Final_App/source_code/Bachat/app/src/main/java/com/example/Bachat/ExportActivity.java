package com.example.Bachat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ExportActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    ArrayList<ListItem> expenseItems;
    ArrayList<ListItem> incomeItems;
    private static final String TAG = "ExportActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_from_screen);
        myDb = new DatabaseHelper(this);
        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExportActivity.this, MainActivity.class);
                intent.putExtra("settings_screen", "settings screen");
                startActivity(intent);
            }
        });

        FloatingActionButton fab_home= (FloatingActionButton)  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExportActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


    }

    public void exportExpense(View view){
        //defining the data
        StringBuilder expenseData = new StringBuilder();
        StringBuilder data = new StringBuilder();
        expenseData.append("CATEGORY, SUBCATEGORY, MOP, AMOUNT, DATE(YYYY/MM/DD), NOTE, CURRENCY");
        Cursor res = myDb.getAllData();
        Log.d(TAG, "res for exportData" + res);
        ArrayList<String> listItem = new ArrayList<>();
        if (res.getCount() == 0) {
            showMessage("Oops", "Looks like you have no data to export");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            String item = new String();
            expenseData.append("\n" + res.getString(1) + "," + res.getString(2) + ", " + res.getString(3) + "," + res.getString(4) + "," + res.getString(5) + "," + res.getString(6) + "," + res.getString(7).substring(0,3));
        }

        try {
            //writing data to csv
            FileOutputStream out = openFileOutput("Expenses.csv", Context.MODE_PRIVATE);
            Log.d(TAG, "exportExpense: inside try\n" + expenseData.toString());
            out.write((expenseData.toString()).getBytes(Charset.forName("UTF-8")));
            out.close();

            //exporting
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(), "Expenses.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.Bachat.fileprovider", fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Expense Details from Bachat");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send Email"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void exportEarning(View view){
        //defining the data
        StringBuilder earningData = new StringBuilder();
        earningData.append("CATEGORY, MODE, AMOUNT, DATE, NOTE, CURRENCY");
        Cursor res = myDb.getAllDataEarning();
        ArrayList<String> listItem = new ArrayList<>();
        if (res.getCount() == 0) {
            showMessage("Oops", "Looks like you have no data to export");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            earningData.append("\n" + res.getString(1) + "," + res.getString(2) + ", " + res.getString(3) + "," + res.getString(4) + "," + res.getString(5) + "," + res.getString(6).substring(0,3));
        }

        try {
            FileOutputStream out = openFileOutput("Earnings.csv", Context.MODE_PRIVATE);
            Log.d(TAG, "exportEarning: "+ earningData.toString());
            out.write((earningData.toString()).getBytes(Charset.forName("UTF-8")));
            out.close();

            //exporting
            Context context = getApplicationContext();
            File fileLocation = new File(getFilesDir(), "Earnings.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.Bachat.fileprovider", fileLocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Income Details from Bachat");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send Email"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}