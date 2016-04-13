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
public class PeopleAdapter
    extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    List<Person> people;

    public PeopleAdapter(List<Person> people) {
        this.people = people;
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
        Person person = people.get(position);
        holder.tvName.setText(person.getName());
        holder.tvId.setText(String.valueOf(person.getId()));
        holder.tvAge.setText(String.valueOf(person.getAge()));
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public void removeAt(int position) {
        people.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, people.size());
    }

    class ViewHolder
        extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvId;
        public TextView tvAge;
        public ImageView imgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = (TextView) itemView.findViewById(R.id.tvId);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAge = (TextView) itemView.findViewById(R.id.tvAge);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgDelete.setOnClickListener(v -> removeAt(getLayoutPosition()));
        }
    }
}
