package com.example.Bachat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResetPassword extends AppCompatActivity {
    EditText resetpassword ;
    EditText resetpasswordconfirm;
    Button btnresetpassword;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        myDb = new DatabaseHelper(this);
        resetpassword = (EditText) findViewById(R.id.editTextResetPassword);
        resetpasswordconfirm = (EditText) findViewById(R.id.editTextResetPasswordConfirm);
        btnresetpassword = (Button) findViewById(R.id.btnResetForget);
        btnresetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = resetpassword.getText().toString();
                String confirmpassword = resetpasswordconfirm.getText().toString();
                if (password.isEmpty())
                {
                    Toast.makeText(ResetPassword.this, "please enter a new password", Toast.LENGTH_SHORT).show();
                }
                if (confirmpassword.isEmpty())
                {
                    Toast.makeText(ResetPassword.this, "please confirm your password", Toast.LENGTH_SHORT).show();
                }
                if (!confirmpassword.equals(password))
                {
                    Toast.makeText(ResetPassword.this, "the passwords do not match", Toast.LENGTH_SHORT).show();
                }
                if(password.isEmpty() || confirmpassword.isEmpty() || !confirmpassword.equals(password)){

                }
                else{
                    Cursor res = myDb.getAllDataUser();
                    StringBuffer sb= new StringBuffer();
                    while (res.moveToNext())
                    {
                        sb.append(res.getString(0));
                    }
                    String username = sb.toString();
                    Log.d("checking user name", "passed username in method is : " + username );
                    myDb.updatePasswordUser(password, username);
                    Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
