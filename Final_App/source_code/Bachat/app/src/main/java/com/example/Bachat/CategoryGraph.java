package com.example.Bachat;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nostra13.universalimageloader.utils.L;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CategoryGraph extends AppCompatActivity {
    RelativeLayout relativeLayout;
    TextView catergory;
    TextView monthYear;
    private LineChart mChart;
    private static String TAG = "Extended Graphs";
    DatabaseHelper myDB;
    BarChart barChart;
    ImageView imageView;
    TextView noTransactionPrompt_1;
    TextView noTransactionPrompt_2;
    RecyclerView mRecyclerView;
    ArrayList<ListItem> ListItems;
    ShowAllAdapterRecycler mAdadpter;
    RecyclerView.LayoutManager mLayoutManager;
    TextView avgDailyExpense;
    TextView percentLastMonth;
    TextView percentLastMonth_2;
    TextView spentFromBudget;
    TextView numTransactions;
    private SimpleDateFormat dateFormat;
    private String date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.catergory_graph_fragment);

        String graphDetails= getIntent().getStringExtra("GraphDetails");
        String[] aGraphDetails = graphDetails.split(",");
        String[] period=aGraphDetails[1].split("/");


        relativeLayout=findViewById(R.id.category_graph_rel_layout);
        barChart = findViewById(R.id.barChart);
        imageView = findViewById(R.id.action_image);
        noTransactionPrompt_1 = findViewById(R.id.prompt_no_transactions);
        noTransactionPrompt_2 = findViewById(R.id.prompt_no_transactions_2);
        mRecyclerView = findViewById(R.id.recyclerlistFilter);
        mLayoutManager = new LinearLayoutManager(this);
        avgDailyExpense = findViewById(R.id.avgDailyExpense);
        percentLastMonth= findViewById(R.id.percentLastMonth);
        spentFromBudget = findViewById(R.id.spentFromBudget);
        numTransactions= findViewById(R.id.numTransactions);
        percentLastMonth_2 = findViewById(R.id.percentLastMonth_2);
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String currentDateandTime = sdf.format(new Date());
        String[] Date=currentDateandTime.split("/");


        relativeLayout.setBackgroundColor(getColor(mGetColor(aGraphDetails[0])));

        catergory = findViewById(R.id.title);
        catergory.setText(aGraphDetails[0]);



        String monthYearString = getMonth(period[1]) + " " + period[0];
        monthYear = findViewById(R.id.sub_title);
        monthYear.setText(monthYearString);
        int lastMonthDate = getnumdays(Integer.parseInt(period[1]) , Integer.parseInt(period[0]));

        myDB = new DatabaseHelper(this);
        Cursor budget = myDB.getBudgetForMonth(Integer.parseInt(period[1]),aGraphDetails[0],HomeFragment.current_profile);
        String stringBudget="";
        if (budget.moveToFirst() && budget.getString(0) != null) {
            do {
                stringBudget= budget.getString(0);
            } while (budget.moveToNext());
        }


        Cursor num_of_Transactions = myDB.getTransactionsOfCurrentMonth(aGraphDetails[1],aGraphDetails[2],aGraphDetails[0]);
        String a = null;
        Cursor getExpensesperday=myDB.getExpensesperday(aGraphDetails[0],aGraphDetails[1],aGraphDetails[2]);
        ArrayList<BarEntry> dateExpenseValues = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();

        Cursor res = myDB.getExpenseFiltered(aGraphDetails[0], "", aGraphDetails[1], aGraphDetails[2]);
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            String item = new String();
            buffer.append(res.getString(0) + "," + res.getString(1) + "," + res.getString(2) + ", " +
                    res.getString(3) + "," + res.getString(4) + "," + res.getString(5) + ", " +
                    res.getString(6) + "," + res.getString(7) + "," + res.getString(8) + ", " +
                    res.getString(9) + ", " + res.getString(10) +"\n");
        }
        String[] bufferdata = buffer.toString().split("\n");
        ListItems = new ArrayList<>();
        for (int i = 0; i < bufferdata.length; i++) {

            String[] listindex = bufferdata[i].split(",");
            Log.d(TAG, "listindex and item are: " + bufferdata + " ");
            Log.d(TAG, "viewData: buffer: " + buffer.toString());
            ListItem item = new ListItem(listindex[0], listindex[1], listindex[2], listindex[3], listindex[4], listindex[5],
                    listindex[6], listindex[7], listindex[8]); //call of constructor

            ListItems.add(item);
        }

        mAdadpter = new ShowAllAdapterRecycler(ListItems);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdadpter);

        if (num_of_Transactions.moveToFirst() && num_of_Transactions.getString(0) != null) {
            do {
                a = num_of_Transactions.getString(0);
            } while (num_of_Transactions.moveToNext());
        }

        int i=0;
        float max=0;
        float totalExpense=0;
        if (getExpensesperday.moveToFirst() && getExpensesperday.getString(0) != null) {
            do {
                float tempExpense = Float.valueOf(getExpensesperday.getString(0)); //yVals
                float tempDate =  Float.valueOf(((getExpensesperday.getString(1)).split("/"))[2]);
                totalExpense=totalExpense+tempExpense;
                dateExpenseValues.add(i,new BarEntry(tempDate, tempExpense));
                Log.d(TAG, "onCreate: " + tempExpense + tempDate + dateExpenseValues.get(i));
                if(max<tempExpense) max=tempExpense;
                i++;
            } while (getExpensesperday.moveToNext());
        }

        //Cursor res = myDB.getExpenseFiltered(aGraphDetails[0], "", aGraphDetails[1], aGraphDetails[2]);


        Cursor prev_month=myDB.getTransactionsOfPrevMonth_c(Integer.parseInt(period[1]),aGraphDetails[0]);
        float totalExpense_lastmonth = 0;
        if (prev_month.moveToFirst() && prev_month.getString(0) != null) {
            do {
                totalExpense_lastmonth = Float.valueOf(prev_month.getString(0));
            } while (prev_month.moveToNext());
        }


        //dateExpenseValues.add(i,new BarEntry(32, 0));


        ///((Math.round((totalExpense/Float.parseFloat(Date[1]))*100))/100.0);



        float avg = totalExpense/Float.parseFloat(Date[1])*100;
        avg= (float) (Math.round(avg)/100.0);

        float prev_avg= (totalExpense_lastmonth/lastMonthDate)*100;
        prev_avg = (float) (Math.round(prev_avg)/100.0);
        if(prev_avg>avg){
            percentLastMonth.setTextColor(getResources().getColor(R.color.Expense,getTheme()));
            avgDailyExpense.setTextColor(getResources().getColor(R.color.Income,getTheme()));
        }else if(prev_avg<avg){
            percentLastMonth.setTextColor(getResources().getColor(R.color.Income,getTheme()));
            avgDailyExpense.setTextColor(getResources().getColor(R.color.Expense,getTheme()));
        }
        else{
            percentLastMonth.setTextColor(getResources().getColor(R.color.primaryTextColorBetalightbackground,getTheme()));
            avgDailyExpense.setTextColor(getResources().getColor(R.color.primaryTextColorBetalightbackground,getTheme()));
        }
        percentLastMonth.setText(String.valueOf(prev_avg)+" " + HomeFragment.default_currency);
        avgDailyExpense.setText(String.valueOf(avg)+" " + HomeFragment.default_currency);
        String tempTrans=a;

        Log.d(TAG, "onCreate: Budget "+stringBudget);

        if(stringBudget.equals("Not Set")||stringBudget.isEmpty()){
            numTransactions.setTextColor(getResources().getColor(R.color.primaryTextColorBetalightbackground,getTheme()));
            spentFromBudget.setText("Not Set");
        }
        else if(totalExpense>Float.valueOf(stringBudget)){
            numTransactions.setTextColor(getResources().getColor(R.color.Expense,getTheme()));
            spentFromBudget.setText(stringBudget+ " " + HomeFragment.default_currency);
        }
        else{
            numTransactions.setTextColor(getResources().getColor(R.color.Income,getTheme()));
            spentFromBudget.setText(stringBudget+ " " + HomeFragment.default_currency);
        }

        numTransactions.setVisibility(View.VISIBLE);

        numTransactions.setText(String.valueOf(totalExpense)+ " " + HomeFragment.default_currency);

        float consume = (Math.round(((totalExpense-totalExpense_lastmonth)/totalExpense)*10000)/100);
        /*if(consume<0){
            percentLastMonth_2.setText(String.valueOf(consume*-1) + "%");
            percentLastMonth.setVisibility(View.INVISIBLE);
            decrease.setVisibility(View.VISIBLE);
        }
        else if(totalExpense_lastmonth==0){
            percentLastMonth.setText(String.valueOf(consume)  + "%");
            percentLastMonth_2.setVisibility(View.INVISIBLE);
            increase.setVisibility(View.VISIBLE);
        }
        else {
            percentLastMonth.setText(String.valueOf(consume)+ "%");
            percentLastMonth_2.setVisibility(View.INVISIBLE);
            increase.setVisibility(View.VISIBLE);
        }*/

        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryGraph.this,MainActivity.class);
                intent.putExtra("Graph", "Graph");
                startActivity(intent);
            }
        });

        ImageButton fab_home=  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryGraph.this, MainActivity.class);
                startActivity(intent);
            }
        });




        //percentLastMonth= findViewById(R.id.percentLastMonth);
        //spentFromBudget = findViewById(R.id.spentFromBudget);
        //numTransactions= findViewById(R.id.numTransactions);



        ArrayList<BarData> barData = new ArrayList<>();




        if(Integer.parseInt(a)==0){
            barChart.setVisibility(View.INVISIBLE);
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bar_chart));
            noTransactionPrompt_1.setText("Oops! You haven't added \ntransactions this month");
            noTransactionPrompt_2.setText("Add transactions to see your chart ");
            mRecyclerView.setVisibility(View.INVISIBLE);
        }
        else{
            BarDataSet perDayExpenses = new BarDataSet(dateExpenseValues, "test");
            perDayExpenses.setColor(getColor(mGetColor(aGraphDetails[0])));
            ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
            barDataSets.add(perDayExpenses);
            BarData data = new BarData(barDataSets);
            data.setBarWidth(0.9f);
            barChart.setData(data);
            barChart.setMaxVisibleValueCount(40);
            barChart.setDrawGridBackground(false);
            barChart.setDragYEnabled(false);
            //barChart.setScaleEnabled(true);
            barChart.getDescription().setEnabled(false);
            barChart.getLegendRenderer();
            barChart.getLegend().setEnabled(false);

            XAxis xAxis = barChart.getXAxis();

            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
            xAxis.setGranularity(1);
            xAxis.setAxisMinimum(0);
            xAxis.setAxisMaximum(31);
            YAxis yAxisLeft = barChart.getAxisLeft();
            yAxisLeft.setDrawZeroLine(true);
            yAxisLeft.mAxisMinimum=0;
            yAxisLeft.mAxisMaximum=6;
            yAxisLeft.setSpaceBottom(10f);
            YAxis yAxisRight = barChart.getAxisRight();
            yAxisRight.mAxisMinimum=0;
            //yAxisRight.setSpaceBottom(5f);
            yAxisRight.setEnabled(false);
            /*yAxisLeft.setDrawGridLines(false);
            yAxisRight.setDrawGridLines(false);*/
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(true);


            barChart.animateY(2000);
            barChart.invalidate();


            }

        }




    private int mGetColor(String Category){
        switch (Category) {
            case "Health":
                return R.color.healthcare;
            case "Donations":
                return R.color.donations;
            case "Bills":
                return R.color.bills;
            case "Shopping":
                return R.color.shopping;
            case "Dining Out":
                return  R.color.dinning;
            case "Entertainment":
                return R.color.entertainment;
            case "Groceries":
                return R.color.groceries;
            case "Pet Care":
                return R.color.pets;
            case "Transportation":
                return R.color.transportation;
            case "Loans":
                return R.color.loans;
            case "Personal Care":
                return R.color.personalcare;
            case "Miscellaneous":
                return R.color.miscellaneous;
            default:
                return R.color.other;
        }
    }

    private String getMonth (String month){
        String retMonth;
        Log.d(TAG,"Month in fn:" + month);
        switch (month){
            case "1" : return "January";
            case "2" : return "February";
            case "3" : return "March";
            case "4" : return "April";
            case "5" : return "May";
            case "6" : return "June";
            case "7" : return "July";
            case "8" : return "August";
            case "9" : return "September";
            case "10" : return "October";
            case "11" : return "November";
            case "12" : return "December";
            default : return "Error";
        }
    }

    private int getnumdays(int monthDefault, int yearDefault) {
        int EndDate;
        switch (monthDefault) {
            case 2: {
                if (yearDefault % 4 == 0)
                    EndDate = 29;
                else EndDate = 28;
                break;}
            case 4:
                EndDate =30;
                break;
            case 6:
                EndDate = 30;
                break;
            case 9:
                EndDate = 30;
                break;
            case 11:
                EndDate = 30;
                break;
            default:
                EndDate =31;
                break;
        }
        return EndDate;
    }

}