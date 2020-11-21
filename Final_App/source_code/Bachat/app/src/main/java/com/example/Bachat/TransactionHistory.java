package com.example.Bachat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TransactionHistory extends AppCompatActivity {
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    FrameLayout frameLayout2;
    String expenseNav;
    Spinner transactionSpinner;
    private static final String TAG = "TransactionHistory";
    //Toolbar toolbar;

    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_history, container, false);


        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager);
        TabLayout tabLayout =  view.findViewById(R.id.tabLayout);
        viewPager2.setAdapter(new TransactionsPagerAdapter(getActivity()));*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_history);
        bottomNavigationView = findViewById(R.id.btm_nav_menu);
        frameLayout = findViewById(R.id.container_fragment);
        frameLayout2 = findViewById(R.id.container_fragment_transactions);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        expenseNav=getIntent().getStringExtra("open_view_all_expenses");




        transactionSpinner = findViewById(R.id.transaction_spinner);

        ArrayAdapter<CharSequence> transactionAdapter = ArrayAdapter.createFromResource(this,R.array.Transaction,R.layout.transaction_spinner_layout);

        //ModeofPayment is the list specified in strings.xml
        transactionAdapter.setDropDownViewResource(R.layout.transaction_spinner_drop_down);
        transactionSpinner.setAdapter(transactionAdapter);
        if(expenseNav!=null){
            String compareValue="Expenses";
            transactionSpinner.setSelection(getIndex(transactionSpinner, compareValue));
            expenseNav=null;

        }
        transactionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                frameLayout.setVisibility(View.INVISIBLE);
                String temp = parent.getItemAtPosition(position).toString();
                if(temp.equals("Expenses")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_transactions, new ShowAllExpenseFragment()).commit();
                }
                else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment_transactions, new ShowAllEarningFragment()).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




        /*viewPager2 = findViewById(R.id.viewPager);
        tabLayout =  findViewById(R.id.tabLayout);

        //toolbar = findViewById(R.id.toolbar);



        viewPager2.setAdapter(new TransactionsPagerAdapter(this));
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position) {
                    case 0: {
                        tab.setText("Income");
                        //tab.setIcon(R.drawable.plus);
                        break;
                    }
                    case 1: {
                        tab.setText("Expenses");
                        //tab.setIcon(R.drawable.minus);
                        break;
                    }
                }

            }
        });
        tabLayoutMediator.attach();
        viewPager2.setUserInputEnabled(false);*/




        if (savedInstanceState == null) {
            if(expenseNav!=null){
               /* TabLayout tabLayout1 = (TabLayout) findViewById(R.id.tabLayout);
                TabLayout.Tab tab = tabLayout.getTabAt(1);
                tab.select();*/
                bottomNavigationView.setSelectedItemId(R.id.bmenuTransaction);
            }
            else bottomNavigationView.setSelectedItemId(R.id.bmenuTransaction);
        }





        /*//getActivity().setTitle("Transaction History");

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        ViewPagerAdapter  adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.AddFragment(showallexpensefragment,"Show expense");
        adapter.AddFragment(showallincomefragment,"Show income");


        tabLayout.setupWithViewPager(viewPager);*/
        //return view;
    }

    @Override
    public void onBackPressed()
    {
    }

    public int getIndex(Spinner spinner, String myString){
        /*Log.d(TAG, "getIndex: CompareValue passing in"+myString);
        Log.d(TAG, "getIndex: Spinner value getting from position 2 "+spinner.getItemAtPosition(2));
        Log.d(TAG, "getIndex: Spinner value getting from position 2 converted to string "+spinner.getItemAtPosition(2).toString());
        Log.d(TAG, "getIndex: Comparison result "+spinner.getItemAtPosition(2).toString().equalsIgnoreCase(myString));*/
        for (int i=0;i<spinner.getCount();i++){
            String adjust = spinner.getItemAtPosition(i).toString();
            if (adjust.equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.bmenuHome:
                            //tabLayout.setVisibility(View.INVISIBLE);
                            //viewPager2.setVisibility(View.INVISIBLE);
                            frameLayout.setVisibility(View.VISIBLE);
                            frameLayout2.setVisibility(View.INVISIBLE);
                            //toolbar.setVisibility(View.INVISIBLE);
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.bmenuGraphs:
                            //tabLayout.setVisibility(View.INVISIBLE);
                            //viewPager2.setVisibility(View.INVISIBLE);
                            frameLayout.setVisibility(View.VISIBLE);
                            frameLayout2.setVisibility(View.INVISIBLE);
                            //toolbar.setVisibility(View.INVISIBLE);
                            selectedFragment = new GraphFragment();
                            break;
                        case R.id.bmenuThreshold:
                            //tabLayout.setVisibility(View.INVISIBLE);
                            //viewPager2.setVisibility(View.INVISIBLE);
                            frameLayout.setVisibility(View.VISIBLE);
                            frameLayout2.setVisibility(View.INVISIBLE);
                            //toolbar.setVisibility(View.INVISIBLE);
                            selectedFragment = new ThresholdFragment();
                            break;
                        case R.id.bmenuTransaction:
                            //tabLayout.setVisibility(View.VISIBLE);
                            //viewPager2.setVisibility(View.VISIBLE);
                            //toolbar.setVisibility(View.VISIBLE);
                            frameLayout.setVisibility(View.INVISIBLE);
                            frameLayout2.setVisibility(View.VISIBLE);
                            return true;
                        case R.id.bmenuOther:
                            //tabLayout.setVisibility(View.INVISIBLE);
                            //viewPager2.setVisibility(View.INVISIBLE);
                            frameLayout.setVisibility(View.VISIBLE);
                            frameLayout2.setVisibility(View.INVISIBLE);
                            //toolbar.setVisibility(View.INVISIBLE);
                            selectedFragment = new SettingsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,
                            selectedFragment).commit();
                    return true;
                }
            };

}
