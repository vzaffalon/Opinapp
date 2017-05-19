package com.opinnapp.opinnapp.tabholder.explore.tabs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opinnapp.opinnapp.R;

import java.util.List;

/**
 * Created by vzaffalon on 19/05/17.
 */

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {
    private List<Tag> tags;
    private Context context;
    private final OnItemClickListener clickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tagName;
        public TextView numberOfPosts;
        public TextView postRank;


        public ViewHolder(View itemView) {
            super(itemView);
            tagName = (TextView) itemView.findViewById(R.id.cell_tag_tag_name);
            numberOfPosts = (TextView) itemView.findViewById(R.id.cell_tag_number_of_posts);
            postRank = (TextView) itemView.findViewById(R.id.cell_tag_post_rank);
        }

        public void bind(final Tag item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TagsAdapter(List<Tag> mTags, Context mContext,OnItemClickListener listener) {
        tags = mTags;
        this.context = mContext;
        this.clickListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TagsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_tag, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(tags.get(position),clickListener);
        holder.numberOfPosts.setText(tags.get(position).getNumberOfPosts() + " postagens");
        holder.tagName.setText(tags.get(position).getTagName());
        holder.postRank.setText(tags.get(position).getPostRank());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tags.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Tag item);
    }

}
