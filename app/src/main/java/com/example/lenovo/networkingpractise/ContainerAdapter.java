package com.example.lenovo.networkingpractise;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContainerAdapter extends ArrayAdapter<Container>{
   ArrayList<Container> containerArrayList;
    Context context;

    public ContainerAdapter(Context context,ArrayList<Container> containerArrayList) {
        super(context,0,containerArrayList);
        this.context=context;
        this.containerArrayList=containerArrayList;
    }

    @Override
    public int getCount() {
        return containerArrayList.size();
    }

    static class ContainerViewHolder{
        TextView idTextView;
        TextView userIdTextView;
        TextView titleTextView;

        public ContainerViewHolder(TextView idTextView, TextView userIdTextView, TextView titleTextView) {
            this.idTextView = idTextView;
            this.userIdTextView = userIdTextView;
            this.titleTextView = titleTextView;
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.list_item,null);
            TextView idTextView=(TextView)convertView.findViewById(R.id.listIdTextView);
            TextView userIdTextView=(TextView)convertView.findViewById(R.id.listUserIdTextView);
            TextView titleTextView=(TextView)convertView.findViewById(R.id.listTitleTextView);
           ContainerViewHolder containerViewHolder=new ContainerViewHolder(idTextView,userIdTextView,titleTextView);
            convertView.setTag(containerViewHolder);
        }
        Container c=containerArrayList.get(position);
        ContainerViewHolder containerViewHolder=(ContainerViewHolder)convertView.getTag();
        containerViewHolder.idTextView.setText("id="+c.id);
        containerViewHolder.userIdTextView.setText("userId="+c.userId);
        containerViewHolder.titleTextView.setText("title: "+c.title);
        return convertView;
    }
}
