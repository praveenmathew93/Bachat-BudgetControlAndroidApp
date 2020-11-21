package com.example.Bachat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {
    TextView questiontext ;
    EditText editanswer;
    Button check;
    DatabaseHelper myDb;
    Cursor res;
    String question;
    String answer;
    String storedanswer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("onCreate: ", "ForgotPassword screen ");
        setContentView(R.layout.forgot_screen);
        myDb = new DatabaseHelper(this);
        questiontext =(TextView)findViewById(R.id.textViewBannerQuestion) ;
        editanswer=(EditText)findViewById(R.id.editTextForgotAnswer);
        res = myDb.getAllDataUser();

        while(res.moveToNext())
        {
            question= res.getString(5);
            storedanswer = res.getString(6);
            Log.d("databse data: ", "" + question + " "+ storedanswer);
        }
        questiontext.setText(question + " ?");

        check = (Button) findViewById(R.id.btnCheckAnswer);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer= editanswer.getText().toString();
                if(answer.isEmpty()){
                    Toast.makeText(ForgotPassword.this,"please enter an answer to continue",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(answer.toLowerCase().equals(storedanswer.toLowerCase())){
                        Intent intent = new Intent(ForgotPassword.this,ResetPassword.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ForgotPassword.this,"Dang! the credentials do not match",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
