package com.example.Bachat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class EarningViewAllAdapter extends ArrayAdapter<EarningListItem> {
    private static final String TAG = "EarningViewAllAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView id;
        TextView mode;
        TextView modeofpayment;
        TextView amount;
        TextView date;


    }
    public EarningViewAllAdapter(Context context, int resource, ArrayList<EarningListItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the  information
        //String id = getItem(position).getId();
        String mode = getItem(position).getMode();
        String modeofpayment = getItem(position).getModeOfPayment();
        String amount = getItem(position).getAmount();
        String date = getItem(position).getDate();


        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        EarningViewAllAdapter.ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new EarningViewAllAdapter.ViewHolder();
            //holder.id = (TextView) convertView.findViewById(R.id.textViewId);
            holder.mode = (TextView) convertView.findViewById(R.id.textViewCategory);
            holder.amount = (TextView) convertView.findViewById(R.id.textViewAmount);
            holder.date = (TextView) convertView.findViewById(R.id.textViewDate);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (EarningViewAllAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        //holder.id.setText(id);
        holder.mode.setText(mode);
        holder.amount.setText(amount);
        holder.date.setText(date);

        return convertView;
    }
}
