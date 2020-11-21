package com.example.Bachat;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    //private onFragmentBtnSelected listener;
    //private onFragmentBtnSelected listener2;
    private static final String TAG = "HomeFragment";
    Button imageButton;
    Button imageButton2;
    ImageButton profileButton;
    Button test_button_2;
    Button test_button;
    TextView profileName;
    DatabaseHelper myDb;
    public static String username;
    public static String current_profile;
    public static String default_currency;
    public static ArrayList<String> ProfileList;
    LinearLayout linearLayout;
    GraphInflater graphInflater;
    /*PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    ArrayList PieEntryLabels;*/


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "home fragment Start");
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        myDb = new DatabaseHelper(getActivity());
        Calendar currentTime = Calendar.getInstance();
        Log.d("Time zone", "= " + currentTime.HOUR + " " + currentTime.AM);
        Log.d(TAG, "onCreateView: Calendar Testing" + currentTime.DAY_OF_MONTH);
        String time = String.valueOf(currentTime.getTime());
        Log.d(TAG, "onCreateView: " + time);
        String compare[] = time.split(" ");
        Log.d(TAG, "onCreateView: " + compare[3]);
        String hour[] = compare[3].split(":");
        int tocheck = Integer.parseInt(hour[0]);
        Log.d(TAG, "onCreateView: " + hour[0]);
        Cursor res = myDb.getAllDataUser();
        StringBuffer sb = new StringBuffer();
        while (res.moveToNext()) {
                sb.append(res.getString(0));
        }
        username = sb.toString();

        Cursor profile = myDb.getCurrentProfiles();
        StringBuffer sb2 = new StringBuffer();
        Log.d(TAG, "onCreateView: " + sb2.toString());
        while (profile.moveToNext()) {
            sb2.append(profile.getString(0));
        }


        current_profile = sb2.toString();

        if(savedInstanceState==null){
            Bundle bundle = this.getArguments();
            Log.d(TAG, "Home:Bundle Saved");
            if(bundle != null){
                String Profile = bundle.getString("Profile");
                current_profile = Profile;
                Log.d(TAG, "Home:Bundle Not Null ");
            }
        }



        Cursor curr = myDb.getDefaultCurrency();
        StringBuffer currBuffer= new StringBuffer();
        Log.d(TAG, "onCreateView: "+ currBuffer.toString());
        while (curr.moveToNext()) {
            currBuffer.append(curr.getString(0));
        }

        default_currency = currBuffer.toString();
        Log.d(TAG, "HomeFragment: default currency: " + default_currency);

            //Toast.makeText(getActivity(), current_profile, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "User: " + username);

            TextView greetings = (TextView) view.findViewById(R.id.greetings);
            if (tocheck >= 5 && tocheck < 12) {
                greetings.setText("Good Morning " + username + " !!");
            } else if (tocheck >= 12 && tocheck < 18) {
                greetings.setText("Good Afternoon " + username + " !!");
            } else if (tocheck >= 18 && tocheck < 24) {
                greetings.setText("Good Evening " + username + " !!");
            } else {
                greetings.setText("Night Night " + username + " !!");
            }

            imageButton = view.findViewById(R.id.Add_Income);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listener.onButtonSelected();
                    /*Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Adding Income", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();*/
                    Intent intent = new Intent(getActivity(), EarningSecondScreen.class);
                    startActivity(intent);
                }
            });
            imageButton2 = view.findViewById(R.id.Add_Expense);
            imageButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //listener.onButtonSelected();
                    /*Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Adding Expense", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();*/
                    Intent intent = new Intent(getActivity(), SecondScreen.class);
                    startActivity(intent);
                }
            });

            profileButton = view.findViewById(R.id.profileButton);

            profileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddEditProfileScreen.class);
                    startActivity(intent);
                }
            });


            profileName = view.findViewById(R.id.profileName);
            profileName.setText(current_profile);

        //Creating an ArrayList to populate the profiles from the database
        ProfileList = new ArrayList<String>();
        Cursor resProfile = myDb.getAllProfiles();
        while (resProfile.moveToNext()) {
            ProfileList.add(resProfile.getString(1));
            Log.d("myTag", "reached while loop");
        }

            //Graph View
        //Incase

            //Ends

        //Graph Call Default
        //linearLayout = view.findViewById(R.id.childRelLayout);
        //graphInflater = new GraphInflater();
        getChildFragmentManager().beginTransaction().replace(R.id.container_fragment, new GraphInflater()).commit();

            /*Spinner profileSpinner = view.findViewById(R.id.profileSpinner);





            ArrayAdapter<String> profileAdapter;
            profileAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ProfileList);
            //categoryAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            profileSpinner.setAdapter(profileAdapter);

            String compareValue = HomeFragment.current_profile;
            if (compareValue != null) {
                int spinnerPosition = profileAdapter.getPosition(compareValue);
                profileSpinner.setSelection(spinnerPosition);}

            profileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String setProfile = parent.getItemAtPosition(position).toString();
                    myDb.setCurrentProfiles(setProfile);
                    HomeFragment.current_profile=setProfile;
                    Toast.makeText(getActivity(),""+HomeFragment.current_profile,Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "onClick: the changed current profile is " + HomeFragment.current_profile);
                    getChildFragmentManager().beginTransaction().replace(R.id.container_fragment, new GraphInflater()).commit();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });*/
            return view;
        }
}
