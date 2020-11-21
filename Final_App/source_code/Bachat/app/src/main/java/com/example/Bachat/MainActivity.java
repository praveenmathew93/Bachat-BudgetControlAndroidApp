package com.example.Bachat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity //implements NavigationView.OnNavigationItemSelectedListener//,HomeFragment.onFragmentBtnSelected
{
    private final static String TAG = "MainActivity";
    public final static String PREFS = "PrefsFile";


    private SharedPreferences settings = null;
    private SharedPreferences.Editor editor = null;
    //DrawerLayout drawerLayout;
    //ActionBarDrawerToggle actionBarDrawerToggle;
    //Toolbar toolbar;
    //NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    //TextView toolbar_title_set;
    public String firstDate;
    public String secondDate;
    public String category;
    public String modeOfPayment;
    public String a;
    public String showAll;
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: MainActivity Started");
        myDb = new DatabaseHelper(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_nav_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);


        Log.d("Time zone", "=" + Calendar.getInstance().getTime());

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
//show sign up activity
            startActivity(new Intent(MainActivity.this, SplashscreenActivity.class));
//startActivity(new Intent(MainActivity.this, SetupScreen.class));
        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        String view_threshold_fragment = getIntent().getStringExtra("open_set_threshold");
        Log.d(TAG, "onCreate: " + view_threshold_fragment);
        String view_Expense_fragment = getIntent().getStringExtra("open_view_all_expenses");
        Log.d(TAG, "onCreate: " + view_Expense_fragment);
        String view_Income_fragment = getIntent().getStringExtra("open_view_all_income");
        Log.d(TAG, "onCreate: " + view_Expense_fragment);
        String view_Import_Export_fragment = getIntent().getStringExtra("Import_Export_Screen");
        String view_Settings_fragment = getIntent().getStringExtra("settings_screen");
        String view_filter_fragment = getIntent().getStringExtra("FilterScreen");
        String view_graph_fragment = getIntent().getStringExtra("Graph");

        if (view_threshold_fragment != null) {
            view_threshold_fragment = "Open Threshold View";
        } else if (view_Expense_fragment != null) {
            view_Expense_fragment = "Open Expense View";
            Log.d(TAG, "onCreate:" + view_Expense_fragment);
        } else if (view_Income_fragment != null) {
            view_Income_fragment = "Open Income View";
            Log.d(TAG, "onCreate:" + view_Income_fragment);
        } else if (view_Import_Export_fragment != null) {
            view_Import_Export_fragment = "Open Import Export Screen";
        } else if (view_Settings_fragment != null) {
            view_Settings_fragment = "Open Settings Fragment";
            bottomNavigationView.setSelectedItemId(R.id.bmenuOther);
        } else if (view_filter_fragment != null) {
            view_filter_fragment = "ShowAllExpenseFragment";
        } else if (view_graph_fragment != null) {
            view_graph_fragment = "Graph";
        } else {
            view_threshold_fragment = "Open Home Fragment";
            view_Expense_fragment = "Open Home Fragment";
            view_Import_Export_fragment = "Open Home Fragment";
            view_filter_fragment = "Open Home Fragment";
            view_Income_fragment="Open Home Fragment";
            view_graph_fragment="Open Home Fragment";
        }
        //onSaveInstanceState(savedInstanceState,view_fragment);
        //Declaring all the components of Navigation Menu
        /*toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_title_set = findViewById(R.id.toolbar_title);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        //For the toggle animation as the user open navigation menu
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();*/


        if (savedInstanceState == null) {
            if (view_threshold_fragment == "Open Threshold View") {
                //toolbar_title_set.setText("Threshold");
                //toolbar_title_set_2.setText("Threshold");
                bottomNavigationView.setSelectedItemId(R.id.bmenuThreshold);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ThresholdFragment()).commit();
                view_threshold_fragment = null;
            } else if (view_Expense_fragment == "Open Expense View") {
                //toolbar_title_set.setText("Expense History");
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ShowAllExpenseFragment()).commit();
                view_Expense_fragment=null;
            } else if (view_Income_fragment == "Open Income View") {
                //toolbar_title_set.setText("Income History");
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ShowAllEarningFragment()).commit();
                view_Income_fragment = null;
            } else if (view_Import_Export_fragment == "Open Import Export Screen") {
                //toolbar_title_set.setText("Import/Export");
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ImportExportFragment()).commit();
                view_Import_Export_fragment = null;
            } else if (view_Settings_fragment == "Open Settings Fragment") {
                //toolbar_title_set.setText("Settings");
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new SettingsFragment()).commit();
                view_Settings_fragment = null;
            } else if (view_filter_fragment=="ShowAllExpenseFragment") {
                //toolbar_title_set.setText("Test");
                Bundle bundle = new Bundle();
                bundle.putString("Filter_options", a);
                ShowAllExpenseFragment showAllExpenseFragment = new ShowAllExpenseFragment();
                showAllExpenseFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, showAllExpenseFragment).commit();
                a = null;
                view_filter_fragment = null;
            }else if (view_graph_fragment == "Graph") {
                //toolbar_title_set.setText("Settings");
                bottomNavigationView.setSelectedItemId(R.id.bmenuGraphs);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new GraphFragment()).commit();

                view_graph_fragment = null;
            }
            else {
                //toolbar_title_set.setText("Bachat");

                String Profile=getIntent().getStringExtra("AddedProfile");
                HomeFragment homeFragment = new HomeFragment();
                if(Profile != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("Profile",Profile);
                    homeFragment.setArguments(bundle);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, homeFragment).commit();
            }
        }

        //code for checking last login time for the app
        // Save time of run:
        settings = getSharedPreferences(PREFS, MODE_PRIVATE);
        editor = settings.edit();

        // First time running app?
        if (!settings.contains("lastRun"))
            enableNotification(null);
        else
            recordRunTime();

        Log.v(TAG, "Starting CheckRecentRun service...");
        startService(new Intent(this,  CheckRecentRun.class));

    }

    @Override
    public void onBackPressed()
    {
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.bmenuHome:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.bmenuGraphs:
                            selectedFragment = new GraphFragment();
                            break;
                        case R.id.bmenuThreshold:
                            selectedFragment = new ThresholdFragment();
                            break;
                        case R.id.bmenuTransaction:
                            /*selectedFragment = new TransactionHistory();
                            break;*/
                            startActivity(new Intent(getApplicationContext(), TransactionHistory.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.bmenuOther:
                            selectedFragment = new SettingsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,
                            selectedFragment).commit();
                    return true;
                }
            };


    public void recordRunTime() {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.commit();
    }

    public void enableNotification(View v) {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.putBoolean("enabled", true);
        editor.commit();
        Log.v(TAG, "Notifications enabled");
    }

    public void disableNotification(View v) {
        editor.putBoolean("enabled", false);
        editor.commit();
        Log.v(TAG, "Notifications disabled");
    }
}


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSettings:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,
                        new Settings()).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/


