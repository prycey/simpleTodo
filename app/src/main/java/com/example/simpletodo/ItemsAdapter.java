package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.viewHolder>{
    @NonNull
    List<String> items;

    public ItemsAdapter(List<String> items){
        this.items = items;
    }
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view with a layout inflator
        View todoview = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new viewHolder(todoview);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
    TextView tvItem;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(String item) {
            tvItem.setText(item);
        }
    }
}
