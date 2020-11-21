package com.example.Bachat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SupportScreen extends AppCompatActivity {
    private static final String TAG = "SupportScreen";
    private TextView supportEmail;
    private TextView socialMedia;
    /*private TextView recipientTo;
    private TextView textTo;
    private TextView textSubject;
    private TextView textMessage;
    private EditText editSubject;
    private EditText editMessage;
    private Button buttonSend;*/
    private String Brand_value;
    private String Model_value;
    private String Manufacturer_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Manufacturer_value = Build.MANUFACTURER;
        Brand_value = Build.BRAND;
        Model_value = Build.MODEL;
        setContentView(R.layout.support_screen);
        supportEmail = findViewById(R.id.textViewSupportEmail);
        socialMedia = findViewById(R.id.textViewFacebookID);
        socialMedia.setMovementMethod(LinkMovementMethod.getInstance());
        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupportScreen.this, MainActivity.class);
                intent.putExtra("settings_screen", "setting screen");
                startActivity(intent);
            }
        });

        FloatingActionButton fab_home= (FloatingActionButton)  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SupportScreen.this, MainActivity.class);
                startActivity(intent);

            }
        });

        /*textTo = findViewById(R.id.textTo);
        recipientTo = findViewById(R.id.toEmail);
        textSubject = findViewById(R.id.textSubject);
        editSubject = findViewById(R.id.edit_text_subject);
        textMessage = findViewById(R.id.textMessage);
        editMessage = findViewById(R.id.edit_text_message);
        buttonSend = findViewById(R.id.button_send);*/

        /*Visibility Settings - OFF
        textTo.setVisibility(View.INVISIBLE);
        recipientTo.setVisibility(View.INVISIBLE);
        textSubject.setVisibility(View.INVISIBLE);
        editSubject.setVisibility(View.INVISIBLE);
        textMessage.setVisibility(View.INVISIBLE);
        editMessage.setVisibility(View.INVISIBLE);
        buttonSend.setVisibility(View.INVISIBLE);*/

        supportEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Visibility Settings - ON
                /*textTo.setVisibility(View.VISIBLE);
                recipientTo.setVisibility(View.VISIBLE);
                textSubject.setVisibility(View.VISIBLE);
                editSubject.setVisibility(View.VISIBLE);
                textMessage.setVisibility(View.VISIBLE);
                editMessage.setVisibility(View.VISIBLE);
                buttonSend.setVisibility(View.VISIBLE);*/
                sendMail();
            }
        });

        /*buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });*/
    }
    private void sendMail() {
        String recipient = supportEmail.getText().toString();
        String[] recipientList = recipient.split(",");
        Log.d(TAG, "sendMail: The recipient is : "+ recipient);
        String subject = "Bachat - Support for "+ Manufacturer_value + " - " + Brand_value + " - " + Model_value;
        String message = "Issue: ";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL  , recipientList);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(intent, "Email via..."));
    }
}