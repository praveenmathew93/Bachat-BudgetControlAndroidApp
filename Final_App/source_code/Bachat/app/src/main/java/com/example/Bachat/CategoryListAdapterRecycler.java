package com.example.Bachat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class CategoryListAdapterRecycler extends RecyclerView.Adapter<CategoryListAdapterRecycler.CategoryViewHolder> {

    ArrayList<Category> mCategoryList;
    private CategoryListAdapterRecycler.OnItemClickListener mListener;
    private static final String TAG = "ShowAllAdapterEarningRe";
    private Context mcontext;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CategoryListAdapterRecycler.OnItemClickListener listener) {
        mListener = listener;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView category;
        ImageView image;

        public CategoryViewHolder(@NonNull View itemView, final CategoryListAdapterRecycler.OnItemClickListener listener) {
            super(itemView);
            category = itemView.findViewById(R.id.textViewCategoryName);
            image = itemView.findViewById(R.id.imageViewCategoryImage);
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


    public CategoryListAdapterRecycler(ArrayList<Category> categoryList,Context context) {
        mCategoryList = categoryList;
        mcontext = context;
    }

    @NonNull
    @Override
    public CategoryListAdapterRecycler.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.category_recycler_item, parent, false);
        CategoryListAdapterRecycler.CategoryViewHolder evh = new CategoryListAdapterRecycler.CategoryViewHolder(v,mListener);
        return evh;
    }
    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapterRecycler.CategoryViewHolder holder, int position) {
        String contactName, noteAdded;

        Category currentItem = mCategoryList.get(position);

        holder.category.setText(currentItem.getName());
        //holder.image.setImageResource(currentItem.getImgURL());
        holder.image.setImageBitmap(loading_icons.decodeSampledBitmapFromResource(mcontext.getResources(),currentItem.getImgURL(),80,80));

    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }
}
