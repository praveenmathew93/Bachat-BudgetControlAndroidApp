/*package com.example.Bachat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class GraphCategoryListAdapter_2  extends ArrayAdapter<GraphCategory>{
    private static final String TAG = "CategoryListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    /*private static class ViewHolder {
        TextView categoryName;
        ImageView iconURL;
        TextView transactions;
        TextView percentage;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    /*public GraphCategoryListAdapter_2(Context context, int resource, ArrayList<GraphCategory> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //sets up the image loader library
        setupImageLoader();

        //get the persons information
        String categoryName = getItem(position).getCategoryName();
        //String birthday = getItem(position).getBirthday();
        //String sex = getItem(position).getSex();
        String iconURL = getItem(position).getIconURL();
        String Percentage = getItem(position).getPercentage();
        String Transactions = getItem(position).getTransactions();

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.categoryName = (TextView) convertView.findViewById(R.id.category_name);
            // holder.birthday = (TextView) convertView.findViewById(R.id.textview2);
            //holder.sex = (TextView) convertView.findViewById(R.id.textView3);
            holder.iconURL = (ImageView) convertView.findViewById(R.id.categoryIcon);
            holder.transactions = (TextView) convertView.findViewById(R.id.transactions_count);
            holder.percentage = (TextView) convertView.findViewById(R.id.percentage);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        /*Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;*/

        /*holder.categoryName.setText(categoryName);
        holder.transactions.setText(String.valueOf(Transactions));
        holder.percentage.setText(String.valueOf(Percentage));


        ImageLoader imageLoader = ImageLoader.getInstance();
        int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed",null,mContext.getPackageName());

        //create display options
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).resetViewBeforeLoading(true).cacheInMemory(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        //download and display image from url
        imageLoader.displayImage(iconURL, holder.iconURL, options);

        return convertView;
    }

    /**
     * Required for setting up the Universal Image loader Library
     */
    /*private void setupImageLoader(){
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
}*/



