package com.example.test.ui.InfoSante;

import android.view.ViewGroup;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterInfoSante extends RecyclerView.Adapter{
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MonViewHolder extends RecyclerView.ViewHolder
    {
        public MonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
