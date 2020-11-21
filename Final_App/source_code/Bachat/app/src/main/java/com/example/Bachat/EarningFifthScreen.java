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

public class EarningFifthScreen extends AppCompatActivity {
    DatabaseHelper myDb;
    private static final String TAG = "EarningFifthScreen";
    public String fromEarningFourth;
    public String emode;
    public String eamount;
    public String edate;
    public String enote;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        myDb= new DatabaseHelper(this);
        Log.d(TAG, "onCreate: created earning fifth screen");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sixth_screen);

        fromEarningFourth = getIntent().getStringExtra("fromEarningFourth");
        TextView finaltextView = (TextView) findViewById(R.id.textView5) ;
        finaltextView.setText(fromEarningFourth);
        String result[] = fromEarningFourth.split(",");
        emode = result[0];
        eamount = result[1];
        edate = result[2];
        enote = result[3];
        Button addButton = (Button) findViewById(R.id.btnAddDataToDb);
        AddDataEarning(addButton);

        Button sixthButton = (Button) findViewById(R.id.btnGoToShowAll);
        sixthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarningFifthScreen.this, EarningShowAllScreen.class);
                startActivity(intent);
            }
        });
    }
    public void AddDataEarning(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertDataEarning(emode, eamount, edate, enote);
                if (isInserted = true)
                    Toast.makeText(EarningFifthScreen.this, "data inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EarningFifthScreen.this,"not inserted",Toast.LENGTH_SHORT).show();

            }
        });
    }

}
*/
