package com.lokesh.weatherinfo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lokesh.weatherinfo.R;

import com.lokesh.weatherinfo.Settings.SettingActivity;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder>  {

    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
    }

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];
    private int mIcons[];

    private String name;
    private int profile;
    private String email;

    Context context;

    void startSettingActivity(int position){
        if(position == 2) {
            Intent i = new Intent();
            i.setClass(context, SettingActivity.class);
            context.startActivity(i);
        }
    }


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int Holderid;

        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView Name;
        TextView email;


        public ViewHolder(View itemView,int ViewType) {
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);



            if(ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                Holderid = 1;
            }

        }

        @Override
        public void onClick(View v) {

            startSettingActivity(getPosition());
        }


    }



    public DrawerAdapter(String Titles[],int Icons[],Context context){
        mNavTitles = Titles;
        mIcons = Icons;
        this.context = context;
    }




    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);

            ViewHolder vhItem = new ViewHolder(v,viewType);

            return vhItem;

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_header,parent,false);

            ViewHolder vhHeader = new ViewHolder(v,viewType);

            return vhHeader;


        }
        return null;

    }


    @Override
    public void onBindViewHolder(DrawerAdapter.ViewHolder holder, int position) {
        if(holder.Holderid ==1) {

            holder.textView.setText(mNavTitles[position - 1]);
            holder.imageView.setImageResource(mIcons[position -1]);
        }

    }


    @Override
    public int getItemCount() {
        return mNavTitles.length+1;
    }



    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}
