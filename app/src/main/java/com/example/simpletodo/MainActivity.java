package com.example.simpletodo;

import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items; //collection of items
    Button Add; //button that adds tasks
    EditText Task; //text field that sends name of entered task
    RecyclerView list; //view that adds filled with items
    ItemsAdapter itemsAdapter; //allows the collection items to be added to the recyclerview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Add = findViewById(R.id.add);
        Task = findViewById(R.id.Task);
        list = findViewById(R.id.list);

        //items = new ArrayList<>();
        loadItems();

        //Listens to see if one of the items in the rview are clicked and if it is the item is removed
        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(items.size() - 1);
                saveItems();
            }
        };
        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        list.setAdapter(itemsAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        //Listens to the Add button and if its touched
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoitem = Task.getText().toString();
                items.add(todoitem);
                itemsAdapter.notifyItemInserted(items.size() - 1);
                Task.setText("");
                saveItems();
            }
        });
    }
    //fetches the data file
    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }
    //Loads data items to the collection
    private void loadItems() {
        try {
            items = new ArrayList(org.apache.commons.io.FileUtils.readLines(getDataFile(), "UTF-8"));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading item");
            items = new ArrayList<>();
        }


    }

    private void saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading item");
        }
    }
}