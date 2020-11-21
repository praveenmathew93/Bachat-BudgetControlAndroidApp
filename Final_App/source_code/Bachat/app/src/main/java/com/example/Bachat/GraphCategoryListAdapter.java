package com.example.Bachat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class GraphCategoryListAdapter  extends RecyclerView.Adapter<GraphCategoryListAdapter.GraphCategoryViewHolder> {
    private static final String TAG = "GraphCategoryListAdapter";
    private ArrayList<GraphCategory> mGraphCategorylist;
    private Context mContext;
    private GraphCategoryListAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(GraphCategoryListAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public class GraphCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         TextView category;
         ImageView icon;
         TextView transactions;
         TextView percentage;

         public GraphCategoryViewHolder(@NonNull View itemView, final GraphCategoryListAdapter.OnItemClickListener listener){
             super(itemView);
             category=itemView.findViewById(R.id.category_name);
             icon=itemView.findViewById(R.id.categoryIcon);
             transactions=itemView.findViewById(R.id.transactions_count);
             percentage=itemView.findViewById(R.id.percentage);
             itemView.setOnClickListener(this);
         }

         @Override
        public void onClick(View v){
             if(mListener!=null){
                 int position = getAdapterPosition();
                 if(position!=RecyclerView.NO_POSITION){
                     mListener.onItemClick(position);
                 }
             }
         }
    }

    public GraphCategoryListAdapter(ArrayList<GraphCategory> graphCategoryList,Context context) {
        mGraphCategorylist = graphCategoryList;
        mContext = context;
    }


    @NonNull
    @Override
    public GraphCategoryListAdapter.GraphCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.graphs_adapter, parent, false);
        GraphCategoryListAdapter.GraphCategoryViewHolder evh = new GraphCategoryListAdapter.GraphCategoryViewHolder(v,mListener);
        return evh;
    }
    @Override
    public void onBindViewHolder(@NonNull GraphCategoryListAdapter.GraphCategoryViewHolder holder, int position) {
        String contactName, noteAdded;

        GraphCategory currentItem = mGraphCategorylist.get(position);

        holder.category.setText(currentItem.getCategoryName());
        holder.percentage.setText(currentItem.getPercentage());
        holder.transactions.setText(currentItem.getTransactions());
        //holder.image.setImageResource(currentItem.getImgURL());
        holder.icon.setImageBitmap(loading_icons.decodeSampledBitmapFromResource(mContext.getResources(),currentItem.getIconURL(),75,75));


    }

    @Override
    public int getItemCount() {
        return mGraphCategorylist.size();
    }


}



