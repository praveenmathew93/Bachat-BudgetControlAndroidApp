package com.example.Bachat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HelpMenuActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<HelpMenuItems> versionsList;
    RecyclerView.Adapter adapter;
    TextView supportText;
    TextView supportText1;
    TextView supportText2;
    TextView supportText3;
    TextView supportText4;
    TextView supportText5;
    TextView supportText6;
    TextView supportText7;
    TextView supportText8;
    TextView supportText9;
    TextView supportTextnew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyler_exapandle);


        supportText = findViewById(R.id.Name);
        supportText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMenuActivity.this, Slider1.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });




        supportText1 = findViewById(R.id.Security1);
        supportText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMenuActivity.this, Slider2.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });


        supportTextnew = findViewById(R.id.export_text);
        supportTextnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMenuActivity.this, Slider3.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });


        supportText2 = findViewById(R.id.importData_text);
        supportText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMenuActivity.this, Slider4.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });



        supportText3 = findViewById(R.id.help_text);
        supportText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMenuActivity.this, Slider5.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });
        supportText4 = findViewById(R.id.support_text);
        supportText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMenuActivity.this, Slider6.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });
        supportText5 = findViewById(R.id.support_text1);
        supportText5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMenuActivity.this, Slider7.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });

        supportText6 = findViewById(R.id.support_text2);
        supportText6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMenuActivity.this, Slider8.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });

        supportText7 = findViewById(R.id.support_text3);
        supportText7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMenuActivity.this, Slider9.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });

        supportText8 = findViewById(R.id.support_text4);
        supportText8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMenuActivity.this, Slider10.class);
                startActivity(intent);
                //Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton fab_home= (FloatingActionButton)  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpMenuActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


        //recyclerView = findViewById(R.id.recyclerView);

        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpMenuActivity.this, MainActivity.class);
                intent.putExtra("settings_screen", "settings screen");
                startActivity(intent);
            }
        });



       /* initData();
        setRecylerView();
    }

    private void setRecylerView() {

        adapter = new HelpMenuAdapter(versionsList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData() {

        versionsList = new ArrayList<>();
        versionsList.add(new HelpMenuItems("more>>","What is Bachat App?","Bachat is a mobile App that helps you live the lifestyle you want staying within your budget; it helps you “spend better and live better”. You can now get hold of your finances and manage better by using Bachat. You can set up budgets for categories, create saving goals and take control of all your money matters." ));
        versionsList.add(new HelpMenuItems("more>>","How can i add an expense/income?","After creating a login. you will be hosted onto your home page, which includes a “Minus” button for adding expenses, a “Plus” button for adding income. Once you have clicked the minus or plus button, you can select different categories and then proceed to select pre-defined sub-categories related to the chosen category or add your own sub-category. Next, you are given an option to choose the date, enter the amount along with the payment mode.  " ));
        versionsList.add(new HelpMenuItems("more>>","Can i set a threshold? ","Yes you can set a threshold based on the categories, by clicking the budget button at the bottom of your home screen and after setting it, the app will tell you how much percentage of budget you have allocated to the particular category.  " ));
        versionsList.add(new HelpMenuItems("more>>","Is there a way to export transactions? ","Yes you can easily export your transactions by simply going into the more option and clicking on the export button." ));
        versionsList.add(new HelpMenuItems("more>>","How can i use graphs? ","The graphs will start appearing on your home screen after you add a transaction for the first time. More graphs are accessible by clicking the graphs button on your home screen." ));
        versionsList.add(new HelpMenuItems("more>>","How do i delete a transaction? ","You can delete a transaction by going into the transaction history and swiping left on the desired entry." ));
        versionsList.add(new HelpMenuItems("more>>","How do i repeat a transaction? ","You can repeat a transaction by going into the transaction history and swiping right on the desired entry." ));
        versionsList.add(new HelpMenuItems("more>>","Can i create multiple profiles? ","Yes, you can create multiple profiles based on your requirements." ));
        versionsList.add(new HelpMenuItems("more>>","What is the default currency? ","The default currency is set by you when you choose a currency for the first time." ));
        versionsList.add(new HelpMenuItems("more>>","What is the way to edit my transactions ?","You can change individual transactions by going into the transaction history" ));
        versionsList.add(new HelpMenuItems("more>>","Points to note. ","1.The dates will be entered like “5/5/2020” not “05/05/2020”.                                     2.The profile name cannot exceed 8 characters." ));





*/
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HelpMenuActivity.this, MainActivity.class);
        intent.putExtra("settings_screen", "settings screen");
        startActivity(intent);
    }
}