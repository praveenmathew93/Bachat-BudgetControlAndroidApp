package com.example.Bachat;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class GraphInflater extends Fragment {
    View view;
    private static final String TAG = "HomeFragment";
    Button imageButton;
    Button imageButton2;
    Button test_button_2;
    DatabaseHelper myDb;
    public static String username;
    public static String current_profile;
    public static String default_currency;
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    ArrayList PieEntryLabels;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_inflater, container, false);
        myDb = new DatabaseHelper(getActivity());
        Cursor Income = myDb.getEarningAmountMain();
        Cursor Expense = myDb.getExpenseAmountMain();
        pieChart = view.findViewById(R.id.piechart);
        TextView summary = view.findViewById(R.id.summary);
        ImageView imageView = (ImageView) view.findViewById(R.id.action_image);
        TextView noTransactionPrompt_1 = (TextView) view.findViewById(R.id.prompt_no_transactions);
        TextView noTransactionPrompt_2 = (TextView) view.findViewById(R.id.prompt_no_transactions_2);
        Float pie_Expense = 0.f;
        Float pie_Income = 0.f;
        if (Expense.moveToFirst() && Expense.getString(0) != null) {
            Log.d(TAG, "onCreateView: Expense Block");
            do {
                Log.d(TAG, "onCreateView: Expense Block 2");
                pie_Expense = Float.valueOf(Expense.getString(0));
            } while (Expense.moveToNext());

            Expense.close();
        }


        if (Income.moveToFirst() && Income.getString(0) != null) {
            Log.d(TAG, "onCreateView: Income Block");
            do {
                Log.d(TAG, "onCreateView: Income Block 2");
                pie_Income = Float.valueOf(Income.getString(0));
            } while (Income.moveToNext());
            Income.close();
        }


        if (pie_Income == 0.f && pie_Expense == 0.f) {
            Log.d(TAG, "Inside if Loop income fucked");
            imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.pie_chart));
            noTransactionPrompt_1.setText("Oops! You haven't added \ntransactions this month");
            noTransactionPrompt_2.setText("Add transactions to see your chart ");
            pieChart.setVisibility(view.INVISIBLE);
            summary.setVisibility(View.INVISIBLE);
        }
        else {
            pieChart.setVisibility(view.VISIBLE);
            imageView.setVisibility(view.INVISIBLE);
            noTransactionPrompt_1.setVisibility(view.INVISIBLE);
            noTransactionPrompt_2.setVisibility(view.INVISIBLE);
            summary.setVisibility(View.VISIBLE);
            pieEntries = new ArrayList();
            ArrayList<Integer> colors = new ArrayList<>();
            float netBalance = (float) ((Math.round((pie_Income - pie_Expense)*100))/100.0);
            Log.d(TAG, "test" + pie_Income.toString() + pie_Expense.toString());
            if(pie_Income==0f){
                pieEntries.add(new PieEntry(pie_Expense, "EXPENSE"));
                colors.add(ContextCompat.getColor(getActivity(), R.color.expense_background));
            }
            else if (pie_Expense==0f){
                pieEntries.add(new PieEntry(pie_Income, "INCOME"));
                colors.add(ContextCompat.getColor(getActivity(), R.color.income_background));

            }
            else {
                pieEntries.add(new PieEntry(pie_Income, "INCOME"));
                pieEntries.add(new PieEntry(pie_Expense, "EXPENSE"));
                colors.add(ContextCompat.getColor(getActivity(), R.color.income_background));
                colors.add(ContextCompat.getColor(getActivity(), R.color.expense_background));

            }
            pieChart.setRotationAngle(135f);
            pieDataSet = new PieDataSet(pieEntries, "");
            pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            //pieDataSet.setValueLinePart1OffsetPercentage(30.f);
            //pieDataSet.setValueLinePart1Length(0.3f);
            //pieDataSet.setValueLinePart2Length(.0f);
            //pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            //pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            pieDataSet.setColors(colors);
            pieDataSet.setSliceSpace(0f);
            pieDataSet.setDrawValues(false);
            pieChart.setEntryLabelColor(Color.WHITE);
            pieChart.setEntryLabelTextSize(15f);
            //pieDataSet.setValueTextColor(Color.BLACK);
            //pieDataSet.setValueTextSize(15f);
            pieChart.setTransparentCircleAlpha(0);
            pieChart.setCenterTextSize(18f);
            pieChart.setCenterText("Net Balance \n" + netBalance);
            pieChart.setRotationEnabled(false);
            pieChart.getLegendRenderer();
            pieChart.getLegend().setEnabled(false);
            pieChart.getDescription().setEnabled(false);
            pieChart.setTouchEnabled(false);
        }

        return view;

    }
}
