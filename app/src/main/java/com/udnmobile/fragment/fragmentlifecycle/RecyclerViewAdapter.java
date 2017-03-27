package com.udnmobile.fragment.fragmentlifecycle;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = "RecyclerViewAdapter";

    private View view;

    private final int NORMAL_TYPE = 0;
    private final int HEADER_TYPE = 1;

    //
    private ItemData[] itemsData;

    //
    public RecyclerViewAdapter(ItemData[] itemsData) {
        this.itemsData = itemsData;
    }

    //
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //
        if (viewType == NORMAL_TYPE) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_normal, parent, false);
            NormalViewHolder holder = new NormalViewHolder(view);
            return holder;
        } else if (viewType == HEADER_TYPE) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_header, parent, false);
            HeaderViewHolder holder = new HeaderViewHolder(view);
            return holder;
        }

        //
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof NormalViewHolder) {

            //Log.d(TAG, itemsData[position].getTitle());
            ((NormalViewHolder) holder).normalTv.setText(itemsData[position].getTitle());
            ((NormalViewHolder) holder).normalIv.setImageResource(itemsData[position].getImageUrl());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    Toast.makeText(v.getContext(), "Item" + position + "is clicked.", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (holder instanceof HeaderViewHolder) {

            ((HeaderViewHolder) holder).headerTv.setText("header");

        }
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_TYPE;
        }
        return NORMAL_TYPE;
    }

    //
    @Override
    public int getItemCount() {
//        return 10;
        return itemsData.length;
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        TextView normalTv;
        ImageView normalIv;

        public NormalViewHolder(View itemView) {
            super(itemView);

            normalTv = (TextView) itemView.findViewById(R.id.item_title);
            normalIv = (ImageView) itemView.findViewById(R.id.item_icon);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView headerTv;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            headerTv = (TextView) itemView.findViewById(R.id.header_title);

        }
    }

    /*//
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView){
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
        }

        @Override
        public void onClick(View view){
            Toast.makeText(view.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }*/

}
