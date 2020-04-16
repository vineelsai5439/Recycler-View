package com.vs.recyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> mExampleList;
    private Adapter mAdapter;
    private EditText editTextInsert;
    private EditText editTextRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonInsert = findViewById(R.id.button_insert);
        Button buttonRemove = findViewById(R.id.button_remove);
        editTextInsert = findViewById(R.id.edittext_insert);
        editTextRemove = findViewById(R.id.edittext_remove);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextInsert.getText().toString().equals("")) {
                    int position = Integer.parseInt(editTextInsert.getText().toString());
                    insertItem(position);
                } else {
                    Toast.makeText(MainActivity.this, "Enter the Position", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextRemove.getText().toString().equals("")) {
                    int position = Integer.parseInt(editTextRemove.getText().toString());
                    removeItem(position);
                } else {
                    Toast.makeText(MainActivity.this, "Enter the Position", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createList();
        buildRecyclerView();
    }

    public void insertItem(int position) {
        mExampleList.add(position, new Item(R.drawable.ic_android, "New Item At Position" + position, "This is Line 2"));
        mAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position) {
        if (mAdapter.getItemCount() > position) {
            mExampleList.remove(position);
            mAdapter.notifyItemRemoved(position);
        } else {
            Toast.makeText(MainActivity.this, "Enter a valid position", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeItem(int position, String text) {
        mExampleList.get(position).changeItem(text);
        mAdapter.notifyItemChanged(position);
    }

    public void createList() {
        mExampleList = new ArrayList<>();
        mExampleList.add(new Item(R.drawable.ic_android, "Line 1", "Line 2"));
        mExampleList.add(new Item(R.drawable.ic_android, "Line 3", "Line 4"));
        mExampleList.add(new Item(R.drawable.ic_android, "Line 5", "Line 6"));
    }

    public void buildRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Adapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                changeItem(position, "Clicked");
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

}
