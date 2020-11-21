package com.example.Bachat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ImportToScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_to_screen);
        ImageView earnimport =(ImageView) findViewById(R.id.imageViewEarning);
        ImageView expenseimport =(ImageView) findViewById(R.id.imageViewExpense);
        TextView textViewImportEarning= findViewById(R.id.textViewImportEarning);
        TextView textViewImportExpense= findViewById(R.id.textViewImportExpense);

        earnimport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ImportToScreen.this,EarningImportScreenInternalLogic.class);
                startActivity(intent);
            }
        });

        textViewImportEarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ImportToScreen.this,EarningImportScreenInternalLogic.class);
                startActivity(intent);
            }
        });
        expenseimport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImportToScreen.this, ImportScreenInternalLogic.class);
                startActivity(intent);
            }
        });
        textViewImportExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImportToScreen.this, ImportScreenInternalLogic.class);
                startActivity(intent);
            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImportToScreen.this, MainActivity.class);
                intent.putExtra("settings_screen", "settings screen");
                startActivity(intent);
            }
        });
        FloatingActionButton fab_home= (FloatingActionButton)  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImportToScreen.this, MainActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ImportToScreen.this, MainActivity.class);
        intent.putExtra("settings_screen","Open Settings Screen");
        startActivity(intent);
    }
}
