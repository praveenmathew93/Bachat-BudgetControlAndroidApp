package com.example.Bachat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class EarningSecondScreen extends AppCompatActivity {

    private static final String TAG = "SecondScreen";
    public static BreakIterator data;
    RecyclerView mRecyclerView;
    CategoryListAdapterRecycler mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Category> earningcategoryList;
    TextView expense_income;

    DatabaseHelper myDb;
    RecyclerView recomRecyclerView;
    RecommendationAdapter recomAdapter;
    RecyclerView.LayoutManager recomLayoutManager;
    ArrayList<Category_Recommendation> recomCategoryList;

    TextView recomText;
    TextView category;
    RelativeLayout relLayout_1;
    RelativeLayout relLayout_2;

    //end of currency definition

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Income);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        myDb = myDb = new DatabaseHelper(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.theGrid);
        recomRecyclerView = (RecyclerView) findViewById(R.id.theGrid_2);
        category = findViewById(R.id.category);
        recomText = findViewById(R.id.recomText);
        expense_income=findViewById(R.id.expense_income);
        expense_income.setText("New Income");
        relLayout_1 = findViewById(R.id.relLayout_1);
        relLayout_2 = findViewById(R.id.relLayout_2);

        relLayout_1.setBackgroundColor(getResources().getColor(R.color.income_background,getTheme()));
        relLayout_2.setBackgroundColor(getResources().getColor(R.color.income_background,getTheme()));


        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EarningSecondScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, "profile object : " + HomeFragment.current_profile);
        Log.d(TAG, "onCreate:Started Earning SecondScreen");
        Log.d("Time zone", "=" + Calendar.getInstance().getTime());
        /*getSupportActionBar().setTitle("Income");
        getSupportActionBar().setSubtitle("Choose Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/



       // GridView mListView = (GridView) findViewById(R.id.theGrid);


        //Recommendation
        Cursor topThree = myDb.getTopThreeEarningsOfMonth();
        recomCategoryList = new ArrayList<>();
        if (topThree.moveToFirst() && topThree.getString(0) != null) {
            do {
                String temp_category = topThree.getString(0);
                int iconURL = getIcon(topThree.getString(0));
                String temp_transactions="";
                if(topThree.getString(1).equals("1")) temp_transactions=topThree.getString(1) + " Transaction";
                else temp_transactions=topThree.getString(1) + " Transactions";
                Category_Recommendation category_recommendation = new Category_Recommendation(temp_category,iconURL,temp_transactions);
                recomCategoryList.add(category_recommendation);
            } while (topThree.moveToNext());


        }
        recomLayoutManager = new GridLayoutManager(this,3);
        recomAdapter = new RecommendationAdapter(recomCategoryList,getApplicationContext());
        recomRecyclerView.setLayoutManager(recomLayoutManager);
        recomRecyclerView.setAdapter(recomAdapter);
        if(recomCategoryList.size()==0){
            recomText.setText("");
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) recomText.getLayoutParams();
            lp.setMargins(0,0,0,0);
            category.setText("");
            RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) category.getLayoutParams();
            lp2.setMargins(0,0,0,0);
            RelativeLayout.LayoutParams marginLayoutParams = new RelativeLayout.LayoutParams(mRecyclerView.getLayoutParams());
            marginLayoutParams.setMargins(20, 90, 20, 50);
            mRecyclerView.setLayoutParams(marginLayoutParams);

            }
        recomAdapter.setOnItemClickListener(new RecommendationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                recomCategoryList.get(position);
                Intent intent = new Intent(EarningSecondScreen.this, EarningThirdScreen.class);
                String toadd =  recomCategoryList.get(position).getCategory();
                intent.putExtra("fromEarningSecond",toadd);
                startActivity(intent);
            }

        });



        Category earningcategory1 = new Category("Salary", R.drawable.salary_icon);
        Category earningcategory2 = new Category("Business", R.drawable.business_icon);
        Category earningcategory3 = new Category("Gifts", R.drawable.gifts_icon_2);
        Category earningcategory4 = new Category("Bonus", R.drawable.bonus_icon);
        Category earningcategory5 = new Category("Interest",  R.drawable.interest_icon);
        Category earningcategory6 = new Category("Cashback", R.drawable.cashback_icon);;
        Category earningcategory7= new Category("Refund", R.drawable.refund_icon);
        Category earningcategory8 = new Category("Donation", R.drawable.donations_income_icon);
        Category earningcategory9 = new Category("Insurance", R.drawable.insurance_payout_icon);
        Category earningcategory10 = new Category("Credit Points", R.drawable.credit_points_icon);
        Category earningcategory11 = new Category("Extra Income", R.drawable.extra_income_icon);
        Category earningcategory12 = new Category("Others", R.drawable.other_income_icon);



        //Add the Person objects to an ArrayList

        earningcategoryList = new ArrayList<>();
        earningcategoryList.add(earningcategory1);
        earningcategoryList.add(earningcategory2);
        earningcategoryList.add(earningcategory3);
        earningcategoryList.add(earningcategory4);
        earningcategoryList.add(earningcategory5);
        earningcategoryList.add(earningcategory6);
        earningcategoryList.add(earningcategory7);
        earningcategoryList.add(earningcategory8);
        earningcategoryList.add(earningcategory9);
        earningcategoryList.add(earningcategory10);
        earningcategoryList.add(earningcategory11);
        earningcategoryList.add(earningcategory12);

        //recycler for grid
        mLayoutManager = new GridLayoutManager(this,3);
        mAdapter = new CategoryListAdapterRecycler(earningcategoryList,getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



        /*CategoryListAdapter adapter = new CategoryListAdapter(this, R.layout.adapter_view_layout, earningcategoryList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "onItemClick:you clicked on a list item  " + peopleList.get(position).getName());
                //toast.makeText(SecondScreen.this, "you clicked on " + peopleList.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EarningSecondScreen.this, EarningThirdScreen.class);
                String toadd =  earningcategoryList.get(position).getName();
                intent.putExtra("fromEarningSecond",toadd);
                startActivity(intent);
            }
        });*/

        mAdapter.setOnItemClickListener(new CategoryListAdapterRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                earningcategoryList.get(position);
                Intent intent = new Intent(EarningSecondScreen.this, EarningThirdScreen.class);
                String toadd =  earningcategoryList.get(position).getName();
                intent.putExtra("fromEarningSecond",toadd);
                startActivity(intent);
            }

        });

        FloatingActionButton fab_home= (FloatingActionButton)  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EarningSecondScreen.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private int getIcon(String Category) {
        switch (Category) {
            case "Salary":
                return R.drawable.salary_icon;
            case "Business":
                return R.drawable.business_icon;
            case "Bonus":
                return R.drawable.bonus_icon;
            case "Cashback":
                return R.drawable.cashback_icon;
            case "Refund":
                return R.drawable.refund_icon;
            case "Donation":
                return R.drawable.donations_income_icon;
            case "Insurance":
                return R.drawable.insurance_payout_icon;
            case "Credit Points":
                return R.drawable.credit_points_icon;
            case "Extra Income":
                return R.drawable.extra_income_icon;
            case "Others":
                return R.drawable.other_income_icon;
            case "Gifts":
                return R.drawable.gifts_icon_2;
            case "Interest":
                return R.drawable.interest_icon;
            default:
                return R.drawable.other;

        }
    }


}





