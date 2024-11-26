package com.example.devops2.adapter;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.devops2.R;

import java.util.List;

public class ShowImageAdapter extends RecyclerView.Adapter<ShowImageAdapter.ShowImageHolder>{

    private Context context;
    private List<Uri> mListImage;
    private ItemListener itemListener;

    public ShowImageAdapter(Context context, List<Uri> mListImage) {
        this.context = context;
        this.mListImage = mListImage;
    }

    public Uri getImage(int position){
        return mListImage.get(position);
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ShowImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_image_layout, parent, false);
        return new ShowImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowImageHolder holder, int position) {
        Uri uri = mListImage.get(position);
        Glide.with(context)
                .load(uri)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mListImage.size();
    }

    public class ShowImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView imageView;
        public ShowImageHolder(@NonNull View view) {
            super(view);
            this.imageView = view.findViewById(R.id.UploadImage);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if(itemListener != null) {
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener {
        void onItemClick(View view, int position);
    }
}
