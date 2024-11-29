package com.example.devops2.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.devops2.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminMainActivity extends AppCompatActivity {


    private LinearLayout vOrders;
    private FirebaseAuth auth;
    private View vLogout;
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

        vLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.signOut();
                finish();
                Toast.makeText(AdminMainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        vOrders = findViewById(R.id.vOrders);
        auth = FirebaseAuth.getInstance();
        vLogout = findViewById(R.id.vLogout);
    }
}