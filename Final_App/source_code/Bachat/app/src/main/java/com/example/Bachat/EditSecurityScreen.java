package com.example.Bachat;

import android.content.Intent;
import android.database.Cursor;
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

public class EditSecurityScreen extends AppCompatActivity {
    Button updatepassword;
    Button updateqa;
    EditText newquestion;
    EditText newanswer;
    EditText newpassword;
    EditText newpasswordconfirm;
    String currentpassword;
    TextView question;
    TextView answer;
    TextView password;
    DatabaseHelper myDb;
    ImageButton back_button;
    Spinner questionSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_screen);
        myDb = new DatabaseHelper(this);

        newquestion = (EditText) findViewById(R.id.editTextNewQuestion);
        newanswer = (EditText) findViewById(R.id.editTextNewAnswer);
        newpassword = (EditText) findViewById(R.id.editTextNewPassword);
        newpasswordconfirm = (EditText) findViewById(R.id.editTextNewPasswordConfirm);
        question = (TextView) findViewById(R.id.textViewCurrentQuestion);
        answer = (TextView) findViewById(R.id.textViewCurrentAnswer);
        //password = (TextView) findViewById(R.id.textViewCurrentPassword);
        updateqa = (Button) findViewById(R.id.btnUpdateQA);
        updatepassword = (Button) findViewById(R.id.btnUpdatePassword);
        newquestion.setVisibility(View.INVISIBLE);

        //code for new question spinner
        questionSpinner = (Spinner) findViewById(R.id.spinnerNewQuestion);
        ArrayAdapter<CharSequence> questionAdapter = ArrayAdapter.createFromResource(this,R.array.userQuestions,android.R.layout.simple_list_item_1);
        //userQuestions is the list specified in strings.xml
        questionAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        questionSpinner.setAdapter(questionAdapter);
        questionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getSelectedItem().toString().equals("Write your own Question...")){
                    questionSpinner.setVisibility(View.INVISIBLE);
                    newquestion.setText("");
                    newquestion.setVisibility(View.VISIBLE);
                }else{
                    newquestion.setText(questionSpinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Cursor res = myDb.getAllDataUser();
        while(res.moveToNext()){
            question.setText(res.getString(5));
            answer.setText(res.getString(6));
            //password.setText(res.getString(1));
            currentpassword = res.getString(1);
        }


        updateqa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedq = newquestion.getText().toString();
                String updateda = newanswer.getText().toString();
                if (updatedq.isEmpty() && updateda.isEmpty()) {
                    Toast.makeText(EditSecurityScreen.this, "please mention both Q and A", Toast.LENGTH_SHORT).show();
                }
               else if (updatedq.isEmpty() && !updateda.isEmpty()) {
                    Toast.makeText(EditSecurityScreen.this, "please mention the question for the updated answer", Toast.LENGTH_SHORT).show();
                }
               else if (updateda.isEmpty() && !updatedq.isEmpty()) {
                    Toast.makeText(EditSecurityScreen.this, "please mention the answer for the updated question", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean dbtest = myDb.updateQAUser(updatedq, updateda);
                    if(dbtest) {
                        question.setText(updatedq);
                        answer.setText(updateda);
                    }
                }
            }
        });
        updatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedp = newpassword.getText().toString();
                String updatedpc = newpasswordconfirm.getText().toString();
                if (updatedp.isEmpty()) {
                    Toast.makeText(EditSecurityScreen.this, "please enter a new password", Toast.LENGTH_SHORT).show();
                }
                else if (updatedpc.isEmpty()) {
                    Toast.makeText(EditSecurityScreen.this, "please confirm your password", Toast.LENGTH_SHORT).show();
                }
                else if (!updatedpc.equals(updatedp)) {
                    Toast.makeText(EditSecurityScreen.this, "the passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else if (updatedp.isEmpty() || updatedpc.isEmpty() || !updatedpc.equals(updatedpc)) {

                }
                else if(updatedp.equals(currentpassword)){
                    Toast.makeText(EditSecurityScreen.this, "new password can not be same as old password", Toast.LENGTH_SHORT).show();
                }
                else {
                    Cursor res = myDb.getAllDataUser();
                    StringBuffer sb = new StringBuffer();
                    while (res.moveToNext()) {
                        sb.append(res.getString(0));
                    }
                    String username = sb.toString();
                    Log.d("checking user name", "passed username in method is : " + username);
                    myDb.updatePasswordUser(updatedp, username);
                    Intent intent = new Intent(EditSecurityScreen.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditSecurityScreen.this, MainActivity.class);
                intent.putExtra("settings_screen", "setting screen");
                startActivity(intent);
            }
        });

        FloatingActionButton fab_home= (FloatingActionButton)  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditSecurityScreen.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
    @Override
    public void onBackPressed()
    {

    }

}

