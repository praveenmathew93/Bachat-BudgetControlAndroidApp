package com.example.Bachat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SecondScreen extends AppCompatActivity {

    private static final String TAG = "SecondScreen";
    RecyclerView mRecyclerView;
    CategoryListAdapterRecycler mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Category> categoryList;

    RecyclerView recomRecyclerView;
    RecommendationAdapter recomAdapter;
    RecyclerView.LayoutManager recomLayoutManager;
    ArrayList<Category_Recommendation> recomCategoryList;
    TextView recomText;
    TextView category;

    DatabaseHelper myDB;
    @Override

    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        setTheme(R.style.Expense);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        Log.d(TAG, "onCreate:Started SecondScreen");
        mRecyclerView = (RecyclerView) findViewById(R.id.theGrid);
        recomRecyclerView = (RecyclerView) findViewById(R.id.theGrid_2);
        category = findViewById(R.id.category);
        recomText = findViewById(R.id.recomText);
        myDB = new DatabaseHelper(this);

        /*getSupportActionBar().setTitle("Expense");
        getSupportActionBar().setSubtitle("Choose Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        FloatingActionButton fab_home= (FloatingActionButton)  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondScreen.this, MainActivity.class);
                startActivity(intent);

            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //Recommendations
        Cursor topThree = myDB.getTopThreeExpenseOfMonth();
        recomCategoryList = new ArrayList<>();
        if (topThree.moveToFirst() && topThree.getString(0) != null) {
            do {
                String temp_category = topThree.getString(0);
                int iconURL = getIcon(topThree.getString(0));
                String temp_transactions="";
                if(topThree.getString(1).equals("1")) temp_transactions=topThree.getString(1) + " Transaction";
                else temp_transactions=topThree.getString(1) + " Transactions";
                Log.d(TAG, "Recom Recycler="+temp_category+temp_transactions+iconURL );
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
                Intent intent = new Intent(SecondScreen.this, ThirdScreen.class);
                String toadd =  recomCategoryList.get(position).getCategory();
                Log.d(TAG, "onItemClick: "+ toadd);
                intent.putExtra("fromSecond",toadd);
                startActivity(intent);
            }

        });






        //GridView mListView = (GridView) findViewById(R.id.theGrid);

        //Create the Person objects
        /*
        Person john = new Person("John","12-20-1998","Male",
                getResources().getIdentifier("@drawable/cartman_cop", null,this.getPackageName()));
                */

        Category category1 = new Category("Health", R.drawable.healthcare);
        Category category2 = new Category("Donations", R.drawable.donations);
        Category category3 = new Category("Bills", R.drawable.billsicon);
        Category category4 = new Category("Shopping", R.drawable.shoppingicon);
        Category category5 = new Category("Dining Out", R.drawable.dinningout);
        Category category6 = new Category("Entertainment", R.drawable.entertaimenticon);;
        Category category7= new Category("Groceries", R.drawable.groceries_icon);
        Category category8 = new Category("Pet Care", R.drawable.petsicon);
        Category category9 = new Category("Transportation", R.drawable.transportation);
        Category category10 = new Category("Loans", R.drawable.loansicon);
        Category category11 = new Category("Personal Care", R.drawable.personalcareicon);
        Category category12= new Category("Miscellaneous", R.drawable.miscellaneousicon);




        categoryList = new ArrayList<>();
        categoryList.add(category1);
        categoryList.add(category3);
        categoryList.add(category2);
        categoryList.add(category4);
        categoryList.add(category5);
        categoryList.add(category6);
        categoryList.add(category7);
        categoryList.add(category8);
        categoryList.add(category9);
        categoryList.add(category10);
        categoryList.add(category11);
        categoryList.add(category12);

        //recycler for grid
        mLayoutManager = new GridLayoutManager(this,3);
        mAdapter = new CategoryListAdapterRecycler(categoryList,getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


      /*  CategoryListAdapter adapter = new CategoryListAdapter(this, R.layout.adapter_view_layout, categoryList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "onItemClick:you clicked on a list item  " + peopleList.get(position).getName());
                //toast.makeText(SecondScreen.this, "you clicked on " + peopleList.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SecondScreen.this, ThirdScreen.class);
                String toadd =  categoryList.get(position).getName();
                Log.d(TAG, "onItemClick: "+ toadd);
                intent.putExtra("fromSecond",toadd);
                startActivity(intent);
            }
        });*/

        mAdapter.setOnItemClickListener(new CategoryListAdapterRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                categoryList.get(position);
                Intent intent = new Intent(SecondScreen.this, ThirdScreen.class);
                String toadd =  categoryList.get(position).getName();
                Log.d(TAG, "onItemClick: "+ toadd);
                intent.putExtra("fromSecond",toadd);
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
            case "Health":
                return R.drawable.healthcare;
            case "Donations":
                return R.drawable.donations;
            case "Bills":
                return R.drawable.billsicon;
            case "Shopping":
                return R.drawable.shoppingicon;
            case "Dining Out":
                return R.drawable.dinningout;
            case "Entertainment":
                return R.drawable.entertaimenticon;
            case "Groceries":
                return R.drawable.groceries_icon;
            case "Pet Care":
                return R.drawable.petsicon;
            case "Transportation":
                return R.drawable.transportation;
            case "Loans":
                return R.drawable.loansicon;
            case "Personal Care":
                return R.drawable.personalcareicon;
            case "Miscellaneous":
                return R.drawable.miscellaneousicon;
            default:
                return R.drawable.other;

        }
    }

}





