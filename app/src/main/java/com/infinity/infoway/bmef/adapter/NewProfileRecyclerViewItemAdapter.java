package com.infinity.infoway.bmef.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.infinity.infoway.bmef.R;

public class NewProfileRecyclerViewItemAdapter extends RecyclerView.Adapter<NewProfileRecyclerViewItemAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_lecture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llcontactdetails, lleducationdetails, llpersonladetails, lldivision, lltime, batchlayout, llimagelecture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
