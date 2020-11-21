package com.example.Bachat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ShowAllExpenseFragment extends Fragment implements FilterDialog.FilterDialogListener{
    DatabaseHelper myDb;
    Button getDetails;
    Spinner filterOptionsSelected;
    Spinner categorySelected;
    Spinner modeofpaymentSelected;
    View view1;
    RecyclerView mRecyclerView;
    ShowAllAdapterRecycler mAdadpter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ListItem> ListItems;
    TextView appliedFilter;
    public static int a;
    private static final String TAG = "ShowAll";
    String[] filterTypes;
    boolean[] checkedItems;
    ArrayList<Integer> mSelectedFilters= new ArrayList<>();
    String selection = "Show All";
    public String firstDate = "";
    public String secondDate = "";
    public String category = "";
    public String modeOfPayment = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener1;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    ImageView noTransactionImageView;
    TextView noTransactionPrompt_1;
    TextView noTransactionPrompt_2;
    private FloatingActionButton filter2;
    public int modePos = 0;
    public int catPos = 0;
    public String fdate = "Start Date";
    public String ldate = "End Date";

    /*@Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("startingDate", ldate);
        Log.d(TAG, "onSaveInstanceState: bundle in showall expense " + outState);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myDb = new DatabaseHelper(getActivity());
        view1 = inflater.inflate(R.layout.show_all_screen_fragment, container, false);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.show_all_screen);
        Log.d(TAG, "onCreate: created ViewAll screen");

        /*if(savedInstanceState != null) {
            Log.d(TAG, "Hakunamatata inside savedInstance if loop");
            filter2.setText(savedInstanceState.getString("startingDate", "Start date"));
        }*/

        //Initialising and linking resources for filtering functionality
        appliedFilter = (TextView) view1.findViewById(R.id.appliedFilter);
        mRecyclerView = view1.findViewById(R.id.recyclerlistFilter);
        filter2 =  view1.findViewById(R.id.btnFilters);
        noTransactionImageView = view1.findViewById(R.id.action_image);
        noTransactionPrompt_1 = view1.findViewById(R.id.prompt_no_transactions);
        noTransactionPrompt_2 = view1.findViewById(R.id.prompt_no_transactions_2);
        noTransactionImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.notransaction));
        noTransactionPrompt_1.setText("Oops! You have no transactions");
        noTransactionPrompt_2.setText("Add transactions on the Home screen");



        filter2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Opening the dialog: ");

                Bundle bundle = new Bundle();
                bundle.putInt("mopPosition", modePos);
                bundle.putInt("catPosition", catPos);
                bundle.putString("startingDate", fdate);
                bundle.putString("endingDate", ldate);

                Log.d(TAG, "onClick: Bundle value is "+ bundle);

                FilterDialog dialog = new FilterDialog();
                dialog.setArguments(bundle);
                dialog.setTargetFragment(ShowAllExpenseFragment.this,1);
                dialog.show(getParentFragmentManager(),"FilterDialog");


        }
        });

        viewData(view1);
        return view1;
    }

    public void openDialog() {
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.show(getActivity().getSupportFragmentManager(),"Filter Data");

    }

    @Override
    public void applyTexts(String categoryChosen, String mopChosen, String startDateChosen, String endDateChosen,
                           int mop, int cat, String startDate, String endDate) {

        category = categoryChosen;
        modeOfPayment = mopChosen;
        firstDate = startDateChosen;
        secondDate = endDateChosen;
        modePos = mop;
        catPos = cat;
        fdate = startDate;
        ldate = endDate;

        Log.d(TAG, "applyTexts: " + category + " " + modeOfPayment + " " + firstDate + " " + secondDate + " " );
        ArrayList<String> item = new ArrayList<String>();
        if (!category.equals("")){
            item.add("Category");
        }
        if (!modeOfPayment.equals("")){
            item.add("Mode of Payment");
        }
        if (!firstDate.equals("") && !secondDate.equals("")){
            item.add("Date");
        }
        Log.d(TAG, "applyTexts: values of first date and second date are: "+firstDate + " " + secondDate);
        appliedFilter.setText("Active filters: " + item);
        viewData(view1);
    }

    public void viewData(final View view) {

                Cursor res = checkFilterOption();
                Log.d(TAG, "inside viewData value of res: "+ res);

                ArrayList<String> listItem = new ArrayList<>();
                if (res.getCount() == 0) {
                    //Toast.makeText(ShowAllScreen.this, "No expenses found", Toast.LENGTH_LONG).show();
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(" , , , , , , , , , , , " + "\n");
                    String[] bufferdata = buffer.toString().split("\n");
                    ListItems = new ArrayList<>();
                    for (int i = 0; i < bufferdata.length; i++) {

                        String[] listindex = bufferdata[i].split(",");
                        Log.d(TAG, "listindex and item are: " + bufferdata + " ");
                        ListItem item = new ListItem(listindex[0], listindex[1], listindex[2], listindex[3], listindex[4], listindex[5],
                                listindex[6],listindex[7], listindex[8], listindex[9], listindex[10]); //call of constructor

                        ListItems.add(item);
                    }
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    noTransactionImageView.setVisibility(View.VISIBLE);
                    noTransactionPrompt_1.setVisibility(View.VISIBLE);
                    noTransactionPrompt_2.setVisibility(View.VISIBLE);
                    //showMessage("Oops", "No expenses found");
                    return;
                }
                    /*mAdadpter = new ShowAllAdapterRecycler(ListItems);
                    TextView textEmptyView;
                    //textEmptyView = view1.findViewById(R.id.listFilterEmpty);

                    mLayoutManager= new LinearLayoutManager(getActivity());
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setAdapter(mAdadpter);
                    //showMessage("Oops", "No expenses found");
                    return;*/
                else {
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        String item = new String();
                        buffer.append(res.getString(0) + "," + res.getString(1) + "," + res.getString(2) + ", " +
                                res.getString(3) + "," + res.getString(4) + "," + res.getString(5) + ", " +
                                res.getString(6) + "," + res.getString(7) + "," + res.getString(8) + ", " +
                                res.getString(9) + ", " + res.getString(10) +"\n");
                    }
                    String[] bufferdata = buffer.toString().split("\n");
                    ListItems = new ArrayList<>();
                    for (int i = 0; i < bufferdata.length; i++) {

                        String[] listindex = bufferdata[i].split(",");
                        Log.d(TAG, "listindex and item are: " + bufferdata + " ");
                        Log.d(TAG, "viewData: buffer: " + buffer.toString());
                        ListItem item = new ListItem(listindex[0], listindex[1], listindex[2], listindex[3], listindex[4], listindex[5],
                                listindex[6],listindex[7], listindex[8]); //call of constructor

                        ListItems.add(item);
                    }
                    //ShowAllAdapterList listAdapter = new ShowAllAdapterList(getActivity(), R.layout.show_all_recycler_item_layout,ListItems);
                    //listshowall.setAdapter(listAdapter);
                    //mRecyclerView = view1.findViewById(R.id.recyclerlistFilter);
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    mAdadpter = new ShowAllAdapterRecycler(ListItems);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setAdapter(mAdadpter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    noTransactionImageView.setVisibility(View.INVISIBLE);
                    noTransactionPrompt_1.setVisibility(View.INVISIBLE);
                    noTransactionPrompt_2.setVisibility(View.INVISIBLE);
                }

                mAdadpter.setOnItemClickListener(new ShowAllAdapterRecycler.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        Intent intent = new Intent(getActivity(), EditUpdateScreen.class);
                        String toeditid =  ListItems.get(position).getId();
                        String toeditcategory =  ListItems.get(position).getCategory();
                        String toeditsubcategory =  ListItems.get(position).getSubcategory();
                        String toeditMOP = ListItems.get(position).getModeofpayment();
                        String toeditamount =  ListItems.get(position).getAmount();
                        String toeditdate =  ListItems.get(position).getDate();
                        String toeditnote =  ListItems.get(position).getCurrency();
                        String toeditcurr =  ListItems.get(position).getCurrency();
                        String toeditname =  ListItems.get(position).getCurrency();
                        String toeditphone =  ListItems.get(position).getCurrency();
                        String toeditemail =  ListItems.get(position).getCurrency();
                        String toedit = toeditid+","+toeditcategory+","+toeditsubcategory+","+toeditMOP+","+toeditamount+","+toeditdate +","+toeditcurr;

                        intent.putExtra("toeditfromshowall", toedit);
                        startActivity(intent);

                    }
                });

                //creating a touch helper to handle the touch on the card views

                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                itemTouchHelper.attachToRecyclerView(mRecyclerView);

    }
    //creating a call back function for the item touch helper object.

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            switch(direction){
                case ItemTouchHelper.LEFT:
                    String toremove= ListItems.get(position).getId();
                    Log.d(TAG, "onSwiped: to remove Db id is  " + toremove);
                    mAdadpter.notifyDataSetChanged();
                    ListItems.remove(position);
                    if(ListItems.isEmpty()){
                        mRecyclerView.setVisibility(View.INVISIBLE);
                        noTransactionImageView.setVisibility(View.VISIBLE);
                        noTransactionPrompt_1.setVisibility(View.VISIBLE);
                        noTransactionPrompt_2.setVisibility(View.VISIBLE);
                    }
                    DeleteData(toremove);
                    break;
                case ItemTouchHelper.RIGHT:
                Intent intentr = new Intent(getActivity(), EditRepeatScreen.class);
                String toeditidr =  ListItems.get(position).getId();
                String toeditcategoryr =  ListItems.get(position).getCategory();
                String toeditsubcategoryr =  ListItems.get(position).getSubcategory();
                String toeditamountr =  ListItems.get(position).getAmount();
                String toeditMOPr = ListItems.get(position).getModeofpayment();
                String toeditdater =  ListItems.get(position).getDate();
                String toeditr = toeditidr+","+toeditcategoryr+","+toeditsubcategoryr+","+toeditMOPr+","+toeditamountr+","+toeditdater;

                intentr.putExtra("toeditfromshowall", toeditr);
                startActivity(intentr);
            }
        }
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(getActivity(), c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_sweep_black)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.SwipeDelete))
                    .addSwipeLeftLabel("Delete").setSwipeLeftLabelColor(R.color.PrimaryText).setSwipeLeftLabelTextSize(TypedValue.COMPLEX_UNIT_SP,18)
                    .addSwipeRightActionIcon(R.drawable.ic_repeat_black)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(), R.color.SwipeRepeat))
                    .addSwipeRightLabel("Repeat").setSwipeRightLabelColor(R.color.Black).setSwipeRightLabelTextSize(TypedValue.COMPLEX_UNIT_SP,18)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public Cursor checkFilterOption() {

        Log.d(TAG, "checkFilterOption: inside checkFilterOptions" );
        if(category.equals("") && modeOfPayment.equals("") && firstDate.equals("") && secondDate.equals("")){
            Log.d(TAG, "checkFilterOption: values are "  + category + " " + modeOfPayment + " " + firstDate + " " + secondDate + " ");
            Cursor res = myDb.getAllData();
            appliedFilter.setText("No Active Filters" );
            return res;
        }else
        {
            Cursor res = myDb.getExpenseFiltered(category, modeOfPayment, firstDate, secondDate);
            Log.d(TAG, "checkFilterOption: values are "  + category + " " + modeOfPayment + " " + firstDate + " " + secondDate + " and res is " + res);
            return res;

        }
    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    //calling method ti delete data from swipe action in card view
    public void DeleteData(String id) {
        Integer deletedRows = myDb.deleteData(id);
        Log.d(TAG, "delete id is : "+id);
        if(deletedRows > 0) {
            Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(),"Data not Deleted",Toast.LENGTH_LONG).show();
        }
    }
}