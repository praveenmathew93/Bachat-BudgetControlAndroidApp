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

public class ViewAllAdapter extends ArrayAdapter<ListItem> {
    private static final String TAG = "ViewAllAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

   /* public ViewAllAdapter(View.OnClickListener onClickListener, int resource, ArrayList<ListItem> objects) {
        super((Context) onClickListener, resource, objects);
    }*/

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        //TextView id;
        TextView category;
        TextView subcategory;
        TextView modeofpayment;
        TextView amount;
        TextView date;

    }
    public ViewAllAdapter(Context context, int resource, ArrayList<ListItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the  information
        //String id = getItem(position).getId();
        String category = getItem(position).getCategory();
        String subcategory = getItem(position).getSubcategory();
        String modeofpayment = getItem(position).getModeofpayment();
        String amount = getItem(position).getAmount();
        String date = getItem(position).getDate();

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewAllAdapter.ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewAllAdapter.ViewHolder();
            //holder.id = (TextView) convertView.findViewById(R.id.textViewId);
            holder.category = (TextView) convertView.findViewById(R.id.textViewCategory);
            holder.subcategory = (TextView) convertView.findViewById(R.id.textViewSubcategory);
            holder.modeofpayment = (TextView) convertView.findViewById(R.id.textViewModeOfPayment); //holder element declaration for mode of payment
            holder.amount = (TextView) convertView.findViewById(R.id.textViewAmount);
            holder.date = (TextView) convertView.findViewById(R.id.textViewDate);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewAllAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        //holder.id.setText(id);
        holder.category.setText(category);
        holder.subcategory.setText(subcategory);
        holder.modeofpayment.setText(modeofpayment); //setting text to the mode of payment holder
        holder.amount.setText(amount);
        holder.date.setText(date);

        return convertView;
    }
}
