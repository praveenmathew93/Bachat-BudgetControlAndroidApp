package com.example.Bachat;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.Bachat.DatabaseHelper;
import com.example.Bachat.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FilterEarningDialog extends AppCompatDialogFragment {
    private static final String TAG = "FilterDialog";
    public Spinner categorySpinner;
    public Spinner modeSpinner;
    public TextView startDate;
    public TextView endDate;
    public Button clear;
    DatabaseHelper myDb;
    private DatePickerDialog.OnDateSetListener mDateSetListener1;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private FilterDialogListener listener;
    private Bundle bundle = getArguments();
    public int modePos;
    public int catPos;
    public String fdate = "Start Date";
    public String ldate = "End Date";

    /*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            startDate.setText(savedInstanceState.getString("startingDate","Start Date"));
        }
    }*/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        myDb = new DatabaseHelper(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_filters,null);

        //super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            Log.d(TAG, "inside savedInstance if loop");
            //startDate.setText(savedInstanceState.getString("startingDate", "Start Date"));
        }



        Bundle bundle = this.getArguments();
        if(bundle != null){
            modePos = bundle.getInt("mopPosition");
            catPos = bundle.getInt("catPosition");
            fdate = bundle.getString("startingDate");
            ldate = bundle.getString("endingDate");
        }

        Log.d(TAG, "onClick: mode, cat, fdate, ldate: "+ modePos + " " + catPos + " " + fdate + " " + ldate);
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy/M/d");
        final String formattedDate = df.format(calendar.getTime());
        Log.d(TAG, "onCreateDialog: "+ formattedDate);


        builder.setView(view)
                //.setTitle("Filters")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int mopPos, categoryPos;
                        String initialDate, finalDate;
                        String category = categorySpinner.getSelectedItem().toString();
                        String modeOfPayment = modeSpinner.getSelectedItem().toString();
                        String firstDate = startDate.getText().toString();
                        String lastDate = endDate.getText().toString();
                        if(category.equals("All")){
                            Log.d(TAG, "Category: ");
                            category = "";
                        }if(modeOfPayment.equals("All")){
                            Log.d(TAG, "MOP: ");
                            modeOfPayment = "";
                        }if(firstDate.equals("Start Date") && lastDate.equals("End Date")){
                            Log.d(TAG, "StartDate: ");
                            lastDate = "";
                            firstDate = "";
                        }if(firstDate.equals("Start Date") && !lastDate.equals("End Date")){
                            firstDate = "1900/1/1";
                        }if(lastDate.equals("End Date") && !firstDate.equals("Start Date")){
                            lastDate = formattedDate;
                        }

                        mopPos = modeSpinner.getSelectedItemPosition();
                        categoryPos = categorySpinner.getSelectedItemPosition();
                        initialDate = startDate.getText().toString();
                        finalDate = endDate.getText().toString();
                        Log.d(TAG, "onClick: mode, cat, fdate, ldate: "+ modePos + " " + catPos + " " + fdate + " " + ldate);
                        listener.applyTexts(category, modeOfPayment, firstDate, lastDate, mopPos, categoryPos, initialDate, finalDate);

                        /*Bundle bundle = new Bundle();
                        bundle.putInt("mopPosition", mopPos);
                        bundle.putInt("catPosition", categoryPos);
                        bundle.putString("startingDate", initialDate);
                        bundle.putString("endingDate", finalDate);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        ShowAllExpenseFragment showAllExpenseFragment = new ShowAllExpenseFragment();
                        showAllExpenseFragment.setArguments(bundle);

                        fragmentTransaction.commit();*/
                    }
                });

        clear = (Button) view.findViewById(R.id.btnClear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categorySpinner.setSelection(0);
                modeSpinner.setSelection(0);
                startDate.setText("Start Date");
                endDate.setText("End Date");
            }
        });

        categorySpinner = (Spinner) view.findViewById(R.id.spnCategory);
        Cursor resCategory = myDb.getAllIncomeCategory();
        if (resCategory.getCount() == 0) {
            Log.d("myTag", "No data found");
        }

        //Creating an ArrayList to populate the categories from the database
        ArrayList<String> CategoryExpenseList = new ArrayList<String>();
        //for providing an 'All' option
        CategoryExpenseList.add("All");
        //Adding the categories from Database to ArrayList
        while (resCategory.moveToNext()) {
            CategoryExpenseList.add(resCategory.getString(0));
            Log.d("myTag", "reached while loop");
        }
        //Creating Spinner for Categories
        ArrayAdapter<String> categoryAdapter;
        categoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.filter_spinner_layout, CategoryExpenseList);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setSelection(catPos);

        modeSpinner = (Spinner) view.findViewById(R.id.spnMop);
        Cursor resModeOdPayment = myDb.getAllIncomeModeOfPayment();
        if (resModeOdPayment.getCount() == 0) {
            Log.d("myTag", "No data found");
        }

        //Creating an ArrayList to populate the categories from the database
        ArrayList<String> modeOfPaymentList = new ArrayList<String>();
        //for providing an 'All' option
        modeOfPaymentList.add("All");
        //Adding the categories from Database to ArrayList
        while (resModeOdPayment.moveToNext()) {
            modeOfPaymentList.add(resModeOdPayment.getString(0));
            Log.d("myTag", "reached while loop");
        }
        //Creating Spinner for Categories
        ArrayAdapter<String> modeAdapter;

        modeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.filter_spinner_layout, modeOfPaymentList);
        //categoryAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        modeSpinner.setAdapter(modeAdapter);
        modeSpinner.setSelection(modePos);


        startDate = (TextView) view.findViewById(R.id.editFirstDate);
        startDate.setText(fdate);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        R.style.MyDatePickerDialogTheme,mDateSetListener1,year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date;
                if(String.valueOf(dayOfMonth).length() == 1){
                    date = year + "/" + (month + 1) + "/0" + dayOfMonth;
                }else{
                    date = year + "/" + (month + 1) + "/" + dayOfMonth;
                }
                startDate.setText(date);
                //firstDate = date;
            }
        };
        endDate = (TextView) view.findViewById(R.id.editSecondDate);
        endDate.setText(ldate);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        R.style.MyDatePickerDialogTheme,mDateSetListener2,year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date;
                if(String.valueOf(dayOfMonth).length() == 1){
                    date = year + "/" + (month + 1) + "/0" + dayOfMonth;
                }else{
                    date = year + "/" + (month + 1) + "/" + dayOfMonth;
                }
                endDate.setText(date);
            }
        };
        return builder.create();
    }
    public interface FilterDialogListener{
        void applyTexts(String categoryChosen, String mopChosen, String startDateChosen, String endDateChosen,
                        int mop, int cat, String startDate, String endDate);

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FilterDialogListener) getTargetFragment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 /*  @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("startingDate", ldate);
    }*/


}
