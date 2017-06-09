package com.opinnapp.opinnapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opinnapp.opinnapp.R;

/**
 * Created by cayke on 09/06/17.
 */

public class OALoadingStoryAdapter extends RecyclerView.Adapter{

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shimmer_story, parent, false);
            return new OALoadingHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    private class OALoadingHolder extends RecyclerView.ViewHolder{
        OALoadingHolder(View itemView) {
            super(itemView);
        }
    }
}
