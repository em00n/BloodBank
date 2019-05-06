package com.emon.bloodbank;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
    TextView nameTV,distTV,bloodTV;
    OnLongClickListener onLongClickListener;

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        nameTV=(TextView) itemView.findViewById(R.id.nameTV);
        distTV=(TextView)itemView.findViewById(R.id.distTV);
        bloodTV=(TextView)itemView.findViewById(R.id.bloodTV);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        onLongClickListener.onLongClick(v,getAdapterPosition());
        return false;
    }


}
