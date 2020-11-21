/*package com.example.Bachat;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.Bachat.DatabaseHelper;
import com.example.Bachat.R;

import java.util.ArrayList;
import java.util.Calendar;


public class FilterAlertDialog extends DialogFragment {
    private static final String TAG = "FilterAlertDialog";

    public interface OnInputSelected{
        void sendInput(String categoryChosen, String mopChosen, String startDateChosen, String endDateChosen);
    }
    public OnInputSelected mOnInputSelected;

    //widgets
    public Spinner categorySpinner;
    public Spinner modeSpinner;
    public TextView startDate;
    public TextView endDate;
    public TextView mActionOk, mActionCancel;
    DatabaseHelper myDb;
    private DatePickerDialog.OnDateSetListener mDateSetListener1;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private FilterDialog.FilterDialogListener listener;
    public int modePos = 0;
    public int catPos = 0;
    public String fdate = "Start Date";
    public String ldate = "End date";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog_filters, container, false);
        myDb = new DatabaseHelper(getActivity());

        //mActionOk = (TextView) view.findViewById(R.id.action_ok);
        //mActionCancel = (TextView) view.findViewById(R.id.action_cancel);


        categorySpinner = (Spinner) view.findViewById(R.id.spnCategory);
        Cursor resCategory = myDb.getAllExpenseCategory();
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
        categoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, CategoryExpenseList);
        //categoryAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setSelection(catPos);

        modeSpinner = (Spinner) view.findViewById(R.id.spnMop);
        Cursor resModeOdPayment = myDb.getAllModeOfPayments();
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

        modeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, modeOfPaymentList);
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
                        android.R.style.Theme_DeviceDefault_Dialog,mDateSetListener1,year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "/" + (month + 1) + "/" + dayOfMonth;
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
                        android.R.style.Theme_DeviceDefault_Dialog,mDateSetListener2,year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "/" + (month + 1) + "/" + dayOfMonth;
                endDate.setText(date);
            }
        };

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = categorySpinner.getSelectedItem().toString();
                String modeOfPayment = modeSpinner.getSelectedItem().toString();
                String firstDate = startDate.getText().toString();
                String lastDate = endDate.getText().toString();

                modePos = modeSpinner.getSelectedItemPosition();
                catPos = categorySpinner.getSelectedItemPosition();
                fdate = startDate.getText().toString();
                ldate = endDate.getText().toString();
                mOnInputSelected.sendInput(category, modeOfPayment, firstDate, lastDate);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        } catch (ClassCastException e) {
            Log.d(TAG, "onAttach: ClassCastException: "+ e.getMessage());
        }
    }
}
*/