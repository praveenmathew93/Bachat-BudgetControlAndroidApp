package com.example.Bachat;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.EventLog;
import android.util.EventLog.Event;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowAllAdapterRecycler extends  RecyclerView.Adapter<ShowAllAdapterRecycler.ExpenseViewHolder>   {
    private static final String TAG = "ShowAllAdapterRecycler";
    private ArrayList<ListItem> mList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener =listener;
    }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView category;
        TextView subcategory;
        TextView mop;
        TextView amount;
        TextView date;
        TextView currency;
        CardView card;
        ImageView note;
        ImageView contact;
        ImageView noNote;
        ImageView noContact;
        ImageButton menu;

        public ExpenseViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            category = itemView.findViewById(R.id.textViewCategory);
            subcategory = itemView.findViewById(R.id.textViewSubcategory);
            mop = itemView.findViewById(R.id.textViewModeOfPayment);
            amount = itemView.findViewById(R.id.textViewAmount);
            date = itemView.findViewById(R.id.textViewDate);
            card = itemView.findViewById(R.id.card_view);
            currency = itemView.findViewById(R.id.textViewCurrency);
            note = itemView.findViewById(R.id.imageViewNote);
            contact = itemView.findViewById(R.id.imageViewContact);
            note.setVisibility(View.INVISIBLE);
            contact.setVisibility(View.INVISIBLE);
            noNote = itemView.findViewById(R.id.imageViewNoNote);
            noContact = itemView.findViewById(R.id.imageViewNoContact);
            noNote.setVisibility(View.INVISIBLE);
            noContact.setVisibility(View.INVISIBLE);
           // menu.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                    Log.d("inside adapter", "onClick: " + position);
                    //showPopupMenu(v);
                    Log.d("returner", "onClick: "+ v);
                }
            }
        }



        private void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.pop_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
            ViewGroup vg =(ViewGroup) view;
            for(int i = 0; i< vg.getChildCount();i++){
                View vgchild = vg.getChildAt(i);
                Log.d("adapter view call", "onItemClick: "+ vgchild.toString());
            }

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_popup_edit:



                    ShowAllExpenseFragment.a=1;

                    return true;
                case R.id.action_popup_repeat:
                    ShowAllExpenseFragment.a=2;
                    Log.d("inside action repeat", "onMenuItemClick: youclicked on item repeat" );

                    break;
                default:
                    return false;
                }
            return false;
        }
    }

    public ShowAllAdapterRecycler(ArrayList<ListItem> expenseList) {
        mList = expenseList;
    }

    @NonNull
    @Override
    public ShowAllAdapterRecycler.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_recycler_item_layout, parent, false);
        ShowAllAdapterRecycler.ExpenseViewHolder evh = new ShowAllAdapterRecycler.ExpenseViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllAdapterRecycler.ExpenseViewHolder holder, int position) {
        String contactName, noteAdded;

        ListItem currentItem = mList.get(position);
        contactName = currentItem.getContactName();
        noteAdded = currentItem.getNote();
        holder.category.setText(currentItem.getCategory());
        holder.subcategory.setText(currentItem.getSubcategory());
        holder.mop.setText(currentItem.getModeofpayment());
        holder.amount.setText(currentItem.getAmount());
        String date =currentItem.getDate();
        String [] datesplit = date.split("/");
        String month;
        if (datesplit[1].equals("1")){
            month = "Jan";
        }
        else if(datesplit[1].equals("2")){
            month = "Feb";
        }
        else if(datesplit[1].equals("3")){
            month = "Mar";
        }
        else if(datesplit[1].equals("4")){
            month = "Apr";
        }
        else if(datesplit[1].equals("5")){
            month = "May";
        }
        else if(datesplit[1].equals("6")){
            month = "Jun";
        }
        else if(datesplit[1].equals("7")){
            month = "Jul";
        }
        else if(datesplit[1].equals("8")){
            month = "Aug";
        }
        else if(datesplit[1].equals("9")){
            month = "Sep";
        }
        else if(datesplit[1].equals("10")){
            month = "Oct";
        }
        else if(datesplit[1].equals("11")){
            month = "Nov";
        }
        else{
            month = "Dec";
        }
        date = datesplit[2] + "-" + month + "-" + datesplit[0];
        holder.date.setText(date);
        holder.currency.setText(currentItem.getCurrency());
        Log.d(TAG, "onBindViewHolder: the value of contact is " + contactName + " and note is " + noteAdded);
        if(contactName.length()<=1){
            holder.contact.setVisibility(View.INVISIBLE);
            holder.noContact.setVisibility(View.VISIBLE);
        }else{
            holder.contact.setVisibility(View.VISIBLE);
            holder.noContact.setVisibility(View.INVISIBLE);
        }
        if(noteAdded.equals(" Not Set")){
            holder.note.setVisibility(View.INVISIBLE);
            holder.noNote.setVisibility(View.VISIBLE);
        }else{
            holder.note.setVisibility(View.VISIBLE);
            holder.noNote.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: mlist is "+mList);
        return mList.size();
    }
}
