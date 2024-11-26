package com.example.devops2.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devops2.R;
import com.example.devops2.adapter.ShowImageAdapter;
import com.example.devops2.model.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

public class UploadFragment extends Fragment {

    private RelativeLayout pickImageButton;
    private ViewPager2 viewPager;
    private Uri ImageUri;
    private ArrayList<Uri> ChooseImageList;
    private ShowImageAdapter showImageAdapter;
    private TextView tvSumOrder;
    private FirebaseFirestore firestore;
    private StorageReference reference;
    private FirebaseStorage storage;
    private AppCompatButton btUpload;
    private ArrayList<String> UrlsList;
    private EditText etOrderCode;
    private TextView tvDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Connect XML
        pickImageButton = view.findViewById(R.id.ChooseImage);
        viewPager = view.findViewById(R.id.viewPager);
        tvSumOrder = view.findViewById(R.id.tvSumOrder);
        btUpload = view.findViewById(R.id.btUpload);
        ChooseImageList = new ArrayList<>();
        showImageAdapter = new ShowImageAdapter(getContext(), ChooseImageList);
        viewPager.setAdapter(showImageAdapter);
        etOrderCode = view.findViewById(R.id.etOrderCode);
        tvDate = view.findViewById(R.id.tvDate);

        //Connect Firebase
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();

        UrlsList = new ArrayList<>();

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date = "";
                        if(d<10)
                            date = "0" + d;
                        else
                            date = String.valueOf(d);
                        if(m>8){
                            date += "/" + (m+1) + "/" + y;
                        }else{
                            date += "/0" + (m+1) + "/" + y;
                        }
                        tvDate.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImages();
            }
        });

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckPermission();
                PickImageFromGallry();
            }
        });
    }

    private void CheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            }else{
                PickImageFromGallry();
            }
        } else {
            PickImageFromGallry();
        }
    }
    private void PickImageFromGallry() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null && data.getClipData() != null) {
            int count = data.getClipData().getItemCount();
            for(int i=0; i<count; i++){
                ImageUri=data.getClipData().getItemAt(i).getUri();
                ChooseImageList.add(ImageUri);
                setAdapter();
            }
            showImageAdapter.notifyDataSetChanged();
            tvSumOrder.setText("Tổng " + String.valueOf(ChooseImageList.size()) + " hình ảnh được tải lên!");
        }
    }
    private void setAdapter() {
        ShowImageAdapter adapter =new ShowImageAdapter(getContext(), ChooseImageList);
        viewPager.setAdapter(adapter);
    }

    private void UploadImages() {
        for(int i=0; i<ChooseImageList.size(); i++){
            Uri uri = ChooseImageList.get(i);
            if(uri != null){
                StorageReference imageFolder = FirebaseStorage.getInstance().getReference().child("Image");
                final StorageReference imageName = imageFolder.child("Image" + i + ": " + uri.getLastPathSegment());
                imageName.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                UrlsList.add(String.valueOf(uri));
                                if(UrlsList.size() == ChooseImageList.size()){
                                    StoreLinks(UrlsList);
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    private void StoreLinks(ArrayList<String> urlsList){
        String date = tvDate.getText().toString();
        String orderCode = etOrderCode.getText().toString();
        if(ChooseImageList.size() != 0 && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(orderCode)){
            Item item = new Item(date, orderCode, UrlsList);
            firestore.collection("Items").add(item).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {

                }
            });
        }
    }
}
