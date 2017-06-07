package com.opinnapp.opinnapp.tabholder.newquestion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.tabholder.explore.tabs.Tag;

import java.util.List;

/**
 * Created by vzaffalon on 19/05/17.
 */

public class ConfirmTagsAdapter extends RecyclerView.Adapter<ConfirmTagsAdapter.ViewHolder> {
    private String[] tags;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tagName;


        public ViewHolder(View itemView) {
            super(itemView);
            tagName = (TextView) itemView.findViewById(R.id.cell_tag_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ConfirmTagsAdapter(String[] mTags, Context mContext) {
        tags = mTags;
        this.context = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ConfirmTagsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_confirm_activity_tag, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tagName.setText(tags[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tags.length;
    }

}
