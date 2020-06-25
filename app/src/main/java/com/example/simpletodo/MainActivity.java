package com.example.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
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

    public static final  String KEY_ITEM_TEXT = "item_text";
    public static final  String KEY_ITEM_POSITION = "item_position";
    public static final  int EDIT_TEXT_CODE = 20;
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
        ItemsAdapter.OnClickListener onClickListener =  new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
        Intent i = new Intent(MainActivity.this, Activityedit.class);
        i.putExtra(KEY_ITEM_TEXT,  items.get(position));
                i.putExtra(KEY_ITEM_POSITION,  position);
                startActivityForResult(i,EDIT_TEXT_CODE);
            }
        };
        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
            items.set(position, itemText);
            itemsAdapter.notifyItemChanged(position);
        }
        super.onActivityResult(requestCode, resultCode, data);
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