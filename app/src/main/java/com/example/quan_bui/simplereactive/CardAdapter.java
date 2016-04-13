package com.example.quan_bui.simplereactive;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Quan Bui on 4/13/16.
 */
public class CardAdapter
    extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<Post> posts;

    public CardAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.card_view_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post =  posts.get(position);
        holder.tvName.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder
        extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageView imgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgDelete.setOnClickListener(v -> removeAt(getLayoutPosition()));
        }
    }

    public void removeAt(int position) {
        posts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, posts.size());
    }
}
