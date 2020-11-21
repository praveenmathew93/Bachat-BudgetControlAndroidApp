package com.example.Bachat;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class AddEditProfileScreen extends AppCompatActivity {
    Button addProfile;
    EditText addProfileText;
    RecyclerView mRecyclerView;
    ProfileListAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DatabaseHelper myDb;
    ArrayList<String> ProfileList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_profile);
        addProfile = findViewById(R.id.addProfile2);
        addProfileText = findViewById(R.id.addProfile);
        mRecyclerView = findViewById(R.id.listView);
        myDb = new DatabaseHelper(this);

        Cursor resProfile = myDb.getAllProfiles();

        //Creating an ArrayList to populate the profiles from the database
        ProfileList = new ArrayList<>();
        while (resProfile.moveToNext()) {
            ProfileList.add(resProfile.getString(1));
            Log.d("myTag", "reached while loop");
        }




        addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProfileText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(addProfileText, InputMethodManager.SHOW_IMPLICIT);
                String profile = addProfileText.getText().toString().trim();
                boolean check = true;
                for (int i = 0; i < HomeFragment.ProfileList.size(); i++) {
                    if (profile.equalsIgnoreCase(HomeFragment.ProfileList.get(i))) {
                        check = false;
                    }
                }
                if (profile.trim().isEmpty()) {
                    Toast.makeText(AddEditProfileScreen.this, "Please enter profile name", Toast.LENGTH_SHORT).show();
                }
                else if(profile.length()>8){
                    Toast.makeText(AddEditProfileScreen.this, "Profile cannot have more than 8 Characters", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (check) {
                        myDb.insertProfile(profile);
                        myDb.setCurrentProfiles(profile);
                        Intent intent = new Intent(AddEditProfileScreen.this, MainActivity.class);
                        intent.putExtra("AddedProfile",profile);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getBaseContext(), "profile with same name already exists", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
        );
        
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ProfileListAdapter(ProfileList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        
        mAdapter.setOnItemClickListener(new ProfileListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String setProfile = ProfileList.get(position);
                myDb.setCurrentProfiles(setProfile);
                HomeFragment.current_profile=setProfile;
                Intent intent = new Intent(AddEditProfileScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //creating a touch helper to handle the touch on the card views

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        
        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEditProfileScreen.this, MainActivity.class);
                intent.putExtra("open_settings_screen", "Open Setting View");
                startActivity(intent);
            }
        });

    }

    //creating a call back function for the item touch helper object.

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            switch(direction){
                case ItemTouchHelper.LEFT:
                    String toremove= ProfileList.get(position);
                    Log.d("swipe", "onSwiped: to remove Db id is  " + toremove);
                    if(!toremove.equalsIgnoreCase("Default")){
                        Log.d( "todelete ", " " + toremove);
                        int value = DeleteData(toremove);
                        if(value == 100){
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(AddEditProfileScreen.this, "The selected profile is set as current, please select another profile before deleting this", Toast.LENGTH_LONG).show();
                        }
                        else if (value == -1){
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(AddEditProfileScreen.this,"Data not Deleted due to technical error, sorry",Toast.LENGTH_LONG).show();
                        }
                        else{
                            ProfileList.remove(position);
                            if(ProfileList.isEmpty()){
                                mRecyclerView.setVisibility(View.INVISIBLE);
                            }
                            Toast.makeText(AddEditProfileScreen.this, "Data Deleted", Toast.LENGTH_LONG).show();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    else{
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(AddEditProfileScreen.this,"cannot delete profile Default",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case ItemTouchHelper.RIGHT:
                    Intent intentr = new Intent(AddEditProfileScreen.this, EditRepeatScreen.class);
                    String toedit =  ProfileList.get(position);
                    intentr.putExtra("toeditfromprofile", toedit);
                    startActivity(intentr);
            }
        }
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(AddEditProfileScreen.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_sweep_black)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(AddEditProfileScreen.this, R.color.SwipeDelete))
                    .addSwipeLeftLabel("Delete").setSwipeLeftLabelColor(R.color.PrimaryText).setSwipeLeftLabelTextSize(TypedValue.COMPLEX_UNIT_SP,18)
                    .addSwipeRightActionIcon(R.drawable.ic_repeat_black)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(AddEditProfileScreen.this, R.color.SwipeRepeat))
                    .addSwipeRightLabel("Repeat").setSwipeRightLabelColor(R.color.Black).setSwipeRightLabelTextSize(TypedValue.COMPLEX_UNIT_SP,18)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        //calling method ti delete data from swipe action in card view
        public int DeleteData(String profile_name) {
            //Toast.makeText(AddEditProfileScreen.this, "Deletion", Toast.LENGTH_SHORT).show();
            int value=0;

            String deletedRows = myDb.deleteProfile(profile_name);
            //Log.d(TAG, "delete id is : ");
            if(deletedRows.equalsIgnoreCase("deleted")) {
                value=1;

            }
            else if(deletedRows.equalsIgnoreCase("error in deletion")) {
                value  = -1;

            }
            else{
                value = 100;

            }
            return value;
        }

    };

}
