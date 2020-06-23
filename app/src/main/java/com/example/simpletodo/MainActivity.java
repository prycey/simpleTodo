package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   List<String> items;

   Button Add;
   EditText Task;
   RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Add = findViewById(R.id.add);
        Task = findViewById(R.id.Task);
        list = findViewById(R.id.list);


        items = new ArrayList<>();
        items.add("walk");
        items.add("dance");
        final ItemsAdapter itemsAdapter = new ItemsAdapter(items);
        list.setAdapter(itemsAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoitem = Task.getText().toString();
                items.add(todoitem);
                itemsAdapter.notifyItemInserted(items.size()-1);
                Task.setText("");
            }
        });


    }
}