/*package com.example.Bachat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SixthScreen extends AppCompatActivity {
    DatabaseHelper myDb;
    private static final String TAG = "SixthScreen";
    public String fromFifth;
    public String category;
    public String subcategory;
    public String modeofpayment;
    public String amount;
    public String date;
    public String note;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        myDb= new DatabaseHelper(this);
        Log.d(TAG, "onCreate: created sixth screen");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sixth_screen);

        fromFifth = getIntent().getStringExtra("fromFifth");
        Log.d(TAG, "onCreate: from fifth"+ fromFifth);
        TextView finaltextView = (TextView) findViewById(R.id.textView5) ;
        finaltextView.setText(fromFifth);
        String result[] = fromFifth.split(",");
        category = result[0];
        subcategory = result[1];
        modeofpayment = result[2];
        amount = result[3];
        date = result[4];
        note = result[5];
        Button addButton = (Button) findViewById(R.id.btnAddDataToDb);
        AddData(addButton);

        Button sixthButton = (Button) findViewById(R.id.btnGoToShowAll);
        sixthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(SixthScreen.this, ShowAllScreen.class);
                //startActivity(intent);
            }
        });
    }
    public void AddData(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(category, subcategory, modeofpayment,amount,date, note);
                if (isInserted = true)
                    Toast.makeText(SixthScreen.this, "data inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SixthScreen.this,"not inserted",Toast.LENGTH_SHORT).show();

            }
        });
    }

}*/