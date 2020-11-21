package com.example.Bachat;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class GraphFragment extends Fragment /*implements FilterDialogGraph.FilterDialogGraphListener */{
    View view;
    private static String TAG = "Graphs";
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    //ArrayList pieEntries;
    ArrayList PieEntryLabels;
    ArrayList pieEntries;
    DatabaseHelper myDB;
    String sdate = "Start Date";
    String edate = "End Date";
    String selectedMonth = "Start Month";
    String selectedYear = "Start Year";
    String StartDate;
    String EndDate;
    int year,month;
    public FloatingActionButton filter2;
    public TextView datePicker;
    public TextView datePicker_2;
    public String fdate = "Start Date";
    public String ldate = "End Date";

    //Values from Dialog box
    public String startDateDialog = "Start Date";
    public String endDateDialog = "End Date";
    //GraphFragmentInflater graphFragmentInflater;


    private DatePickerDialog.OnDateSetListener mDateSetListener1;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.graph_fragment, container, false);
        datePicker = (TextView) view.findViewById(R.id.btnMonth);
        datePicker_2 = (TextView) view.findViewById(R.id.btnYear);
        Calendar.getInstance();
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonthYear(v);
            }
        });
        datePicker_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonthYear(v);
            }
        });
        /*filter2 = (FloatingActionButton) view.findViewById(R.id.btnFilters);
        filter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Opening the dialog: ");

                Bundle bundle = new Bundle();
                bundle.putString("startingDate", startDateDialog);
                bundle.putString("endingDate", endDateDialog);

                Log.d(TAG, "onClick: Bundle value is " + bundle);

                FilterDialogGraph dialog = new FilterDialogGraph();
                dialog.setArguments(bundle);
                dialog.setTargetFragment(GraphFragment.this, 1);
                dialog.show(getParentFragmentManager(), "FilterGraphDialog");
            }
        });*/

        getChildFragmentManager().beginTransaction().replace(R.id.container_fragment, new GraphFragmentInflater()).commit();

        return view;
    }

    public void callParentMethod(){
        getActivity().onBackPressed();
    }


    /*@Override
    public void applyTexts(String startDateChosen, String endDateChosen, String startDate, String endDate) {
        Log.d(TAG, "applyTexts: The values coming in are: " + startDateChosen + " " + endDateChosen
                + " " + startDate + " " + endDate);
        StartDate = startDateChosen;
        EndDate = endDateChosen;

        Bundle bundle_GI = new Bundle();
        bundle_GI.putString("start_month", startDateChosen);
        bundle_GI.putString("end_date", endDateChosen);
        GraphFragmentInflater graphFragmentInflater = new GraphFragmentInflater();
        graphFragmentInflater.setArguments(bundle_GI);
        getChildFragmentManager().beginTransaction().replace(R.id.container_fragment, graphFragmentInflater).commit();
    }*/
    public void btnMonthYear(View view){
        Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(),
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth_fn, int selectedYear_fn) {
                        Log.d(TAG, "onDateSet: the selected month is "+ selectedMonth_fn + " and the year is: " + selectedYear_fn);
                        selectedMonth = String.valueOf(selectedMonth_fn+1);
                        selectedYear = String.valueOf(selectedYear_fn);
                        Bundle bundle_GI = new Bundle();
                        bundle_GI.putString("select_month", selectedMonth);
                        bundle_GI.putString("select_year", selectedYear);
                        GraphFragmentInflater graphFragmentInflater = new GraphFragmentInflater();
                        graphFragmentInflater.setArguments(bundle_GI);
                        getChildFragmentManager().beginTransaction().replace(R.id.container_fragment, graphFragmentInflater).commit();
                        }
                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth(Calendar.JULY)
                .setMinYear(1990)
                .setActivatedYear(2020)
                .setMaxYear(2030)
                //.setMinMonth(Calendar.FEBRUARY)
                //.setTitle("Select trading month")
                //.setMonthRange(Calendar.FEBRUARY, Calendar.NOVEMBER)
                // .setMaxMonth(Calendar.OCTOBER)
                // .setYearRange(1890, 1890)
                // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                //.showMonthOnly()
                // .showYearOnly()
                .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(int selectedMonth) {
                        String month = " ";
                        switch (selectedMonth){
                            case 0: {datePicker.setText("January "); break;}
                            case 1: {datePicker.setText("February "); break;}
                            case 2: {datePicker.setText("March "); break;}
                            case 3: {datePicker.setText("April "); break;}
                            case 4: {datePicker.setText("May "); break;}
                            case 5: {datePicker.setText("June "); break;}
                            case 6: {datePicker.setText("July "); break;}
                            case 7: {datePicker.setText("August "); break;}
                            case 8: {datePicker.setText("September "); break;}
                            case 9: {datePicker.setText("October "); break;}
                            case 10: {datePicker.setText("November "); break;}
                            case 11: {datePicker.setText("December "); break;}
                        }
                    } })
                .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                    @Override
                    public void onYearChanged(int selectedYear) { datePicker_2.setText(String.valueOf(selectedYear)); } })
                .build().show();
        }

}