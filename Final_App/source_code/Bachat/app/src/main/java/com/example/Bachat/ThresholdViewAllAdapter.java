package com.example.Bachat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class ThresholdViewAllAdapter extends ArrayAdapter<ThresholdListItem> {
    private static final String TAG = "ThresholdViewAllAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView category;
        TextView amount;
        ImageView categoryIcon;
    }

    public ThresholdViewAllAdapter(Context context, int resource, ArrayList<ThresholdListItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        setupImageLoader();

        //get the  information
        String category = getItem(position).getCategory();
        String amount = getItem(position).getAmount();
        String categoryIcon=getItem(position).getIconURL();

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ThresholdViewAllAdapter.ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ThresholdViewAllAdapter.ViewHolder();
            holder.category = (TextView) convertView.findViewById(R.id.textViewCategory);
            holder.amount = (TextView) convertView.findViewById(R.id.textViewAmount);
            holder.categoryIcon = (ImageView)  convertView.findViewById(R.id.category_icon);


            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ThresholdViewAllAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        /*Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;*/

        //holder.id.setText(id);
        holder.category.setText(category);
        holder.amount.setText(amount);
        ImageLoader imageLoader = ImageLoader.getInstance();
        int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed",null,mContext.getPackageName());

        //create display options
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).resetViewBeforeLoading(true).cacheInMemory(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        //download and display image from url
        imageLoader.displayImage(categoryIcon, holder.categoryIcon, options);



        return convertView;
    }

    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }

}
