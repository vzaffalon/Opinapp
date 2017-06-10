package com.opinnapp.opinnapp.tabholder.comments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OAComment;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vzaffalon on 19/05/17.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    private List<OAComment> comments;
    private Context context;
    private final OnItemClickListener clickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tv_notification;
        public TextView tv_time;
        public CircleImageView iv_perfilPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_notification = (TextView) itemView.findViewById(R.id.cell_notification_message);
            tv_time = (TextView) itemView.findViewById(R.id.cell_notification_time);
            iv_perfilPicture = (CircleImageView) itemView.findViewById(R.id.cell_profile_picture);
        }

        public void bind(final OAComment item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CommentsAdapter(List<OAComment> mComments, Context mContext, OnItemClickListener listener) {
        comments = mComments;
        this.context = mContext;
        this.clickListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_comments, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(comments.get(position),clickListener);
        holder.tv_notification.setText(comments.get(position).getText());
        //holder.tv_time.setText(comments.get(position).getTime());
        Picasso.with(context).load(comments.get(position).getOwner().getImagePath()).into(holder.iv_perfilPicture);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return comments.size();
    }

    public interface OnItemClickListener {
        void onItemClick(OAComment item);
    }

}
