package com.example.Bachat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Calendar;

public class ThresholdFragment extends Fragment {
    DatabaseHelper myDb;
    View view;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    ArrayList<ThresholdListItem> ThresholdListItems;
    String monthString;
    String selectedmonth = "-1";
    ListView listshowthreshold;
    TextView remainingAmount;
    int month;
    String profile=HomeFragment.current_profile;
    float categorySum =0;
    float remaining;
    float monthbudget;
    String[] categoryvalues = new String[12];
    RelativeLayout budgetImage;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myDb= new DatabaseHelper(getActivity());
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.threshold_show_all_screen, container, false);
        //Log.d(TAG, "onCreate: Threshold View Screen");
         listshowthreshold = (ListView) view.findViewById(R.id.listViewThreshold);
        remainingAmount = view.findViewById(R.id.remainingAmount);
        budgetImage=view.findViewById(R.id.budgetImage);
        budgetImage.setVisibility(View.INVISIBLE);
        Calendar calendar = Calendar.getInstance();
        month=calendar.get(Calendar.MONTH)+1;
        Log.d("current month", "onCreateView: "+ month);



        if (month == 1){
            monthString = "January";
        } else if ( month == 2){
            monthString = "February";
        } else if (month == 3){
            monthString = "March";
        } else if (month == 4){
            monthString = "April";
        } else if (month == 5){
            monthString = "May";
        } else if (month == 6){
            monthString = "June";
        } else if (month == 7){
            monthString = "July";
        } else if (month == 8){
            monthString = "August";
        } else if (month == 9){
            monthString = "September";
        } else if (month == 10){
            monthString = "October";
        } else if (month == 11){
            monthString = "November";
        } else if (month == 12){
            monthString = "December";
        }


        Boolean resultcheck=myDb.checkEntryThreshold(String.valueOf(month),HomeFragment.current_profile);

        if (resultcheck){

            Cursor res = myDb.getEntryThreshold(String.valueOf(month),HomeFragment.current_profile);

            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {
                buffer.append(res.getString(0) + "," + res.getString(1) + "\n");
            }

            String[] bufferdata  = buffer.toString().split("\n");

            ThresholdListItems = new ArrayList<>();

            for(int i=0; i< bufferdata.length; i++){
                String[] listindex  = bufferdata[i].split(",");
                ThresholdListItem item = new ThresholdListItem(listindex[0],listindex[1],getIcon(listindex[0]));
                ThresholdListItems.add(item);
            }
            //for month budget
            if (res.moveToFirst()) {
                if (!(res.getString(1).equalsIgnoreCase("Not Set"))){
                    monthbudget = Float.parseFloat(res.getString(1));
                }
                else{
                    monthbudget = -1;
                }

            }
            //end for month budget

            // for remaining amount
            StringBuffer buffer2 = new StringBuffer();
            while (res.moveToNext()) {
            buffer2.append(res.getString(1) +  "\n");
            }
            String[] bufferdata2  = buffer2.toString().split("\n");

            Log.d("bufferdata length is" , String.valueOf(bufferdata2.length));
            Log.d("bufferdata content is" ,buffer2.toString());

            for(int i=0;i<=11;i++){
                int j=i+1;
                res.moveToPosition(j);
                categoryvalues[i]=res.getString(1);
                if(!categoryvalues[i].equalsIgnoreCase("Not Set")){
                    categorySum += Float.parseFloat(categoryvalues[i]);
                }
            }
            //for remianing amount close
            //for remaining text view
            if (monthbudget == -1)
            {
                remaining = -1;
                remainingAmount.setText("No Budget yet");
            }
            else{
                Log.d("category sum :", String.valueOf(categorySum));
                remaining = monthbudget-categorySum;
                remainingAmount.setText("Remaining Budget " + String.valueOf(remaining));
                categorySum =0;
            }
            //for remianing text view close
        }
        else{
            Boolean returncheck = myDb.createNewEntryThreshold(String.valueOf(month),HomeFragment.current_profile);


            Cursor res = myDb.getEntryThreshold(String.valueOf(month),HomeFragment.current_profile);

            StringBuffer buffer = new StringBuffer();

            while (res.moveToNext()) {
                buffer.append(res.getString(0) + "," + res.getString(1) + "\n");
            }


            String[] bufferdata  = buffer.toString().split("\n");

            ThresholdListItems = new ArrayList<>();

            for(int i=0; i< bufferdata.length; i++){
                String[] listindex  = bufferdata[i].split(",");
                ThresholdListItem item = new ThresholdListItem(listindex[0],listindex[1],getIcon(listindex[0]));
                ThresholdListItems.add(item);
            }

            //for month budget
            if (res.moveToFirst()) {
                if (!(res.getString(1).equalsIgnoreCase("Not Set"))){
                    monthbudget = Float.parseFloat(res.getString(1));
                }
                else{
                    monthbudget = -1;
                }

            }
            //end for month budget

            // for remaining amount
            StringBuffer buffer2 = new StringBuffer();
            while (res.moveToNext()) {
                buffer2.append(res.getString(1) +  "\n");
            }
            String[] bufferdata2  = buffer2.toString().split("\n");

            Log.d("bufferdata length is" , String.valueOf(bufferdata2.length));
            Log.d("bufferdata content is" ,buffer2.toString());

            for(int i=0;i<=11;i++){
                int j=i+1;
                res.moveToPosition(j);
                categoryvalues[i]=res.getString(1);
                if(!categoryvalues[i].equalsIgnoreCase("Not Set")){
                    categorySum += Float.parseFloat(categoryvalues[i]);
                }
            }
            //for remianing amount close
            //for remaining text view
            if (monthbudget == -1)
            {
                remaining = -1;
                remainingAmount.setText("N/A");
            }
            else{
                remaining = monthbudget-categorySum;
                remainingAmount.setText(String.valueOf(remaining));
                categorySum =0;
            }
            //for remianing text view close

        }



        ThresholdViewAllAdapter adapter = new ThresholdViewAllAdapter(getActivity(), R.layout.threshold_view_all_layout, ThresholdListItems);
        listshowthreshold.setAdapter(adapter);

       listshowthreshold.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String monthpassed;
                if((selectedmonth.equalsIgnoreCase("-1")) || (selectedmonth.equalsIgnoreCase("Month"))){
                    monthpassed = String.valueOf(month);
                    Log.d("month", "month string -1 ");

                }
                else {
                    Log.d("month", "month string is selected from spinner");
                    monthpassed = selectedmonth;
                }
                Intent intent = new Intent(getActivity(), EditThresholdScreen.class);
                String toeditcategory =  ThresholdListItems.get(position).getCategory();
                String toeditamount =  ThresholdListItems.get(position).getAmount();
                String toedit = toeditcategory+","+toeditamount+"," + monthpassed +","+ HomeFragment.current_profile;
                intent.putExtra("toeditfromthresholdviewscreen", toedit);
                startActivity(intent);
            }
        });


        Spinner MonthSpinner = view.findViewById(R.id.spinnerMonthThreshold);

        //Creating an ArrayList to populate the months
        ArrayList<String> MonthList = new ArrayList<String>();
        MonthList.add("Month");
        MonthList.add("January");
        MonthList.add("February");
        MonthList.add("March");
        MonthList.add("April");
        MonthList.add("May");
        MonthList.add("June");
        MonthList.add("July");
        MonthList.add("August");
        MonthList.add("September");
        MonthList.add("October");
        MonthList.add("November");
        MonthList.add("December");




        ArrayAdapter<String> monthAdapter;
        monthAdapter = new ArrayAdapter<String>(getActivity(), R.layout.threshold_spinner_layout, MonthList);
        //categoryAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        MonthSpinner.setAdapter(monthAdapter);
        String compareValue = HomeFragment.default_currency;
        MonthSpinner.setSelection(month);
        MonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedmonth = parent.getItemAtPosition(position).toString();
                //Log.d("Test", "onItemSelected: " + selectedmonth);

                if(selectedmonth=="January"){selectedmonth=String.valueOf(1);}
                else if(selectedmonth == "February"){selectedmonth=String.valueOf(2);}
                else if(selectedmonth == "March"){selectedmonth=String.valueOf(3);}
                else if(selectedmonth == "April"){selectedmonth=String.valueOf(4);}
                else if(selectedmonth == "May"){selectedmonth=String.valueOf(5);}
                else if(selectedmonth == "June"){selectedmonth=String.valueOf(6);}
                else if(selectedmonth == "July"){selectedmonth=String.valueOf(7);}
                else if(selectedmonth == "August"){selectedmonth=String.valueOf(8);}
                else if(selectedmonth == "September"){selectedmonth=String.valueOf(9);}
                else if(selectedmonth == "October"){selectedmonth=String.valueOf(10);}
                else if(selectedmonth == "November"){selectedmonth=String.valueOf(11);}
                else if(selectedmonth == "December"){selectedmonth=String.valueOf(12);}
                //Log.d("Test2", "onItemSelectedconverted: " + selectedmonth);



                if(selectedmonth.equalsIgnoreCase("Month")){
                    Toast.makeText(getActivity(),"nothing to refresh",Toast.LENGTH_SHORT).show();
                    listshowthreshold.setVisibility(View.INVISIBLE);
                    budgetImage.setVisibility(View.VISIBLE);

                }

                else{ // creating and updating the list view according to the month selected from the spinner
                    Log.d("else", "choose month else");
                    Boolean resultcheck=myDb.checkEntryThreshold(String.valueOf(selectedmonth),HomeFragment.current_profile);
                    listshowthreshold.setVisibility(View.VISIBLE);
                    budgetImage.setVisibility(View.INVISIBLE);

                    if (resultcheck){

                        Cursor res = myDb.getEntryThreshold(String.valueOf(selectedmonth),HomeFragment.current_profile);

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append(res.getString(0) + "," + res.getString(1) + "\n");
                        }


                        String[] bufferdata  = buffer.toString().split("\n");

                        ThresholdListItems = new ArrayList<>();

                        for(int i=0; i< bufferdata.length; i++){
                            String[] listindex  = bufferdata[i].split(",");
                            ThresholdListItem item = new ThresholdListItem(listindex[0],listindex[1],getIcon(listindex[0]));
                            ThresholdListItems.add(item);
                        }


                        //for month budget
                        if (res.moveToFirst()) {
                            if (!(res.getString(1).equalsIgnoreCase("Not Set"))){
                                monthbudget = Float.parseFloat(res.getString(1));
                            }
                            else{
                                monthbudget = -1;
                            }

                        }
                        //end for month budget

                        // for remaining amount
                        StringBuffer buffer2 = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer2.append(res.getString(1) +  "\n");
                        }
                        String[] bufferdata2  = buffer2.toString().split("\n");

                        Log.d("bufferdata length is" , String.valueOf(bufferdata2.length));
                        Log.d("bufferdata content is" ,buffer2.toString());

                        for(int i=0;i<=11;i++){
                            int j=i+1;
                            res.moveToPosition(j);
                            categoryvalues[i]=res.getString(1);
                            if(!categoryvalues[i].equalsIgnoreCase("Not Set")){
                                categorySum += Float.parseFloat(categoryvalues[i]);
                            }
                        }
                        //for remianing amount close
                        //for remaining text view
                        if (monthbudget == -1)
                        {
                            remaining = -1;
                            remainingAmount.setText("N/A");
                        }
                        else{
                            remaining = monthbudget-categorySum;
                            remainingAmount.setText(String.valueOf(remaining));
                            categorySum =0;
                        }
                        //for remianing text view close
                    }
                    else{
                        Log.d("nested else", "creating new db entries ");
                        myDb.createNewEntryThreshold(String.valueOf(selectedmonth),HomeFragment.current_profile);

                    }

                    Cursor res = myDb.getEntryThreshold(String.valueOf(selectedmonth),HomeFragment.current_profile);

                    StringBuffer buffer = new StringBuffer();

                    while (res.moveToNext()) {
                        buffer.append(res.getString(0) + "," + res.getString(1) + "\n");
                    }


                    String[] bufferdata  = buffer.toString().split("\n");

                    ThresholdListItems = new ArrayList<>();

                    for(int i=0; i< bufferdata.length; i++){
                        String[] listindex  = bufferdata[i].split(",");
                        ThresholdListItem item = new ThresholdListItem(listindex[0],listindex[1],getIcon(listindex[0]));
                        ThresholdListItems.add(item);
                    }


                    //for month budget
                    if (res.moveToFirst()) {
                        if (!(res.getString(1).equalsIgnoreCase("Not Set"))){
                            monthbudget = Float.parseFloat(res.getString(1));
                        }
                        else{
                            monthbudget = -1;
                        }

                    }
                    //end for month budget

                    // for remaining amount
                    StringBuffer buffer2 = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer2.append(res.getString(1) +  "\n");
                    }
                    String[] bufferdata2  = buffer2.toString().split("\n");

                    Log.d("bufferdata length is" , String.valueOf(bufferdata2.length));
                    Log.d("bufferdata content is" ,buffer2.toString());

                    for(int i=0;i<=11;i++){
                        int j=i+1;
                        res.moveToPosition(j);
                        categoryvalues[i]=res.getString(1);
                        if(!categoryvalues[i].equalsIgnoreCase("Not Set")){
                            categorySum += Float.parseFloat(categoryvalues[i]);
                        }
                    }
                    //for remianing amount close
                    //for remaining text view
                    if (monthbudget == -1)
                    {
                        remaining = -1;
                        remainingAmount.setText("N/A");
                    }
                    else{
                        remaining = monthbudget-categorySum;
                        remainingAmount.setText(String.valueOf(remaining));
                        categorySum =0;
                    }
                    //for remianing text view close

                    ThresholdViewAllAdapter adapter = new ThresholdViewAllAdapter(getActivity(), R.layout.threshold_view_all_layout, ThresholdListItems);
                    listshowthreshold.setAdapter(adapter);

                    if (Integer.parseInt(selectedmonth) == 1){
                        monthString = "January";
                    } else if (Integer.parseInt(selectedmonth) == 2){
                        monthString = "February";
                    } else if (Integer.parseInt(selectedmonth) == 3){
                        monthString = "March";
                    } else if (Integer.parseInt(selectedmonth) == 4){
                        monthString = "April";
                    } else if (Integer.parseInt(selectedmonth) == 5){
                        monthString = "May";
                    } else if (Integer.parseInt(selectedmonth) == 6){
                        monthString = "June";
                    } else if (Integer.parseInt(selectedmonth) == 7){
                        monthString = "July";
                    } else if (Integer.parseInt(selectedmonth) == 8){
                        monthString = "August";
                    } else if (Integer.parseInt(selectedmonth) == 9){
                        monthString = "September";
                    } else if (Integer.parseInt(selectedmonth) == 10){
                        monthString = "October";
                    } else if (Integer.parseInt(selectedmonth) == 11){
                        monthString = "November";
                    } else if (Integer.parseInt(selectedmonth) == 12){
                        monthString = "December";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        return view;

    }
    public void callParentMethod(){
        getActivity().onBackPressed();
    }


    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private String getIcon(String Category) {
        switch (Category.toUpperCase()) {
            case "HEALTH":
                return "drawable://" + R.drawable.healthcare;
            case "DONATIONS":
                return "drawable://" + R.drawable.donations;
            case "BILLS":
                return "drawable://" + R.drawable.billsicon;
            case "SHOPPING":
                return "drawable://" + R.drawable.shoppingicon;
            case "DINING OUT":
                return "drawable://" + R.drawable.dinningout;
            case "ENTERTAINMENT":
                return "drawable://" + R.drawable.entertaimenticon;
            case "GROCERIES":
                return "drawable://" + R.drawable.groceries_icon;
            case "PET CARE":
                return "drawable://" + R.drawable.petsicon;
            case "TRANSPORTATION":
                return "drawable://" + R.drawable.transportation;
            case "LOANS":
                return "drawable://" + R.drawable.loansicon;
            case "PERSONAL CARE":
                return "drawable://" + R.drawable.personalcareicon;
            case "MISCELLANEOUS":
                return "drawable://" + R.drawable.miscellaneousicon;
            case "BUDGET":
                return "drawable://" + R.drawable.thresholdiconcolor;
            default:
                return "drawable://" + R.drawable.other;
        }
    }

}


