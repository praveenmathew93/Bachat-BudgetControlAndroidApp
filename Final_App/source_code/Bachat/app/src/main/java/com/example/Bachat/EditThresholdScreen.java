package com.example.Bachat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;


public class EditThresholdScreen extends AppCompatActivity {
    //String fromThresholdCategory;
    String toeditfromthresholdviewscreen;
    int flag = 0;
    EditText Threshold;
    String value;
    String[] splitstring;
    public Button removeButton;
    DatabaseHelper myDb;
    String month;
    String profile;
    float monthbudget;
    float categorySum =0;
    float remaining;
    TextView percent_value;
    ProgressBar progressBar;
    String[] categoryvalues = new String[12];


    private static final String TAG = "ThresholdScreen";
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);
        toeditfromthresholdviewscreen = getIntent().getStringExtra("toeditfromthresholdviewscreen");;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threshold_screen);
        TextView showcat = (TextView)findViewById(R.id.textViewThresholdCategory);
        Threshold = findViewById(R.id.editTextThreshold);
        TextView budget = (TextView)findViewById(R.id.textViewBudgetEditThreshold);
        TextView remainingbudget = (TextView)findViewById(R.id.textViewRemainingEditThrehsold);
        progressBar =  findViewById(R.id.progressbar_1);
        percent_value = findViewById(R.id.percent_value);
        setEditTextCursorColor(Threshold,getResources().getColor(R.color.Black,getTheme()));


        splitstring= toeditfromthresholdviewscreen.split(",");
        showcat.setText(splitstring[0]);
        if(splitstring[1].equals("Not Set")) {
            Threshold.setText("0");
        }
        else{
            Threshold.setText(splitstring[1]);
        }
        month = splitstring[2];
        profile = splitstring[3];
        Log.d(TAG, "value for month is " + (month));

        Cursor res = myDb.getEntryThreshold(month,profile);

        if (res.moveToFirst()) {
            if (!(res.getString(1).equalsIgnoreCase("Not Set"))){
                monthbudget = Float.parseFloat(res.getString(1));
               if(splitstring[0].equalsIgnoreCase("Budget")){
                   budget.setText("currently set budget for the month is" + monthbudget + "you can edit the monthy budget by entering a new value below" );
               }
               else{
                   budget.setText("Budget for the month is " + monthbudget );
               }
            }
            else{
                monthbudget = -1;
                if(splitstring[0].equalsIgnoreCase("Budget")){
                    budget.setText("currently set budget for the month is not set. You can set the monthy budget by entering a new value below" );
                }
                else{
                    budget.setText("Budget for the month is not set as yet");
                }

            }

        }

        for(int i=0;i<=11;i++){
            int j=i+1;
            res.moveToPosition(j);
            categoryvalues[i]=res.getString(1);
            if(!categoryvalues[i].equalsIgnoreCase("Not Set")){
                categorySum += Float.parseFloat(categoryvalues[i]);
            }
        }

        /*StringBuffer buffer = new StringBuffer();

        while (res.moveToNext()) {
            buffer.append(res.getString(1) +  "\n");
        }

        String[] bufferdata  = buffer.toString().split("\n");
        Log.d(TAG, "bufferdata length is" + bufferdata.length);
        Log.d(TAG, "bufferdata content is" + buffer.toString());

        for(int i=0; i< bufferdata.length; i++){
            categoryvalues[i]=bufferdata[i+1];
            if(!categoryvalues[i].equalsIgnoreCase("Not Set")){
                categorySum += Float.parseFloat(categoryvalues[i]);
            }
        }*/


        if(splitstring[0].equalsIgnoreCase("budget"))
        {
            remaining = 999999999f;
            remainingbudget.setText("remaining budget not applicable here");
        }
        else{

            if (monthbudget >=0){
                remaining = monthbudget - categorySum + Float.parseFloat(Threshold.getText().toString());
                remainingbudget.setText("remaining budget for this month is " + String.valueOf(remaining));
            }
            else
            {
                remaining = 999999999f;
                remainingbudget.setText("remaining N/A, budget not set");
            }

        }


        Log.d(TAG, "remining amount is : " + remaining);

        int progress = (int) ((categorySum/monthbudget)*100);
        Log.d("progress for progressbar", String.valueOf(progress));

        progressBar.setProgress(progress);
        percent_value.setText(String.valueOf(progress) + "%");
        // some thing like percent_value.setText("used "+ progress + "% of" + monthbudget)//
        budget.setVisibility(View.INVISIBLE);
        remainingbudget.setVisibility(View.INVISIBLE);






        final Button setThreshold = (Button) findViewById(R.id.btnSetThreshold);
        setThreshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Float.parseFloat(Threshold.getText().toString()) >= 99999999f)
                {
                    Toast.makeText(EditThresholdScreen.this, "Please enter amount which is smaller than 99999999", Toast.LENGTH_SHORT).show();
                }
                if(Float.parseFloat(Threshold.getText().toString()) < 0f)
                {
                    Toast.makeText(EditThresholdScreen.this, "Please enter amount which is greater than 0", Toast.LENGTH_SHORT).show();
                }
                else if(Threshold.getText().toString().isEmpty())
                {
                    Toast.makeText(EditThresholdScreen.this, "Please enter amount ", Toast.LENGTH_SHORT).show();
                }
                else if(remaining-Float.parseFloat(Threshold.getText().toString())<0)
                {
                    Toast.makeText(EditThresholdScreen.this, "amount exceeds overall budget", Toast.LENGTH_SHORT).show();
                }
                else if(Float.parseFloat(Threshold.getText().toString()) < 99999999f &&  Float.parseFloat(Threshold.getText().toString()) >= 0f && !(remaining-Float.parseFloat(Threshold.getText().toString())<0))
                {
                    setThreshold();
                }

            }
        });

        removeButton =(Button)findViewById(R.id.btnRemoveThreshold);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeThreshold();
            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditThresholdScreen.this, MainActivity.class);
                intent.putExtra("open_set_threshold", "Open Threshold View");
                startActivity(intent);
            }
        });

        ImageButton fab_home= findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditThresholdScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    void setThreshold(){
        value=Threshold.getText().toString();
        if(value.length()<1) {
            Log.d(TAG, "value.length < 1");
            String threshold = "Not Set";
            //boolean isUpdate = myDb.updateThreshold(fromThresholdCategory,threshold);
            boolean isUpdate = myDb.updateThreshold(splitstring[0], threshold, month, profile);

            Log.d(TAG, "update amount is : " + threshold);
            if (isUpdate == true)
                flag = 1;
        }
        else{
            boolean isUpdate;
            boolean isUpdate2;
            if (monthbudget>-1){
                Log.d(TAG, " budget > -1, setThreshold: values are " + splitstring[0] + value + month+ profile);
                isUpdate = myDb.updateThreshold(splitstring[0],value, month, profile);
                isUpdate2 = true;
            }
            else{
                Log.d(TAG, "setThreshold: values are " + splitstring[0] + value + month+ profile);
                isUpdate = myDb.updateThreshold(splitstring[0],value, month, profile);
                isUpdate2 = myDb.updateThreshold("Budget",value, month, profile);
            }


            Log.d(TAG, "update amount is : "+value);
            if(isUpdate == true && isUpdate2 == true)
                Log.d(TAG, "setThreshold: update ok");
                flag=1;
        }
       if(flag == 1){
           Log.d(TAG, "flag set to 1");

           Intent intent = new Intent(EditThresholdScreen.this, MainActivity.class);
           intent.putExtra("open_set_threshold", "Open Threshold View");
           startActivity(intent);
       }
    }

    void removeThreshold(){
        Log.d(TAG, "removeThreshold: ");
            String threshold = "Not Set";
        boolean isUpdate;
        boolean isUpdate2;
        boolean isUpdate3;
        boolean isUpdate4;
        boolean isUpdate5;
        boolean isUpdate6;
        boolean isUpdate7;
        boolean isUpdate8;
        boolean isUpdate9;
        boolean isUpdate10;
        boolean isUpdate11;
        boolean isUpdate12;
        boolean isUpdate13;


            if(splitstring[0].equalsIgnoreCase("Budget")){
                //boolean isUpdate = myDb.updateThreshold(fromThresholdCategory,threshold);
                isUpdate= myDb.updateThreshold(splitstring[0],threshold, month, profile);
                isUpdate2 = myDb.updateThreshold("Health",threshold, month, profile);
                isUpdate3 = myDb.updateThreshold("Donations",threshold, month, profile);
                isUpdate4 = myDb.updateThreshold("Bills",threshold, month, profile);
                isUpdate5 = myDb.updateThreshold("Shopping",threshold, month, profile);
                isUpdate6 = myDb.updateThreshold("Dining out",threshold, month, profile);
                isUpdate7 = myDb.updateThreshold("Entertainment",threshold, month, profile);
                isUpdate8 = myDb.updateThreshold("Groceries",threshold, month, profile);
                isUpdate9 = myDb.updateThreshold("Pet",threshold, month, profile);
                isUpdate10 = myDb.updateThreshold("Transport",threshold, month, profile);
                isUpdate11 = myDb.updateThreshold("Loans",threshold, month, profile);
                isUpdate12 = myDb.updateThreshold("Personal Care",threshold, month, profile);
                isUpdate13 = myDb.updateThreshold("Miscellaneous",threshold, month, profile);

            }
            else{
                isUpdate = myDb.updateThreshold(splitstring[0],threshold, month, profile);
                isUpdate2 = true;
                isUpdate3 = true;
                isUpdate4 = true;
                isUpdate5 = true;
                isUpdate6 = true;
                isUpdate7 = true;
                isUpdate8 = true;
                isUpdate9 = true;
                isUpdate10 = true;
                isUpdate11 = true;
                isUpdate12 = true;
                isUpdate13 = true;

            }

            Log.d(TAG, "removed amount is : "+threshold);
            if(isUpdate == true && isUpdate2 == true && isUpdate3 == true && isUpdate4 == true && isUpdate5 == true && isUpdate6 == true && isUpdate7 == true && isUpdate8 == true && isUpdate9 == true && isUpdate10 == true && isUpdate11 == true && isUpdate12 == true && isUpdate13 == true )
                flag=1;
        if(flag == 1){
            Log.d(TAG, "flag set to 1");

            Intent intent = new Intent(EditThresholdScreen.this, MainActivity.class);
            intent.putExtra("open_set_threshold", "Open Threshold View");
            startActivity(intent);
        }
    }


    public static void setEditTextCursorColor(EditText editText, int color) {
        try {
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(editText);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(editText);

            final Field fSelectHandleLeft = editor.getClass().getDeclaredField("mSelectHandleLeft");
            final Field fSelectHandleRight = editor.getClass().getDeclaredField("mSelectHandleRight");
            final Field fSelectHandleCenter = editor.getClass().getDeclaredField("mSelectHandleCenter");
            fSelectHandleLeft.setAccessible(true);
            fSelectHandleRight.setAccessible(true);
            fSelectHandleCenter.setAccessible(true);



            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(editText.getContext(), drawableResId);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);


            // Set the drawables
            if(Build.VERSION.SDK_INT >= 28){//set differently in Android P (API 28)
                field = editor.getClass().getDeclaredField("mDrawableForCursor");
                field.setAccessible(true);
                field.set(editor, drawable);
            }else {
                Drawable[] drawables = {drawable, drawable};
                field = editor.getClass().getDeclaredField("mCursorDrawable");
                field.setAccessible(true);
                field.set(editor, drawables);
            }

            //optionally set the "selection handle" color too
            //setEditTextHandleColor(editText, color);
        } catch (Exception ignored) {}

    }

}
