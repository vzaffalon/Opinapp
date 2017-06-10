package com.opinnapp.opinnapp.tabholder.explore.tabs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opinnapp.opinnapp.R;
import com.opinnapp.opinnapp.models.OAUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vzaffalon on 19/05/17.
 */

public class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.ViewHolder> {
    private List<OAUser> perfils;
    private Context context;
    private final OnItemClickListener clickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView perfilName;
        public CircleImageView perfilPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            perfilName = (TextView) itemView.findViewById(R.id.cell_perfil_name);
            perfilPicture = (CircleImageView) itemView.findViewById(R.id.cell_perfil_picture);
        }

        public void bind(final OAUser item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PerfilAdapter(List<OAUser> mPerfils, Context mContext, OnItemClickListener listener) {
        perfils = mPerfils;
        this.context = mContext;
        this.clickListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PerfilAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_perfil, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.bind(perfils.get(position),clickListener);
        holder.perfilName.setText(perfils.get(position).getName());
        Picasso.with(context).load(perfils.get(position).getImagePath()).into(holder.perfilPicture);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return perfils.size();
    }

    public interface OnItemClickListener {
        void onItemClick(OAUser item);
    }

}
