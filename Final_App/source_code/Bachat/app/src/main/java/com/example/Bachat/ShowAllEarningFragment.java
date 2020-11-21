package com.example.Bachat;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import android.support.v4.app.Fragment;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ShowAllEarningFragment extends Fragment implements FilterEarningDialog.FilterDialogListener{
    DatabaseHelper myDb;
    Spinner monthSelected;
    Spinner yearSelected;
    Spinner filterOptionsSelected;
    TextView appliedFilter;
    Spinner categorySelected;
    View view1;
    private FloatingActionButton filter2;
    RecyclerView mRecyclerView;
    ShowAllAdapterEarningRecycler mAdadpter;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<EarningListItem> EarningListItems;
    ImageView noTransactionImageView;
    TextView noTransactionPrompt_1;
    TextView noTransactionPrompt_2;
    public String firstDate = "";
    public String secondDate = "";
    public String category = "";
    public String modeOfPayment = "";
    public int modePos = 0;
    public int catPos = 0;
    public String fdate = "Start Date";
    public String ldate = "End Date";
    private static final String TAG = "EarningShowAll";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myDb = new DatabaseHelper(getActivity());
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.show_all_screen);
        view1 = inflater.inflate(R.layout.show_all_screen_fragment, container, false);
        Log.d(TAG, "onCreate: created Earning Show All screen");
        mRecyclerView = (RecyclerView) view1.findViewById(R.id.recyclerlistFilter);
        noTransactionImageView = view1.findViewById(R.id.action_image);
        noTransactionPrompt_1 = view1.findViewById(R.id.prompt_no_transactions);
        noTransactionPrompt_2 = view1.findViewById(R.id.prompt_no_transactions_2);
        noTransactionImageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.notransaction));
        noTransactionPrompt_1.setText("Oops! You have no transactions");
        noTransactionPrompt_2.setText("Add transactions on the Home screen");


        //Initialising and linking resources for filtering functionality
        appliedFilter = (TextView) view1.findViewById(R.id.appliedFilter);
        filter2 = (FloatingActionButton) view1.findViewById(R.id.btnFilters);

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

                FilterEarningDialog dialog = new FilterEarningDialog();
                dialog.setArguments(bundle);
                dialog.setTargetFragment(ShowAllEarningFragment.this,1);
                dialog.show(getParentFragmentManager(),"FilterEarningDialog");
            }
        });

        Log.d(TAG, "onCreate: values are" + filterOptionsSelected + "" + categorySelected + "" + yearSelected + "" + monthSelected);
        viewData(view1);
        return view1;
    }


    public void callParentMethod(){
        Log.d(TAG, "callParentMethod: inside back call");
        getActivity().onBackPressed();
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
        ArrayList<String> listItem = new ArrayList<>();
        if (res.getCount() == 0) {
            //Toast.makeText(ShowAllScreen.this, "No expenses found", Toast.LENGTH_LONG).show();
            StringBuffer buffer = new StringBuffer();
            buffer.append(" , , , , , , , , , , , "+"\n");
            String[] bufferdata  = buffer.toString().split("\n");
            EarningListItems = new ArrayList<>();
            for(int i=0; i< bufferdata.length; i++){

                String[] listindex  = bufferdata[i].split(",");
                Log.d(TAG, "listindex and item are: "+bufferdata+" ");
                EarningListItem item = new EarningListItem(listindex[0],listindex[1],listindex[2],listindex[3],listindex[4],listindex[5],listindex[6],listindex[7]); //call of constructor
                EarningListItems.add(item);
            }
            mRecyclerView.setVisibility(View.INVISIBLE);

            noTransactionImageView.setVisibility(View.VISIBLE);
            noTransactionPrompt_1.setVisibility(View.VISIBLE);
            noTransactionPrompt_2.setVisibility(View.VISIBLE);


            //mAdadpter = new ShowAllAdapterEarningRecycler(EarningListItems);
            //textEmptyView = view1.findViewById(R.id.listFilterEmpty);
            //textEmptyView.setText("Try different filters or add a new Transaction maybe?");
            /*mLayoutManager= new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mAdadpter);*/
            //showMessage("Oops", "No expenses found");
            return;
        }
                else{
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append(res.getString(0) + "," + res.getString(1) + "," + res.getString(2) + "," +
                            res.getString(3) + "," + res.getString(4) + ", " + res.getString(5) + "," +
                            res.getString(6) + "," + res.getString(7) + ", " + res.getString(8) + ", " + res.getString(9) +"\n");
                }


                String[] bufferdata = buffer.toString().split("\n");
                EarningListItems = new ArrayList<>();
                for (int i = 0; i < bufferdata.length; i++) {
                    String[] listindex = bufferdata[i].split(",");
                    Log.d(TAG, "viewData: inside list index" + bufferdata[i]);
                    EarningListItem item = new EarningListItem(listindex[0], listindex[1], listindex[2], listindex[3], listindex[4], listindex[5],
                            listindex[6], listindex[7], listindex[8], listindex[9]);
                    EarningListItems.add(item);
                }
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    mAdadpter = new ShowAllAdapterEarningRecycler(EarningListItems);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setAdapter(mAdadpter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    noTransactionImageView.setVisibility(View.INVISIBLE);
                    noTransactionPrompt_1.setVisibility(View.INVISIBLE);
                    noTransactionPrompt_2.setVisibility(View.INVISIBLE);
                }


                mAdadpter.setOnItemClickListener(new ShowAllAdapterEarningRecycler.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                       /* View parent = (View) v.getParent();

                        if(position%8 == 0) {
                            View vlistitem = parent.findViewById(R.id.divider2);
                            showPopupMenu(vlistitem, position);
                        }else if(position%8 == 1 || position%8 == 6 || position%8 == 7) {
                            View vlistitem = parent.findViewById(R.id.divider3);
                            showPopupMenu(vlistitem, position);
                        }else if(position%8 == 2 || position%8 == 5 || position%8 == 4) {
                            View vlistitem = parent.findViewById(R.id.divider4);
                            showPopupMenu(vlistitem, position);
                        }else if(position%8 == 3 ) {
                            View vlistitem = parent.findViewById(R.id.divider5);
                            //showPopupMenu(vlistitem, position);
                        }*/
                        EarningListItems.get(position);
                        Intent intent = new Intent(getActivity(), EarningEditUpdateScreen.class);
                        String toeditid =  EarningListItems.get(position).getId();
                        String toeditmode =  EarningListItems.get(position).getMode();
                        String toeditamount =  EarningListItems.get(position).getAmount();
                        String toeditdate =  EarningListItems.get(position).getDate();
                        String toeditcurr =  EarningListItems.get(position).getCurrency();
                        String toedit = toeditid+","+toeditmode+","+toeditamount+","+toeditdate+","+toeditcurr;


                        intent.putExtra("toeditfromearningshowall", toedit);
                        startActivity(intent);
                    }
                    //start of comment for popup menu
                    /*

                    public void showPopupMenu(View view, final int position){
                        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                        popupMenu.setGravity(Gravity.CENTER);
                        popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.action_popup_edit:

                                        Intent intent = new Intent(getActivity(), EarningEditUpdateScreen.class);
                                        String toeditid =  EarningListItems.get(position).getId();
                                        String toeditmode =  EarningListItems.get(position).getMode();
                                        String toeditamount =  EarningListItems.get(position).getAmount();
                                        String toeditdate =  EarningListItems.get(position).getDate();
                                        String toedit = toeditid+","+toeditmode+","+toeditamount+","+toeditdate;


                                        intent.putExtra("toeditfromearningshowall", toedit);
                                        startActivity(intent);


                                        break;
                                    case R.id.action_popup_repeat:
                                        Log.d(TAG, "onMenuItemClick: youclicked on item repeat" );
                                        Intent intentr = new Intent(getActivity(), EarningEditRepeatScreen.class);
                                        String toeditidr =  EarningListItems.get(position).getId();
                                        String toeditmoder =  EarningListItems.get(position).getMode();
                                        String toeditamountr =  EarningListItems.get(position).getAmount();
                                        String toeditdater =  EarningListItems.get(position).getDate();
                                        String toeditr = toeditidr+","+toeditmoder+","+toeditamountr+","+toeditdater;


                                        intentr.putExtra("toeditfromearningshowall", toeditr);
                                        startActivity(intentr);

                                        break;
                                    default:
                                        return false;
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                    }*/
                    //end of comment for pop menu
                });

                //creating a touch helper to handle the touch on the card views


                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                itemTouchHelper.attachToRecyclerView(mRecyclerView);


                //ShowAllAdapterEarningList adapter = new ShowAllAdapterEarningList(getActivity(), R.layout.earning_show_all_recycler_item_layout,EarningListItems);
                //listshowall.setAdapter(adapter);
                //Toast.makeText(getActivity(), "Click on an Item to edit", Toast.LENGTH_LONG).show();
                /*listshowall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Log.d(TAG, "onItemClick:you clicked on a list item  " + peopleList.get(position).getName());
                        //toast.makeText(SecondScreen.this, "you clicked on " + peopleList.get(position).getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), EarningEditScreen.class);
                        String toeditid =  EarningListItems.get(position).getId();
                        String toeditmode =  EarningListItems.get(position).getMode();
                        String toeditamount =  EarningListItems.get(position).getAmount();
                        String toeditdate =  EarningListItems.get(position).getDate();
                        String toedit = toeditid+","+toeditmode+","+toeditamount+","+toeditdate;


                        intent.putExtra("toeditfromearningshowall", toedit);
                        startActivity(intent);
                    }
                });*/

            }
    //creating a call back function for the item touch helper object.

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    String toremove = EarningListItems.get(position).getId();
                    Log.d(TAG, "onSwiped: to remove Db id is  " + toremove);
                    mAdadpter.notifyDataSetChanged();
                    EarningListItems.remove(position);
                    if(EarningListItems.isEmpty()){
                        mRecyclerView.setVisibility(View.INVISIBLE);
                        noTransactionImageView.setVisibility(View.VISIBLE);
                        noTransactionPrompt_1.setVisibility(View.VISIBLE);
                        noTransactionPrompt_2.setVisibility(View.VISIBLE);
                    }
                    DeleteData(toremove);
                    break;

                    case ItemTouchHelper.RIGHT:
                        Intent intentr = new Intent(getActivity(), EarningEditRepeatScreen.class);
                        String toeditidr =  EarningListItems.get(position).getId();
                        String toeditmoder =  EarningListItems.get(position).getMode();
                        String toeditamountr =  EarningListItems.get(position).getAmount();
                        String toeditdater =  EarningListItems.get(position).getDate();
                        String toeditcurr =  EarningListItems.get(position).getCurrency();
                        String toeditr = toeditidr+","+toeditmoder+","+toeditamountr+","+toeditdater+","+toeditcurr;
                        mAdadpter.notifyDataSetChanged();
                        intentr.putExtra("toeditfromearningshowall", toeditr);
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
                    .addSwipeRightLabel("Repeat").setSwipeRightLabelColor(R.color.PrimaryText).setSwipeRightLabelTextSize(TypedValue.COMPLEX_UNIT_SP,18)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public Cursor checkFilterOption() {

        Log.d(TAG, "checkFilterOption: inside checkFilterOptions" );
        if(category.equals("") && modeOfPayment.equals("") && firstDate.equals("") && secondDate.equals("")){
            Log.d(TAG, "checkFilterOption: values are "  + category + " " + modeOfPayment + " " + firstDate + " " + secondDate + " ");
            Cursor res = myDb.getAllDataEarning();
            appliedFilter.setText("No Active Filters" );
            return res;
        }else
        {
            Cursor res = myDb.getEarningFiltered(category, modeOfPayment, firstDate, secondDate);
            Log.d(TAG, "else Earning checkFilterOption: values are "  + category + " " + modeOfPayment + " " + firstDate + " " + secondDate + " and res is " + res);
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
        Integer deletedRows = myDb.deleteDataEarning(id);
        Log.d(TAG, "delete id is : "+id);
        if(deletedRows > 0) {
            Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(),"Data not Deleted",Toast.LENGTH_LONG).show();
        }
    }
}



