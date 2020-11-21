/*package com.example.Bachat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ThresholdViewScreen extends AppCompatActivity {
    DatabaseHelper myDb;
    private static final String TAG = "`ThresholdViewScreen`";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        myDb= new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threshold_show_all_screen);
        Log.d(TAG, "onCreate: Threshold View Screen");
        ListView listshowthreshold = (ListView) findViewById(R.id.listViewThreshold);

        Cursor res = myDb.getAllDataThreshold();
        if (res.getCount() == 0) {
            //message
            showMessage("error", "no data in table");
            return;}

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append(res.getString(0) + "," + res.getString(1) + "\n");

        }

        Log.d(TAG, " "+ buffer.toString());

        /*Button homebutton = (Button) findViewById(R.id.btnGoToMain);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThresholdViewScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });*/

        /*here String[] bufferdata  = buffer.toString().split("\n");
        final ArrayList<ThresholdListItem> ThresholdListItems = new ArrayList<>();
        Log.d(TAG, ""+ bufferdata.length);
        for(int i=0; i< bufferdata.length; i++){
            String[] listindex  = bufferdata[i].split(",");
            ThresholdListItem item = new ThresholdListItem(listindex[0],listindex[1]);
            ThresholdListItems.add(item);
        }
        ThresholdViewAllAdapter adapter = new ThresholdViewAllAdapter(this, R.layout.threshold_view_all_layout, ThresholdListItems);
        listshowthreshold.setAdapter(adapter);

        listshowthreshold.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "onItemClick:you clicked on a list item  " + peopleList.get(position).getName());
                //toast.makeText(SecondScreen.this, "you clicked on " + peopleList.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ThresholdViewScreen.this, ThresholdScreen.class);

                String toeditcategory =  ThresholdListItems.get(position).getCategory();
                String toeditamount =  ThresholdListItems.get(position).getAmount();
                String toedit = toeditcategory+","+toeditamount;
                intent.putExtra("toeditfromthresholdviewscreen", toedit);
                startActivity(intent);
            }
        });


        
    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}*/
