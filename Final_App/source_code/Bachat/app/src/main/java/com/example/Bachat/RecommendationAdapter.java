package com.example.Bachat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.RecommendationViewHolder> {

    ArrayList<Category_Recommendation> mRecommendationList;
    private RecommendationAdapter.OnItemClickListener mListener;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(RecommendationAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public class RecommendationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView category;
        ImageView icon;
        TextView transactions;

        public RecommendationViewHolder(@NonNull View itemView, final RecommendationAdapter.OnItemClickListener listener) {
            super(itemView);
            category = itemView.findViewById(R.id.textViewCategoryName);
            icon = itemView.findViewById(R.id.imageViewCategoryImage);
            transactions = itemView.findViewById(R.id.transaction_number);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }

        }


    }


    public RecommendationAdapter(ArrayList<Category_Recommendation> RecommendationList, Context context) {
        mRecommendationList = RecommendationList;
        mContext = context;
    }

    @NonNull
    @Override
    public RecommendationAdapter.RecommendationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.category_recycler_item_recom, parent, false);
        RecommendationAdapter.RecommendationViewHolder evh = new RecommendationAdapter.RecommendationViewHolder(v,mListener);
        return evh;
    }
    @Override
    public void onBindViewHolder(@NonNull RecommendationAdapter.RecommendationViewHolder holder, int position) {
        String contactName, noteAdded;

        Category_Recommendation currentItem = mRecommendationList.get(position);

        Log.d("Adapter Test", "onBindViewHolder: " + currentItem.getCategory()+currentItem.getIconURL()+currentItem.getTransactions());

        holder.category.setText(currentItem.getCategory());
        //holder.image.setImageResource(currentItem.getImgURL());
        holder.icon.setImageBitmap(loading_icons.decodeSampledBitmapFromResource(mContext.getResources(),currentItem.getIconURL(),80,80));
        holder.transactions.setText(currentItem.getTransactions());


    }

    @Override
    public int getItemCount() {
        return mRecommendationList.size();
    }
}
