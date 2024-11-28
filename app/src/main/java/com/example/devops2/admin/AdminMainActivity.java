package com.example.devops2.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.devops2.R;

public class AdminMainActivity extends AppCompatActivity {


    private LinearLayout vOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        initView();

        vOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, OrderActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        vOrders = findViewById(R.id.vOrders);
    }
}