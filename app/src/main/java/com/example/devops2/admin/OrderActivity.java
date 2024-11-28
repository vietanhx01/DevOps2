package com.example.devops2.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.devops2.R;
import com.example.devops2.adapter.DateOrderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class OrderActivity extends AppCompatActivity implements DateOrderAdapter.ItemListener{

    private RecyclerView recyclerView;
    private ArrayList<String> list;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Orders");
    private DateOrderAdapter adapter;
    private View vBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.recyclerView);
        vBack = findViewById(R.id.vBack);

        GridLayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new DateOrderAdapter(OrderActivity.this, list);
        recyclerView.setAdapter(adapter);

        vBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, AdminMainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String date = dataSnapshot.getKey().toString();
                    if (date != null) {
                        list.add(date);
                    }
                }
                adapter.setmList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter.setItemListener((DateOrderAdapter.ItemListener) OrderActivity.this);
    }

    @Override
    public void onItemClick(View view, int position) {
        String date = adapter.getDate(position);

    }
}