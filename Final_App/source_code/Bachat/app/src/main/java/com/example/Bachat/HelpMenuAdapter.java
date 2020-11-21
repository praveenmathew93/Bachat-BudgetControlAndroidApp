package com.example.Bachat;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelpMenuAdapter extends RecyclerView.Adapter<HelpMenuAdapter.VersionVH> {

    List<HelpMenuItems> versionsList;
    Context context;







    public HelpMenuAdapter(List<HelpMenuItems> versionsList, Context context) {
        this.versionsList = versionsList;
        this.context = context;

    }

    @NonNull
    @Override
    public VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new VersionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionVH holder, int position) {

        final HelpMenuItems versions = versionsList.get(position);
        holder.codeNameTxt.setText(versions.getCodeName());
        holder.versionTxt.setText(versions.getVersion());
        //  holder.descriptionTxt.setText(versions.getDescription());
        holder.imageButton.setText(versions.getButton());
        //   holder.imageButton.setImageDrawable(context.getResources().getDrawable(versions.getImagebutton()));


        if (position==0){
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Slider1.class);

                    context.startActivity(intent);
                }

            });


        }
        else if (position==1) {
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Slider2.class);

                    context.startActivity(intent);
                }

            });

        }
        else if (position==2){
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Slider3.class);

                    context.startActivity(intent);
                }

            });


        }
        else if (position==3){
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Slider4.class);

                    context.startActivity(intent);
                }

            });


        }

        else if (position==4){
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Slider5.class);

                    context.startActivity(intent);
                }

            });


        }

        else if (position==5){
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Slider6.class);

                    context.startActivity(intent);
                }

            });


        }
        else if (position==6){
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Slider7.class);

                    context.startActivity(intent);
                }

            });


        }
        else if (position==7){
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Slider8.class);

                    context.startActivity(intent);
                }

            });


        }
        else if (position==8){
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Slider9.class);

                    context.startActivity(intent);
                }

            });


        }
        else if (position==9){
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Slider10.class);

                    context.startActivity(intent);
                }

            });


        }
        else if (position==10){
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Slider11.class);

                    context.startActivity(intent);
                }

            });


     //   }
       // else if (position==11){
         //   holder.imageButton.setOnClickListener(new View.OnClickListener() {
           //     @Override
             //   public void onClick(View v) {
              //      Intent intent = new Intent(context, Slider12.class);

              //      context.startActivity(intent);
              //  }
//
           // });


      //  }
       // else if (position==12){
         //   holder.imageButton.setOnClickListener(new View.OnClickListener() {
              //  @Override
              //  public void onClick(View v) {
                   // Intent intent = new Intent(context, Slider13.class);

                   // context.startActivity(intent);
               // }

           // });


       // }
        //else if (position==13){
            //holder.imageButton.setOnClickListener(new View.OnClickListener() {
               // @Override
               // public void onClick(View v) {
                 //   Intent intent = new Intent(context, Slider14.class);

                 //  context.startActivity(intent);
               // }

          //  });


        }

        boolean isExpandable = versionsList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);


    }

    @Override
    public int getItemCount() {
        return versionsList.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {

        Button imageButton;


        TextView codeNameTxt, versionTxt;//,descriptionTxt;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public VersionVH(@NonNull View itemView) {
            super(itemView);


            codeNameTxt = itemView.findViewById(R.id.code_name);

            versionTxt = itemView.findViewById(R.id.version);
            imageButton = itemView.findViewById(R.id.btn1);




            //  descriptionTxt =itemView.findViewById(R.id.description);

            linearLayout = itemView.findViewById(R.id.linear_layout11);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);


            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HelpMenuItems versions = versionsList.get(getAdapterPosition());
                    versions.setExpandable(!versions.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
