package com.example.Bachat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileListAdapter  extends RecyclerView.Adapter<ProfileListAdapter.ViewHolder> {

    private ArrayList<String> mProfileList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView profileName;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            profileName = itemView.findViewById(R.id.Profile);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });
        }
    }

    public ProfileListAdapter(ArrayList<String> profileList){
        mProfileList=profileList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_adapter,parent,false);
        ViewHolder vh = new ViewHolder(view,mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String currentProfile = mProfileList.get(position);

        holder.profileName.setText(currentProfile);

    }

    @Override
    public int getItemCount() {
        return mProfileList.size();
    }



}



