/*package com.example.Bachat;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.Calendar;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

public class ShowAllScreen extends AppCompatActivity {
    DatabaseHelper myDb;
    Button getDetails;
    Button getSelectedDetails;
    Spinner monthSelected;
    Spinner yearSelected;
    Spinner daySelected;
    ArrayAdapter adapter;
    ListView filterList;
    Spinner filterOptionsSelected;
    Spinner categorySelected;
    int month,year;

    private static final String TAG = "ShowAll";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_screen);
        Log.d(TAG, "onCreate: created ViewAll screen");

        //Initialising and linking resources for filtering functionality
        daySelected = (Spinner) findViewById(R.id.spnDate);
        monthSelected = (Spinner) findViewById(R.id.spnMonth);
        yearSelected = (Spinner) findViewById(R.id.spnYear);
        filterOptionsSelected = (Spinner) findViewById(R.id.spnOptions);
        categorySelected = (Spinner) findViewById(R.id.spnCategory);
        getDetails = (Button) findViewById(R.id.btnFilter);


        //Spinner for options
        ArrayAdapter<String> optionsAdapter;
        String[] filterOptions = {"Show all", "Category Only", "Date Only", "Date and Category"};
        Spinner optionsSpinner = (Spinner) findViewById(R.id.spnOptions);
        optionsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, filterOptions);
        optionsSpinner.setAdapter(optionsAdapter);

        optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String options = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), options + " chosen", Toast.LENGTH_SHORT).show();
                if (options == "Category Only" || options == "Date and Category"){
                    //Spinner for category
                    Cursor resCategory = myDb.getAllExpenseCategory();
                    if (resCategory.getCount() == 0) {
                        Log.d("myTag", "No data found");
                    }

                    ArrayList<String> CategoryExpenseList = new ArrayList<String>();
                    CategoryExpenseList.add("All");
                    while (resCategory.moveToNext()) {
                        CategoryExpenseList.add(resCategory.getString(0));
                    }

                    ArrayAdapter<String> categoryAdapter;
                    Spinner categorySpinner = (Spinner) findViewById(R.id.spnCategory);
                    categoryAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, CategoryExpenseList);
                    categorySpinner.setAdapter(categoryAdapter);

                    categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String category = parent.getItemAtPosition(position).toString();
                            //Toast.makeText(parent.getContext(), category + " chosen", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
                if (options == "Date Only" || options == "Date and Category"){
                    ArrayAdapter<String> yearAdapter;
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    String[] years = new String[24];
                    for (int i = 0; i < 24; i++) {
                        years[i] = Integer.toString(currentYear);
                        currentYear--;
                    }

                    Spinner yearSpinner = (Spinner) findViewById(R.id.spnYear);
                    yearAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, years);
                    yearSpinner.setAdapter(yearAdapter);

                    yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(parent.getContext(), "Choose month and date to refine your search", Toast.LENGTH_SHORT).show();
                            int yearItem = Integer.parseInt(parent.getItemAtPosition(position).toString());
                            if (monthSelected.getSelectedItem().toString() == "All") {
                                dateSetting(yearItem, 0);
                            } else {
                                month = Integer.parseInt(monthSelected.getSelectedItem().toString());
                                dateSetting(yearItem, month);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    //Spinner for month
                    ArrayAdapter<String> monthAdapter;
                    String[] months = new String[13];
                    months[0] = "All";
                    for (int i = 1; i <= 12; i++) {
                        months[i] = Integer.toString(i);
                    }

                    Spinner monthSpinner = (Spinner) findViewById(R.id.spnMonth);
                    monthAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, months);
                    monthSpinner.setAdapter(monthAdapter);

                    monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            year = Integer.parseInt(yearSelected.getSelectedItem().toString());
                            if (monthSelected.getSelectedItem().toString() == "All") {
                                dateSetting(year, 0);
                            } else {
                                int monthItem = Integer.parseInt(parent.getItemAtPosition(position).toString());
                                dateSetting(year, monthItem);
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ListView listshowall= (ListView) findViewById(R.id.listFilter);

        viewData();
        Log.d(TAG, "onCreate: values are"+filterOptionsSelected + "" + categorySelected + "" + yearSelected + "" + monthSelected);

        //Home Button
        Button lastbutton = (Button) findViewById(R.id.btnLast);
        lastbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowAllScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void dateSetting(int yearChosen, int monthChosen){

        ArrayAdapter<String> dayAdapter;
        String[] days1 = new String[30];
        String[] days2 = new String[31];
        String[] days3 = new String[29];
        String[] days4 = new String[32];
        days1[0] = days2[0] = days3[0] = days4[0] = "All";

        Spinner daySpinner = (Spinner) findViewById(R.id.spnDate);
        if (yearChosen%4 == 0 && monthChosen == 2){
            for (int i = 1; i <= 29; i++) {
                days1[i] = Integer.toString(i);
                dayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, days1);
                daySpinner.setAdapter(dayAdapter);
            }
        }else if(monthChosen == 4 || monthChosen == 6 || monthChosen == 9 || monthChosen == 11){
            for (int i = 1; i <= 30; i++) {
                days2[i] = Integer.toString(i);
                dayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, days2);
                daySpinner.setAdapter(dayAdapter);
            }
        }else if(monthChosen == 2){
            for (int i = 1; i <= 28; i++) {
                days3[i] = Integer.toString(i);
                dayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, days3);
                daySpinner.setAdapter(dayAdapter);
            }
        }else{
            for (int i = 1; i <= 31; i++) {
                days4[i] = Integer.toString(i);
                dayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, days4);
                daySpinner.setAdapter(dayAdapter);
            }
        }
    }

    public void viewData() {

        getDetails.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listshowall= (ListView) findViewById(R.id.listFilter);
                Cursor res = checkFilterOption();
                ArrayList<String> listItem = new ArrayList<>();
                if (res.getCount() == 0) {
                    //Toast.makeText(ShowAllScreen.this, "No expenses found", Toast.LENGTH_LONG).show();
                    showMessage("Oops", "No expenses found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    String item = new String();
                    buffer.append(res.getString(0) + "," + res.getString(1) + "," + res.getString(2) + ", " + res.getString(3) + "," + res.getString(4) + "," + res.getString(5) +"\n");
                    //item = res.getString(0) + ". " + res.getString(4) + " - " + res.getString(1) + "("
                    //        + res.getString(2) + ") on " + res.getString(5) + " by " + res.getString(3);
                    //listItem.add(item);
                }
                String[] bufferdata  = buffer.toString().split("\n");
                final ArrayList<ListItem> ListItems = new ArrayList<>();
                for(int i=0; i< bufferdata.length; i++){

                    String[] listindex  = bufferdata[i].split(",");
                    Log.d(TAG, "listindex and item are: "+bufferdata+" ");
                    ListItem item = new ListItem(listindex[0],listindex[1],listindex[2],listindex[3],listindex[4],listindex[5]); //call of constructor

                    ListItems.add(item);
                }
                ViewAllAdapter listAdapter = new ViewAllAdapter(ShowAllScreen.this, R.layout.view_all_layout,ListItems);
                listshowall.setAdapter(listAdapter);
                //adapter = new ArrayAdapter<>(ShowAllScreen.this, android.R.layout.simple_list_item_1, listItem);
                //filterList.setAdapter(adapter);
                Toast.makeText(ShowAllScreen.this, "Click on an Item to edit", Toast.LENGTH_LONG).show();
                //setOnItemClick listener to move into edit screen
                listshowall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Log.d(TAG, "onItemClick:you clicked on a list item  " + peopleList.get(position).getName());
                        //toast.makeText(SecondScreen.this, "you clicked on " + peopleList.get(position).getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ShowAllScreen.this, EditScreen.class);
                        String toeditid =  ListItems.get(position).getId();
                        String toeditcategory =  ListItems.get(position).getCategory();
                        String toeditsubcategory =  ListItems.get(position).getSubcategory();
                        String toeditamount =  ListItems.get(position).getAmount();
                        String toeditMOP = ListItems.get(position).getModeofpayment();
                        String toeditdate =  ListItems.get(position).getDate();
                        String toedit = toeditid+","+toeditcategory+","+toeditsubcategory+","+toeditMOP+","+toeditamount+","+toeditdate;
                        intent.putExtra("toeditfromshowall", toedit);
                        startActivity(intent);
                    }
                });

            }
        });
    }

    public Cursor checkFilterOption() {
        String selected = filterOptionsSelected.getSelectedItem().toString();

        switch (selected) {
            case "Show all": {
                Cursor res = myDb.getAllData();
                return res;

            }
            case "Date Only": {
                String day = daySelected.getSelectedItem().toString();
                if (monthSelected.getSelectedItem().toString() == "All"){
                    if (day == "All"){
                        Cursor res = myDb.getSelectedAllDate(year, 0);
                        return res;
                    }else{
                        int date = Integer.parseInt(daySelected.getSelectedItem().toString());
                        Cursor res = myDb.getSelectedAllDateWithDay(year, 0, date);
                        return res;
                    }
                }else {
                    int month = Integer.parseInt(monthSelected.getSelectedItem().toString());
                    int year = Integer.parseInt(yearSelected.getSelectedItem().toString());

                    Log.d(TAG, "checkFilterOption: value of day at Date Only: " + day);
                    if (day == "All") {
                        Log.d(TAG, "checkFilterOption: month and year are: " + month + " " + year);
                        Cursor res = myDb.getSelectedAllDate(year, month);
                        return res;
                    } else {
                        int date = Integer.parseInt(daySelected.getSelectedItem().toString());
                        Cursor res = myDb.getSelectedAllDateWithDay(year, month, date);
                        return res;
                    }
                }
            }
            case "Category Only": {
                String categoryS = categorySelected.getSelectedItem().toString();
                Cursor res = myDb.getSelectedCategory(categoryS);
                return res;

            }
            case "Date and Category": {
                String day = daySelected.getSelectedItem().toString();
                String categoryS = categorySelected.getSelectedItem().toString();
                if (monthSelected.getSelectedItem().toString() == "All") {
                    if (day == "All") {
                        Cursor res = myDb.getSelectedCategoryAndDate(year, 0, categoryS);
                        return res;
                    } else {
                        int date = Integer.parseInt(daySelected.getSelectedItem().toString());
                        Cursor res = myDb.getSelectedCategoryAndDateWithDay(year, 0, date, categoryS);
                        return res;
                    }
                }else {
                    int month = Integer.parseInt(monthSelected.getSelectedItem().toString());
                    int year = Integer.parseInt(yearSelected.getSelectedItem().toString());

                    if (day == "All") {
                        Cursor res = myDb.getSelectedCategoryAndDate(year, month, categoryS);
                        return res;
                    } else {
                        int date = Integer.parseInt(daySelected.getSelectedItem().toString());
                        Cursor res = myDb.getSelectedCategoryAndDateWithDay(year, month, date, categoryS);
                        return res;
                    }
                }
            }
        }
        Cursor res = myDb.getAllData();
        return res;
    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}




























        /*ListView listshowall= (ListView) findViewById(R.id.listFilter);
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            //message
            showMessage("error", "no data in table");
            return;}

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            //buffer.append("ID: " + res.getString(0) + " , Category: " + res.getString(1) + " , Subcategory: " + res.getString(2) + " , MOP: " + res.getString(3) + " , Amount: " + res.getString(4) + " , Date: " + res.getString(5) + " , Note: " + res.getString(6) + "\n");
            buffer.append(res.getString(0) + "," + res.getString(1) + "," + res.getString(2) + "," + res.getString(3) + "," + res.getString(4) + "," + res.getString(5) +" ," + res.getString(6) +"\n");
        }

        String[] bufferdata  = buffer.toString().split("\n");
        final ArrayList<ListItem> ListItems = new ArrayList<>();
        for(int i=0; i< bufferdata.length; i++){
            String[] listindex  = bufferdata[i].toString().split(",");
            ListItem item = new ListItem(listindex[0],listindex[1],listindex[2],listindex[3],listindex[4],listindex[5],listindex[6]); //call of constructor
            ListItems.add(item);
        }

        ViewAllAdapter ladapter = new ViewAllAdapter(this, R.layout.view_all_layout,ListItems);
        listshowall.setAdapter(adapter);

        listshowall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "onItemClick:you clicked on a list item  " + peopleList.get(position).getName());
                //toast.makeText(SecondScreen.this, "you clicked on " + peopleList.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShowAllScreen.this, EditScreen.class);
                String toeditid =  ListItems.get(position).getId();
                String toeditcategory =  ListItems.get(position).getCategory();
                String toeditsubcategory =  ListItems.get(position).getSubcategory();
                String toeditamount =  ListItems.get(position).getAmount();
                String toeditMOP = ListItems.get(position).getModeofpayment();
                String toeditdate =  ListItems.get(position).getDate();
                String toedit = toeditid+","+toeditcategory+","+toeditsubcategory+","+toeditMOP+","+toeditamount+","+toeditdate;
                intent.putExtra("toeditfromshowall", toedit);
                startActivity(intent);
            }
        });


        Button lastbutton = (Button) findViewById(R.id.btnLast);
        lastbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowAllScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }*/

