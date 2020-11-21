package com.example.Bachat;

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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowAllAdapterEarningRecycler extends RecyclerView.Adapter<ShowAllAdapterEarningRecycler.EarningViewHolder> {
    ArrayList<EarningListItem> mEarningList;
    private OnItemClickListener mListener;
    private static final String TAG = "ShowAllAdapterEarningRe";


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener =listener;
    }

    public class EarningViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView mode;
        TextView amount;
        TextView date;
        TextView mop;
        TextView currency;
        ImageView note;
        ImageView contact;
        ImageView noNote;
        ImageView noContact;
        ImageButton menu;


        public EarningViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mode = itemView.findViewById(R.id.textViewCategoryEarning);
            amount = itemView.findViewById(R.id.textViewAmountEarning);
            date = itemView.findViewById(R.id.textViewDateEarning);
            mop = itemView.findViewById(R.id.textViewModeOfPaymentEarning);
            currency = itemView.findViewById(R.id.textViewCurrencyEarning);
            note = itemView.findViewById(R.id.imageViewNote);
            contact = itemView.findViewById(R.id.imageViewContact);
            note.setVisibility(View.INVISIBLE);
            contact.setVisibility(View.INVISIBLE);
            noNote = itemView.findViewById(R.id.imageViewNoNote);
            noContact = itemView.findViewById(R.id.imageViewNoContact);
            noNote.setVisibility(View.INVISIBLE);
            noContact.setVisibility(View.INVISIBLE);
            //menu = itemView.findViewById(R.id.imageButtonMenu);
            //menu.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (mListener != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }

        }

        private void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.pop_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_popup_edit:


                    ShowAllExpenseFragment.a = 1;

                    return true;
                case R.id.action_popup_repeat:
                    ShowAllExpenseFragment.a = 2;
                    Log.d("inside action repeat", "onMenuItemClick: youclicked on item repeat");

                    break;
                default:
                    return false;
                }
            return false;
         }
    }



    public ShowAllAdapterEarningRecycler(ArrayList<EarningListItem> earningList) {
        mEarningList = earningList;
    }

    @NonNull
    @Override
    public EarningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.earning_show_all_recycler_item_layout, parent, false);
        EarningViewHolder evh = new EarningViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull EarningViewHolder holder, int position) {
        String contactName, noteAdded;

        EarningListItem currentItem = mEarningList.get(position);
        contactName = currentItem.getContactName();
        noteAdded = currentItem.getNote();
        holder.mode.setText(currentItem.getMode());
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
        holder.mop.setText(currentItem.getModeOfPayment());
        holder.currency.setText(currentItem.getCurrency());
        Log.d(TAG, "onBindViewHolder: the value of CURRENCY IS  is " + currentItem.getCurrency());
        Log.d(TAG, "onBindViewHolder: the value of contact is " + "" + " and note is " + noteAdded);
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
        return mEarningList.size();
    }
}
