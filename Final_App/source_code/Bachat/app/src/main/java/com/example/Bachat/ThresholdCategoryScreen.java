/*package com.example.Bachat;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.GridView;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import java.util.ArrayList;

public class ThresholdCategoryScreen extends AppCompatActivity {

    private static final String TAG = "ThresholdCategoryScreen";
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        Log.d(TAG, "onCreate:Started Threshold Category Screen");


        //ListView mListView = (ListView) findViewById(R.id.theGrid);
        GridView mListView = (GridView) findViewById(R.id.theGrid);



        Category category1 = new Category("Health", "drawable://" + R.drawable.healthcare);
        Category category2 = new Category("Donations", "drawable://" + R.drawable.donations);
        Category category3 = new Category("Bills", "drawable://" + R.drawable.billsicon);
        Category category4 = new Category("Shopping", "drawable://" + R.drawable.shoppingicon);
        Category category5 = new Category("Dining Out", "drawable://" + R.drawable.dinningout);
        Category category6 = new Category("Entertainment", "drawable://" + R.drawable.entertaimenticon);;
        Category category7= new Category("Groceries", "drawable://" + R.drawable.groceries_icon);
        Category category8 = new Category("Pet", "drawable://" + R.drawable.petsicon);
        Category category9 = new Category("Transport", "drawable://" + R.drawable.transportation);
        Category category10 = new Category("Loans", "drawable://" + R.drawable.loans);
        Category category11 = new Category("Personal Care", "drawable://" + R.drawable.personalcareicon);
        Category category12= new Category("Miscellaneous", "drawable://" + R.drawable.miscellaneousicon);



        //Add the Person objects to an ArrayList
        final ArrayList<Category> thresholdCategoryList = new ArrayList<>();
        thresholdCategoryList.add(category1);
        thresholdCategoryList.add(category3);
        thresholdCategoryList.add(category2);
        thresholdCategoryList.add(category4);
        thresholdCategoryList.add(category5);
        thresholdCategoryList.add(category6);
        thresholdCategoryList.add(category7);
        thresholdCategoryList.add(category8);
        thresholdCategoryList.add(category9);
        thresholdCategoryList.add(category10);
        thresholdCategoryList.add(category11);
        thresholdCategoryList.add(category12);
        thresholdCategoryList.add(category12);
        thresholdCategoryList.add(category12);


        CategoryListAdapter adapter = new CategoryListAdapter(this, R.layout.adapter_view_layout, thresholdCategoryList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d(TAG, "onItemClick:you clicked on a list item  " + peopleList.get(position).getName());
                //toast.makeText(SecondScreen.this, "you clicked on " + peopleList.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ThresholdCategoryScreen.this, EditThresholdScreen.class);
                String toadd =  thresholdCategoryList.get(position).getName();
                intent.putExtra("fromThresholdCategory",toadd);
                startActivity(intent);
            }
        });
    }
}
*/




