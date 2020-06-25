package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.viewHolder>{

    //creates an onLongClick Listener
    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }
    public interface OnClickListener{
        void onItemClicked(int position);
    }
    //items is the collection that is adapted to rview
    List<String> items;
    //on click listener
    OnLongClickListener longClickListener;
   OnClickListener ClickListener;

    //this method initialize a ItemAdapter
    public ItemsAdapter(List<String> items,OnLongClickListener longClickListner,  OnClickListener ClickListener){
        this.items = items;
        this.longClickListener = longClickListner;
        this.ClickListener = ClickListener;
    }

    //creates a view holder to hold new item
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a new view with a layout inflator
        View todoview = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new viewHolder(todoview);
    }

    @Override
    //binds a view holder to a position
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String item = items.get(position);
        holder.bind(item);
    }

    @Override
    //returns the number of items
    public int getItemCount() {
        return items.size();
    }
    //view holder class
    class viewHolder extends RecyclerView.ViewHolder{
    TextView tvItem;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvItem = itemView.findViewById(android.R.id.text1);
        }
        //binds the text to a listener
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClickListener.onItemClicked(getAdapterPosition());
                }
            });
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });

        }

    }
}
