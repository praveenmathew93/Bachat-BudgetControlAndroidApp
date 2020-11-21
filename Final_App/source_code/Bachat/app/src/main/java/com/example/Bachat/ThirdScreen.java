package com.example.Bachat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Rectangle;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ThirdScreen extends AppCompatActivity  {
    private static final String TAG = "ThirdScreen";
    public String fromSecond,addedSubCategory;
    public String toadd;
    Context context;
    TextView subTitle;
    ImageView edit;
    EditText search_bar;
    CustomAdapter listAdapter;
    EditText subcategoryname;

    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout_2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(getCustomTheme(getIntent().getStringExtra("fromSecond")));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_screen);
        search_bar = findViewById(R.id.search_bar);
        subcategoryname = findViewById(R.id.editText);

        setEditTextCursorColor(subcategoryname,getResources().getColor(getColor(getIntent().getStringExtra("fromSecond")),getTheme()));


        Drawable drawable_edit = getDrawable(R.drawable.ic_pencil);
        drawable_edit = DrawableCompat.wrap(drawable_edit);
        DrawableCompat.setTint(drawable_edit, getResources().getColor(getColor(getIntent().getStringExtra("fromSecond")),getTheme()));
        DrawableCompat.setTintMode(drawable_edit, PorterDuff.Mode.SRC_IN);

        subcategoryname.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable_edit,null,null,null);



        //edit.setColorFilter(getResources().getColor(getColor(getIntent().getStringExtra("fromSecond")),getTheme()), PorterDuff.Mode.SRC_IN);
        relativeLayout=findViewById(R.id.relLayout_1);
        relativeLayout_2=findViewById(R.id.relLayout_2);


        relativeLayout.setBackgroundColor(getColor(getColor(getIntent().getStringExtra("fromSecond"))));
        relativeLayout_2.setBackgroundColor(getColor(getColor(getIntent().getStringExtra("fromSecond"))));

        //Reminder to the user of which Category was chosen by him in previous page
        String categoryToShow = getIntent().getStringExtra("fromSecond");
        /*TextView categoryView = (TextView) findViewById(R.id.textCategory);
        categoryView.setText(categoryToShow);*/

        /*getSupportActionBar().setTitle("Expense");
        getSupportActionBar().setSubtitle("Add/Choose Sub-Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        //Log.d(TAG, "onCreate: "+ categoryToShow);
        //Opening the database to capture the category
        final String selectedCategory = getIntent().getStringExtra("fromSecond");
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        subTitle = findViewById(R.id.toolbar_sub_title);

        subTitle.setText(categoryToShow + ": Add/Choose Sub-Category");







        //Creating a ListView to populate with data from DB
        final ListView listView = (ListView) findViewById(R.id.subCatList);

        // Creating an Arraylist to populate from the database with the list of sub-categories related to the selectedCategory
        final ArrayList<String> subCategoryList = new ArrayList<>();
        Cursor cursor = databaseAccess.getListContents(selectedCategory);

        if(cursor.getCount()==0){
            Toast.makeText(ThirdScreen.this,"No sub-categories yet for this category",Toast.LENGTH_LONG).show();
        }else{
            while (cursor.moveToNext()){
                subCategoryList.add(cursor.getString(0));
                listAdapter = new CustomAdapter(subCategoryList,this);
                listView.setAdapter(listAdapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ThirdScreen.this, FourthScreen.class);
                String subcategoryname = subCategoryList.get(position);
                fromSecond = getIntent().getStringExtra("fromSecond");
                String toadd = fromSecond + "," + subcategoryname;
                addedSubCategory = subcategoryname;
                Log.d(TAG, "onClick: value of added subcategory: "+ addedSubCategory);
                intent.putExtra("fromThird",toadd);
                intent.putExtra("fromThird_Category",fromSecond);
                startActivity(intent);
                Log.d(TAG, "onItemClick: name is "+ subCategoryList.get(position));
                //Toast.makeText(ThirdScreen.this,"You have chosen "+ subCategoryList.get(position),Toast.LENGTH_SHORT).show();
            }
        });



        CharSequence hint= "Add Sub-Category to " + getIntent().getStringExtra("fromSecond") ;
        //subcategoryname.setHint(hint);


        Button thirdButton = (Button) findViewById(R.id.btnGoToFourth);
        thirdButton.setTextColor(getResources().getColor(getColor(getIntent().getStringExtra("fromSecond")),getTheme()));
        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdScreen.this, FourthScreen.class);
                /*TextView theTextView = (TextView) findViewById(R.id.textAddSubcat);*/
                fromSecond = getIntent().getStringExtra("fromSecond");
                int flag = 0;
                //EditText subcategoryname = (EditText) findViewById(R.id.editText);
                addedSubCategory = subcategoryname.getText().toString();
                Log.d(TAG, "onClick: subcategoryname is "+flag);
                Log.d(TAG, "onClick: subcategoryname is "+ addedSubCategory);
                toadd = fromSecond + "," + subcategoryname.getText().toString();

                if (addedSubCategory.isEmpty())
                {
                   //databaseAccess.insertListContents(selectedCategory,"None");
                    Toast.makeText(ThirdScreen.this, "Please mention a Sub-Category", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseAccess.insertListContents(selectedCategory,addedSubCategory);
                    databaseAccess.close();

                    Log.d(TAG, "onClick: passed value is: "+ toadd );
                    intent.putExtra("fromThird",toadd);
                    intent.putExtra("fromThird_Category",fromSecond);
                    startActivity(intent);
                }

            }
        });
        Log.d(TAG, "onCreate: insertListContents: the values being passed to fourth are" + selectedCategory + " and " + addedSubCategory);
        //close connection after getting result

        FloatingActionButton fab_home= (FloatingActionButton)  findViewById(R.id.fab_home);
        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdScreen.this, MainActivity.class);
                startActivity(intent);

            }
        });

        ImageButton back = (ImageButton) findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdScreen.this, SecondScreen.class);
                startActivity(intent);
            }
        });


        Drawable drawable = getDrawable(R.drawable.ic_search);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, getResources().getColor(getColor(getIntent().getStringExtra("fromSecond")),getTheme()));
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        search_bar.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,null,null,null);

        //search_bar.setBackground(null);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(3,getResources().getColor(getColor(getIntent().getStringExtra("fromSecond")),getTheme()));
        gradientDrawable.setCornerRadius(50);
        search_bar.setBackground(gradientDrawable);

        setEditTextCursorColor(search_bar,getResources().getColor(getColor(getIntent().getStringExtra("fromSecond")),getTheme()));
        /*try {
            EditTextTint.applyColor(search_bar, getResources().getColor(getColor(getIntent().getStringExtra("fromSecond")),getTheme()));
        } catch (EditTextTint.EditTextTintError e) {
            e.printStackTrace();
        }*/

        if(subCategoryList.size()<=0){
            search_bar.setVisibility(View.INVISIBLE);
        }



        search_bar.setHighlightColor(getResources().getColor(getColor(getIntent().getStringExtra("fromSecond")),getTheme()));
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(subCategoryList.size()>0)(ThirdScreen.this).listAdapter.getFilter().filter(charSequence);
                else {
                    search_bar.setEnabled(false);
                    search_bar.setFocusable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private class CustomAdapter extends BaseAdapter implements Filterable {
        private ArrayList<String> subCategoryList;
        private ArrayList<String> subCategoryListFiltered;
        private Context context;

        public CustomAdapter(ArrayList<String> subCategoryList, Context context) {
            this.subCategoryList = subCategoryList;
            this.subCategoryListFiltered = subCategoryList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return subCategoryListFiltered.size();
        }

        @Override
        public String getItem(int position) {
            return subCategoryListFiltered.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            @SuppressLint({"ViewHolder", "InflateParams"}) View view = getLayoutInflater().inflate(R.layout.subcategory_list_adapter,null);

            TextView subcategory_text = view.findViewById(R.id.subcategory);
            Log.d(TAG, "getView: "+ subCategoryListFiltered.get(position));

            subcategory_text.setText(subCategoryListFiltered.get(position));


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fromSecond = getIntent().getStringExtra("fromSecond");
                    String toadd = fromSecond + "," + subCategoryListFiltered.get(position);
                    Intent intent = new Intent(ThirdScreen.this, FourthScreen.class);
                    intent.putExtra("fromThird",toadd);
                    intent.putExtra("fromThird_Category",fromSecond);
                    startActivity(intent);
                }
            });

            return view;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if(constraint.length() == 0 || constraint==null){
                        filterResults.count = subCategoryList.size();
                        filterResults.values = subCategoryList;
                    }else{
                        String searchString = constraint.toString().toUpperCase();
                        ArrayList<String> resultData = new ArrayList<>();
                        for(String subcategorystring:subCategoryList){
                            if(subcategorystring.toUpperCase().contains(searchString)){
                                resultData.add(subcategorystring);
                            }

                            filterResults.count = resultData.size();
                            filterResults.values = resultData;
                        }
                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    subCategoryListFiltered = (ArrayList<String>) results.values;
                    notifyDataSetChanged();

                }
            };
            return filter;
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


    private int getCustomTheme(String Category){
        switch (Category) {
            case "Health":
                return R.style.HealthCare;
            case "Donations":
                return R.style.Donations;
            case "Bills":
                return R.style.Bills;
            case "Shopping":
                return R.style.Shopping;
            case "Dining Out":
                return  R.style.Dinning;
            case "Entertainment":
                return  R.style.Entertainment;
            case "Groceries":
                return R.style.Groceries;
            case "Pet Care":
                return R.style.PetCare;
            case "Transportation":
                return R.style.Transportation;
            case "Loans":
                return R.style.Loans;
            case "Personal Care":
                return R.style.PersonalCare;
            case "Miscellaneous":
                return R.style.Miscellaneous;
            default:
                return R.style.HealthCare;
        }
    }

    public static void setEditTextCursorColor(EditText editText, int color) {
        try {
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(editText);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(editText);

            final Field fSelectHandleLeft = editor.getClass().getDeclaredField("mSelectHandleLeft");
            final Field fSelectHandleRight = editor.getClass().getDeclaredField("mSelectHandleRight");
            final Field fSelectHandleCenter = editor.getClass().getDeclaredField("mSelectHandleCenter");
            fSelectHandleLeft.setAccessible(true);
            fSelectHandleRight.setAccessible(true);
            fSelectHandleCenter.setAccessible(true);



            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(editText.getContext(), drawableResId);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);


            // Set the drawables
            if(Build.VERSION.SDK_INT >= 28){//set differently in Android P (API 28)
                field = editor.getClass().getDeclaredField("mDrawableForCursor");
                field.setAccessible(true);
                field.set(editor, drawable);
            }else {
                Drawable[] drawables = {drawable, drawable};
                field = editor.getClass().getDeclaredField("mCursorDrawable");
                field.setAccessible(true);
                field.set(editor, drawables);
            }

            //optionally set the "selection handle" color too
            //setEditTextHandleColor(editText, color);
        } catch (Exception ignored) {}

    }


}

