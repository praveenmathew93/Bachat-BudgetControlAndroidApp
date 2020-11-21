/*package com.example.Bachat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EarningFourthScreen extends AppCompatActivity {
    private static final String TAG = "FifthScreen";
    public String fromEarningThird;
    DatePicker picker;
    DatabaseHelper myDb;
    public String category;
    public String subcategory;
    public String modeofpayment;
    public String amount;
    public String addedDate;
    public String addedNote;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: created fifth screen" );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth_screen);
        picker = (DatePicker) findViewById(R.id.editText3);
        //final EditText editnote = (EditText)findViewById(R.id.editTextNote);
        myDb = new DatabaseHelper(this);

        Button fifthButton = (Button) findViewById(R.id.btnGoToSixth);
        fifthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(EarningFourthScreen.this, EarningFifthScreen.class);
                TextView theTextView = (TextView) findViewById(R.id.textView4);
                // theTextView.setText("Learing Navigation");
                // String stringFromTextView = theTextView.getText().toString();
                // System.out.println(stringFromTextView);
                fromEarningThird = getIntent().getStringExtra("fromEarningThird");
                Log.d(TAG, "onClick: fromEarningThird = "+fromEarningThird);
                EditText editnote = (EditText)findViewById(R.id.editTextNote);
                String notecheck = editnote.getText().toString();
                String note;
                if(notecheck.length() == 0){
                    note = "Not Set";
                }
                else
                    note= notecheck;

                String date = picker.getYear()+"/"+ (picker.getMonth() + 1)+"/"+picker.getDayOfMonth();
                String toadd = fromEarningThird + "," + date + "," + note;

                String result[] = toadd.split(",");
                Log.d(TAG, "onClick: "+result);
                category = result[0];
                amount = result[1];
                addedDate = result[2];
                addedNote = result[3];
        
                boolean isInserted = myDb.insertDataEarning(category, amount, addedDate, addedNote);
                if (isInserted = true)
                    Toast.makeText(EarningFourthScreen.this, "data inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EarningFourthScreen.this,"not inserted",Toast.LENGTH_SHORT).show();

                fromEarningThird = getIntent().getStringExtra("fromEarningThird");
               // EditText date = (EditText) findViewById(R.id.editText3);

                //intent.putExtra("fromEarningFourth",toadd);
                //startActivity(intent);
            }
        });

        Button sixthButton = (Button) findViewById(R.id.btnGoToShowAll);
        sixthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarningFourthScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

 */