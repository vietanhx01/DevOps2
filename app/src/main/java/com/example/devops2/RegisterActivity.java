package com.example.devops2;

import android.content.Intent;
import android.os.Bundle;

import com.example.devops2.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    private Spinner spOps;

    private EditText etHo, etTen, etSDT, etTaiKhoan, etMatKhau, etLaiMatKhau;
    private Button btDangKy, btTroLai;
    private TextView tvDangNhap;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String username = "", password = "", repassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        initView();

        btDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initView() {
        etHo = findViewById(R.id.etHo);
        etTen = findViewById(R.id.etTen);
        etSDT = findViewById(R.id.etSDT);
        spOps = findViewById(R.id.spOps);
        etTaiKhoan = findViewById(R.id.etTaiKhoan);
        etMatKhau = findViewById(R.id.etMatKhau);
        etLaiMatKhau = findViewById(R.id.etLaiMatKhau);
        btDangKy = findViewById(R.id.btDangKy);
        btTroLai = findViewById(R.id.btTroLai);
        tvDangNhap = findViewById(R.id.tvDangNhap);
        spOps.setAdapter(new ArrayAdapter<>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.spOps)));
    }

    private void register(){
        String firstName = etHo.getText().toString();
        String lastName = etTen.getText().toString();
        String phone = etSDT.getText().toString();
        String Ops = spOps.getSelectedItem().toString();
        username = etTaiKhoan.getText().toString();
        password = etMatKhau.getText().toString();
        repassword = etLaiMatKhau.getText().toString();
        if(firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || Ops.isEmpty() ||
                username.isEmpty() || password.isEmpty() || repassword.isEmpty()){
            Toast.makeText(this, "Không để trông các trường thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!phone.matches("[0-9]{10}")){
            Toast.makeText(this, "Sai định dạng số điện thoại!", Toast.LENGTH_SHORT).show();
        }
        if(password.length() < 6){
            Toast.makeText(this, "Password phải ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(repassword)){
            Toast.makeText(this, "Nhập 2 trường password phải giống nhau!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(etTaiKhoan.getText().toString(), etMatKhau.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            DatabaseReference root_user = FirebaseDatabase.getInstance().getReference("Users");
            @Override
            public void onSuccess(AuthResult authResult) {
//                String userId = auth.getCurrentUser().getUid();
                String usernameChild = username.substring(0, username.length() - 4) + "-com";
                root_user.child(usernameChild).setValue(new User(firstName, lastName, phone, Ops, username)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RegisterActivity.this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                        final Intent data = new Intent();
                        data.putExtra("email", username);
                        data.putExtra("password", password);
                        setResult(RESULT_OK, data);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Tạo tài khoản không thành công!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Tạo tài khoản không thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}