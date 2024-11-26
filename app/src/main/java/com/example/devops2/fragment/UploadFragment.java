package com.example.devops2.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.devops2.R;
import com.example.devops2.adapter.ShowImageAdapter;

import java.util.ArrayList;

public class UploadFragment extends Fragment {

    private RelativeLayout pickImageButton;
    private ViewPager2 viewPager;
    private Uri ImageUri;
    private ArrayList<Uri> ChooseImageList;
    private ShowImageAdapter showImageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pickImageButton = view.findViewById(R.id.ChooseImage);
        viewPager = view.findViewById(R.id.viewPager);
        ChooseImageList = new ArrayList<>();

        showImageAdapter = new ShowImageAdapter(getContext(), ChooseImageList);

        viewPager.setAdapter(showImageAdapter);
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
        }
    }
    private void setAdapter() {
        ShowImageAdapter adapter =new ShowImageAdapter(getContext(), ChooseImageList);
        viewPager.setAdapter(adapter);
    }
}
