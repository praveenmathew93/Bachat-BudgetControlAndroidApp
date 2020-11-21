package com.example.Bachat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class GraphFragmentInflater extends Fragment {
    View view;
    private static String TAG = "Graphs";
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    //ArrayList pieEntries;
    ArrayList PieEntryLabels;
    ArrayList<PieEntry> pieEntries;
    DatabaseHelper myDB;
    String sdate = "Start Date";
    String edate = "End Date";
    String StartDate;
    String EndDate;
    String SelectMonth;
    String SelectYear;
    //public FloatingActionButton filter2;
    public String fdate = "Start Date";
    public String ldate = "End Date";
    private RecyclerView mRecyclerView;
    private GraphCategoryListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FrameLayout frameLayout;
    private FrameLayout frameLayout2;


    //Values from Dialog box
    public String startDateDialog = "Start Date";
    public String endDateDialog = "End Date";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.graphfragmentinflater, container, false);

        //ListView mListView = (ListView) view.findViewById(R.id.categoryList);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.categoryList);
        pieChart = view.findViewById(R.id.piechart);
        ImageView imageView = view.findViewById(R.id.action_image);
        TextView noTransactionPrompt_1 = view.findViewById(R.id.prompt_no_transactions);
        TextView noTransactionPrompt_2 = view.findViewById(R.id.prompt_no_transactions_2);
        //frameLayout = view.findViewById(R.id.divider_2);

        Bundle bundle = this.getArguments();
        /*if(bundle != null){
            StartDate = bundle.getString("start_date");
            EndDate = bundle.getString("end_date");
            Log.d(TAG, "Bundle Test: " +StartDate+ EndDate);
            bundle.clear();
        }*/

        if (bundle != null) {
            SelectMonth = bundle.getString("select_month");
            SelectYear = bundle.getString("select_year");
            Log.d(TAG, "Bundle Test: " + SelectMonth + SelectYear);
            bundle.clear();
        }

        Calendar calendar = Calendar.getInstance();
        int monthDefault = calendar.get(Calendar.MONTH) + 1;
        int yearDefault = calendar.get(Calendar.YEAR);

        myDB = new DatabaseHelper(getActivity());

        /*if(StartDate==null){
            StartDate=yearDefault + "/" + (monthDefault) + "/" + 1;
        }
        else
            if(EndDate==null){
            switch(monthDefault){
                case 2 : {if(yearDefault%4==0) EndDate=yearDefault + "/" + (monthDefault) + "/" + 29;
                else EndDate=yearDefault + "/" + (monthDefault) + "/" + 28;}
                case 4 : EndDate=yearDefault + "/" + (monthDefault) + "/" + 30;
                case 6 : EndDate=yearDefault + "/" + (monthDefault) + "/" + 30;
                case 9 : EndDate=yearDefault + "/" + (monthDefault) + "/" + 30;
                case 11 : EndDate=yearDefault + "/" + (monthDefault) + "/" + 30;
                default : EndDate=yearDefault + "/" + (monthDefault) + "/" + 31;
            }
        }*/

        if (SelectYear == null || SelectMonth == null) {
            StartDate = yearDefault + "/" + (monthDefault) + "/0" + 1;
            switch (monthDefault) {
                case 2: {
                    if (yearDefault % 4 == 0)
                        EndDate = yearDefault + "/" + (monthDefault) + "/" + 29;
                    else EndDate = yearDefault + "/" + (monthDefault) + "/" + 28;
                    break;
                }
                case 4:
                    EndDate = yearDefault + "/" + (monthDefault) + "/" + 30;
                    break;
                case 6:
                    EndDate = yearDefault + "/" + (monthDefault) + "/" + 30;
                    break;
                case 9:
                    EndDate = yearDefault + "/" + (monthDefault) + "/" + 30;
                    break;
                case 11:
                    EndDate = yearDefault + "/" + (monthDefault) + "/" + 30;
                    break;
                default:
                    EndDate = yearDefault + "/" + (monthDefault) + "/" + 31;
                    break;
            }
        } else {
            StartDate = SelectYear + "/" + (SelectMonth) + "/0" + 1;
            int year = Integer.valueOf(SelectYear);
            switch (Integer.valueOf(SelectMonth)) {
                case 2: {
                    if (year % 4 == 0) EndDate = year + "/" + (SelectMonth) + "/" + 29;
                    else EndDate = year + "/" + (SelectMonth) + "/" + 28;
                }
                case 4:
                    EndDate = year + "/" + (SelectMonth) + "/" + 30;
                case 6:
                    EndDate = year + "/" + (SelectMonth) + "/" + 30;
                case 9:
                    EndDate = year + "/" + (SelectMonth) + "/" + 30;
                case 11:
                    EndDate = year + "/" + (SelectMonth) + "/" + 30;
                default:
                    EndDate = year + "/" + (SelectMonth) + "/" + 31;
            }
        }

        Log.d(TAG, StartDate + EndDate);


        Log.d(TAG, "Graph: StartDate" + StartDate + "EndDate:" + EndDate);

        Cursor categoryExpenseDB = myDB.getExpenseAmountByCategory(StartDate, EndDate);
        ArrayList<Integer> colors = new ArrayList<>();

        ArrayList<Drawable> categoryIcon = new ArrayList<>();
        //Drawable[] categoryIcon={} ;
        ArrayList<Float> categoryExpenseValue = new ArrayList<>();
        //float[] categoryExpenseValue={};
        ArrayList<String> categoryName = new ArrayList<>();
        //String[] categoryName=new String[15];
        ArrayList<Integer> color_array = new ArrayList<>();
        //int[] color_array={};
        float totalExpense = 0f;
        int i = 0;
        pieEntries = new ArrayList<PieEntry>();

        if (categoryExpenseDB.moveToFirst() && categoryExpenseDB.getString(0) != null) {
            do {
                categoryName.add(i, categoryExpenseDB.getString(0));
                categoryExpenseValue.add(i, Float.valueOf(categoryExpenseDB.getString(1)));
                Log.d(TAG, "Value from DB" + categoryName.get(i) + categoryExpenseValue.get(i));
                categoryIcon.add(i, ContextCompat.getDrawable(getActivity(), getIcon(categoryName.get(i))));
                color_array.add(i, ContextCompat.getColor(getActivity(), getColor(categoryName.get(i))));
                totalExpense = totalExpense + categoryExpenseValue.get(i);
                Log.d(TAG, "Total Expense" + totalExpense);
                i++;
            } while (categoryExpenseDB.moveToNext());
            categoryExpenseDB.close();
            color_array.add(i, ContextCompat.getColor(getActivity(), R.color.other));
            categoryIcon.add(i, ContextCompat.getDrawable(getActivity(), R.drawable.other_pie));
        }

        float lessThanFivePercent = 0;
        int color_index = 0;

        for (int j = 0; j < categoryName.size(); j++) {
            float temp = ((categoryExpenseValue.get(j) * 100) / totalExpense);
            if (temp > 5f) {
                String temp_catergory=categoryName.get(j);
                pieEntries.add(new PieEntry(categoryExpenseValue.get(j), categoryIcon.get(j),temp_catergory));
                colors.add(color_index, color_array.get(j));
                String a = categoryName.get(j);
                Log.d("Category Test", a + color_index);
                color_index++;
            } /*else {
                lessThanFivePercent = lessThanFivePercent + categoryExpenseValue.get(j);
            }*/
            //colors.add(color_index,color_array.get(color_array.size()-1));
        }
        /*if (lessThanFivePercent != 0) {
            pieEntries.add(new PieEntry(lessThanFivePercent, "Others"));
            Log.d("Category Test", "test" + color_index);
            colors.add(color_index, color_array.get(color_array.size() - 1));
        }*/

        if (pieEntries.isEmpty()) {
            pieChart.setVisibility(View.INVISIBLE);
            //frameLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.pie_chart));
            noTransactionPrompt_1.setText("Oops! You haven't added \ntransactions this month");
            noTransactionPrompt_2.setText("Add transactions to see your chart ");

        } else {
            imageView.setVisibility(View.INVISIBLE);
            //frameLayout.setVisibility(View.INVISIBLE);
            noTransactionPrompt_1.setVisibility(View.INVISIBLE);
            noTransactionPrompt_2.setVisibility(View.INVISIBLE);
            pieChart.setVisibility(View.VISIBLE);
            //mListView.setVisibility(View.VISIBLE);
            pieDataSet = new PieDataSet(pieEntries, "");
            pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            //Log.d("Colors", "color" + colors.get(colors.size()-1));
            pieDataSet.setUsingSliceColorAsValueLineColor(true);
            pieDataSet.setValueLinePart1OffsetPercentage(90.f);
            pieDataSet.setValueLinePart1Length(0.7f);
            pieDataSet.setValueLinePart2Length(0f);
            pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            pieDataSet.setSelectionShift(90);
            pieDataSet.setColors(colors);
            pieDataSet.setSliceSpace(0f);
            pieDataSet.setDrawValues(false);
            pieChart.setEntryLabelColor(Color.WHITE);
            pieChart.setEntryLabelTextSize(13);
            //pieDataSet.setValueTextColor(Color.BLACK);
            //pieDataSet.setValueTextSize(15f);
            pieChart.setTransparentCircleAlpha(110);
            pieChart.setTransparentCircleRadius(55);
            //pieChart.setCenterTextSize(15f);
            //pieDataSet.setIconsOffset(new MPPointF(0,50)); //without hole radius
            pieDataSet.setIconsOffset(new MPPointF(0, 80)); //with hole radius
            pieChart.setRotationEnabled(true);
            pieChart.getLegendRenderer();
            pieChart.getLegend().setEnabled(false);
            pieChart.getDescription().setEnabled(false);
            pieChart.setHoleRadius(40);
            pieChart.animateXY(1000, 1000);
            pieChart.setTouchEnabled(true);
            pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    String category= (String) e.getData();
                    pieDataSet.setSelectionShift(0);
                    Intent intent = new Intent(getActivity(), CategoryGraph.class);
                    intent.putExtra("GraphDetails",category+"," + StartDate + "," + EndDate);
                    startActivity(intent);
                }

                @Override
                public void onNothingSelected() {
                    pieDataSet.setSelectionShift(0);
                    Log.e(TAG, "onValueSelected: not Listening" );

                }
            });


            GraphCategory GraphCategory1 = new GraphCategory("Health",
                            R.drawable.healthcare,
                            getCategoryPercent(myDB, StartDate, EndDate, "Health", totalExpense),
                            getTransactions(myDB, StartDate, EndDate, "Health"));
            Log.d(TAG, "GraphCategory:" + GraphCategory1);
            GraphCategory GraphCategory2 = new GraphCategory("Donations",
                    R.drawable.donations,
                    getCategoryPercent(myDB, StartDate, EndDate, "Donations",totalExpense),
                    getTransactions(myDB, StartDate, EndDate, "Donations"));
            GraphCategory GraphCategory3 = new GraphCategory("Bills",
                    R.drawable.billsicon,
                    getCategoryPercent(myDB, StartDate, EndDate, "Bills",totalExpense),
                    getTransactions(myDB, StartDate, EndDate, "Bills"));
            GraphCategory GraphCategory4 = new GraphCategory("Shopping",
                    R.drawable.shoppingicon,
                    getCategoryPercent(myDB, StartDate, EndDate, "Shopping",totalExpense),
                    getTransactions(myDB, StartDate, EndDate, "Shopping"));
            GraphCategory GraphCategory5 = new GraphCategory("Dining Out",
                    R.drawable.dinningout,
                    getCategoryPercent(myDB, StartDate, EndDate, "Dining Out",totalExpense),
                    getTransactions(myDB, StartDate, EndDate, "Dining Out"));
            GraphCategory GraphCategory6 = new GraphCategory("Entertainment",
                    R.drawable.entertaimenticon,
                    getCategoryPercent(myDB, StartDate, EndDate, "Entertainment",totalExpense),
                    getTransactions(myDB, StartDate, EndDate, "Entertainment"));
            GraphCategory GraphCategory7 = new GraphCategory("Groceries",
                    R.drawable.groceries_icon,
                    getCategoryPercent(myDB, StartDate, EndDate, "Groceries",totalExpense),
                    getTransactions(myDB, StartDate, EndDate, "Groceries"));
            GraphCategory GraphCategory8 = new GraphCategory("Pet Care",
                    R.drawable.petsicon,
                    getCategoryPercent(myDB, StartDate, EndDate, "Pet Care",totalExpense),
                    getTransactions(myDB, StartDate, EndDate, "Pet Care"));
            GraphCategory GraphCategory9 = new GraphCategory("Transportation",
                    R.drawable.transportation,
                    getCategoryPercent(myDB, StartDate, EndDate, "Transportation",totalExpense),
                    getTransactions(myDB, StartDate, EndDate, "Transportation"));
            GraphCategory GraphCategory10 = new GraphCategory("Loans",
                    R.drawable.loansicon,
                    getCategoryPercent(myDB, StartDate, EndDate, "Loans",totalExpense),
                    getTransactions(myDB, StartDate, EndDate, "Loans"));
            GraphCategory GraphCategory11 = new GraphCategory("Personal Care",
                    R.drawable.personalcareicon,
                    getCategoryPercent(myDB, StartDate, EndDate, "Personal Care",totalExpense),
                    getTransactions(myDB, StartDate, EndDate, "Personal Care"));
            GraphCategory GraphCategory12 = new GraphCategory("Miscellaneous",
                    +R.drawable.miscellaneousicon,
                    getCategoryPercent(myDB, StartDate, EndDate, "Miscellaneous",totalExpense),
                    getTransactions(myDB, StartDate, EndDate, "Miscellaneous"));
            Log.d(TAG, "GraphCategory:" + GraphCategory12);

            final ArrayList<GraphCategory> GraphCategoryList = new ArrayList<>();
            GraphCategoryList.add(GraphCategory1);
            GraphCategoryList.add(GraphCategory3);
            GraphCategoryList.add(GraphCategory2);
            GraphCategoryList.add(GraphCategory4);
            GraphCategoryList.add(GraphCategory5);
            GraphCategoryList.add(GraphCategory6);
            GraphCategoryList.add(GraphCategory7);
            GraphCategoryList.add(GraphCategory8);
            GraphCategoryList.add(GraphCategory9);
            GraphCategoryList.add(GraphCategory10);
            GraphCategoryList.add(GraphCategory11);
            GraphCategoryList.add(GraphCategory12);
            Collections.sort(GraphCategoryList, new Comparator<GraphCategory>() {
                @Override
                public int compare(GraphCategory o1, GraphCategory o2) {
                    return o2.getPercentage().compareTo(o1.getPercentage());
                }
            });


            /*GraphCategoryListAdapter adapter = new GraphCategoryListAdapter(getActivity(), R.layout.graphs_adapter, GraphCategoryList);
            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Log.d(TAG, "onItemClick:you clicked on a list item  " + peopleList.get(position).getName());
                    //toast.makeText(SecondScreen.this, "you clicked on " + peopleList.get(position).getName(), Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(getActivity(), ThirdScreen.class);
                    String toadd = GraphCategoryList.get(position).getCategoryName();
                    Toast.makeText(getActivity(), "In Progress", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "onItemClick: "+ toadd);
                    //intent.putExtra("fromSecond",toadd);
                    //startActivity(intent);
                }
            });*/

            Calendar.getInstance();
            Log.d(TAG, "onCreateView: Calendar Fuck" + Calendar.DAY_OF_MONTH);
            mLayoutManager = new LinearLayoutManager(getActivity());
            mAdapter = new GraphCategoryListAdapter(GraphCategoryList, getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnItemClickListener(new GraphCategoryListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    GraphCategoryList.get(position);
                    if(getTransactions(myDB, StartDate, EndDate, GraphCategoryList.get(position).getCategoryName()).equals("0 Transactions")){
                        Log.d(TAG, "onItemClick: "+ "Test1");
                    }
                    else{
                        Intent intent = new Intent(getActivity(), CategoryGraph.class);
                        intent.putExtra("GraphDetails",GraphCategoryList.get(position).getCategoryName()+"," + StartDate + "," + EndDate);
                        startActivity(intent);

                    }
                    /*Intent intent = new Intent(SecondScreen.this, ThirdScreen.class);
                    String toadd =  categoryList.get(position).getName();
                    Log.d(TAG, "onItemClick: "+ toadd);
                    intent.putExtra("fromSecond",toadd);
                    startActivity(intent);*/
                }
            });


            pieChart.invalidate();


        }
        return view;
    }

    private int getIcon(String Category) {
        switch (Category) {
            case "Health":
                return R.drawable.healthcare_pie;
            case "Donations":
                return R.drawable.donations_pie;
            case "Bills":
                return R.drawable.billsicon_pie;
            case "Shopping":
                return R.drawable.shoppingicon_pie;
            case "Dining Out":
                return R.drawable.dinningout_pie;
            case "Entertainment":
                return R.drawable.entertaimenticon_pie;
            case "Groceries":
                return R.drawable.groceries_icon_pie;
            case "Pet Care":
                return R.drawable.petsicon_pie;
            case "Transportation":
                return R.drawable.transportation_pie;
            case "Loans":
                return R.drawable.loansicon_pie;
            case "Personal Care":
                return R.drawable.personalcareicon_pie;
            case "Miscellaneous":
                return R.drawable.miscellaneousicon_pie;
            default:
                return R.drawable.other;

        }
    }


    private int getIconList(String Category) {
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

    private int getColor(String Category){
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

    private String getMonth (int month){
        String retMonth;
        Log.d(TAG,"Month in fn:" + month);
        switch (month){
            case 1 : return "Jan";
            case 2 : return "Feb";
            case 3 : return "Mar";
            case 4 : return "Apr";
            case 5 : return "May";
            case 6 : return"Jun";
            case 7 : return "Jul";
            case 8 : return "Aug";
            case 9 : return "Sep";
            case 10 : return "Oct";
            case 11 : return "Nov";
            case 12 : return "Dec";
            default : return "Error";
        }
    }

    private String getCategoryPercent(DatabaseHelper myDB_M,String startDate_M,String endDate_M, String category_M,float total_expense_m){
        String percent = "0";
        String a="0";
        Cursor categoryExp = myDB_M.getExpenseAmountByCategory2(startDate_M,endDate_M,category_M);
        if (categoryExp.moveToFirst() && categoryExp.getString(0) != null) {
            do {
                a = categoryExp.getString(0);
            } while (categoryExp.moveToNext());
        }
        double amount = Double.valueOf(a);
        //percent = (float) ((Math.round(percent / total_expense_m) * 10000) / 100.0);
        percent = String.valueOf(Math.round((amount/total_expense_m) * 10000)/100.0) + "%";
        /*String[] percent_1 = percent.split(".");
        Log.d(TAG, "getCategoryPercent: "+ percent.split(".")[0]);
        /*String[] percent_1 = percent.split(".");
        if(percent_1[1]=="0%"){
            percent = percent_1+"%";
        }*/
        return percent;
    }


    private String getTransactions (DatabaseHelper myDB_M,String startDate_M,String endDate_M, String category_M){
        String a="0";
        String returnTransactionNumber;
        Cursor transactionNumber = myDB_M.getTransactionsOfCurrentMonth(startDate_M,endDate_M,category_M);
        if (transactionNumber.moveToFirst() && transactionNumber.getString(0) != null) {
            do {
                a = transactionNumber.getString(0);
            } while (transactionNumber.moveToNext());
        }

        if(Integer.valueOf(a)==1){
            returnTransactionNumber = a + " Transaction";
        }
        else{
            returnTransactionNumber = a + " Transactions";
        }
        return returnTransactionNumber;
    }

}