/*public Bundle onSaveInstanceState(Bundle savedInstanceState,String view_fragment) {
super.onSaveInstanceState(savedInstanceState);
// Save UI state changes to the savedInstanceState.
// This bundle will be passed to onCreate if the process is
// killed and restarted.
if(view_fragment == null){
return savedInstanceState;
}
else{
savedInstanceState.putString("MyString", view_fragment);
return savedInstanceState;
}
}

/*@Override
public void onButtonSelected() {
Toast.makeText(this, "Fragment Button is working", Toast.LENGTH_SHORT).show();
}

/*
// DatabaseHelper myDb = null;
private static final String TAG = "MainActivity";

@Override
protected void onCreate(Bundle savedInstanceState) {
//Test Commit
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
Log.d(TAG, "onCreate: MainActivity Started" );
// myDb= new DatabaseHelper(this);

TextView showallearning = (TextView) findViewById(R.id.textViewShowAllEarning);
showallearning.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Intent intent = new Intent(MainActivity.this,EarningShowAllScreen.class);
startActivity(intent);
}
});
TextView showallexpense = (TextView) findViewById(R.id.textViewShowAllExpense);
showallexpense.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Intent intent = new Intent(MainActivity.this,ShowAllScreen.class);
startActivity(intent);
}
});

Button threshold = (Button) findViewById(R.id.btnGoToSetThreshold);
threshold.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Intent intent = new Intent(MainActivity.this, ThresholdViewScreen.class);
startActivity(intent);
}
});

/* Button viewthreshold = (Button) findViewById(R.id.btnGoToViewThreshold);
viewthreshold.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Intent intent = new Intent(MainActivity.this, ThresholdCategoryScreen.class);
startActivity(intent);
}
});*/
//addding here again because one was already existing
/*ImageView plusImage = (ImageView) findViewById(R.id.plusImage);
int imageResource = getResources().getIdentifier("@drawable/plus", null, this.getPackageName());
plusImage.setImageResource(imageResource);
plusImage.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Log.d(TAG, "onClick: you clicked on the plus image");
//Toast.makeText(MainActivity.this, "image clicked", Toast.LENGTH_SHORT).show();
Intent intent = new Intent(MainActivity.this, EarningSecondScreen.class);
startActivity(intent);
}
});
ImageView minusImage = (ImageView) findViewById(R.id.minusImage);
int imageResourcea = getResources().getIdentifier("@drawable/minus", null, this.getPackageName());
minusImage.setImageResource(imageResourcea);
minusImage.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Log.d(TAG, "onClick: you clicked on the minus image");
//Toast.makeText(MainActivity.this, "image clicked", Toast.LENGTH_SHORT).show();
Intent intent = new Intent(MainActivity.this, SecondScreen.class);
startActivity(intent);
}
});
ImageView convertImage = (ImageView) findViewById(R.id.convertImage);
int imageResourceb = getResources().getIdentifier("@drawable/convert", null, this.getPackageName());
convertImage.setImageResource(imageResourceb);
convertImage.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Log.d(TAG, "onClick: you clicked on the convert image");
//Toast.makeText(MainActivity.this, "image clicked", Toast.LENGTH_SHORT).show();
Intent intent = new Intent(MainActivity.this, ImportToScreen.class);
startActivity(intent);
}
});
}*/

