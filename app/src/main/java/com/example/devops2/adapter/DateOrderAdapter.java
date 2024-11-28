package com.example.devops2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devops2.R;

import java.util.ArrayList;

public class DateOrderAdapter extends RecyclerView.Adapter<DateOrderAdapter.DateOrderHolder> {

    private Context context;
    private ArrayList<String> mList;
    private ItemListener itemListener;

    public DateOrderAdapter(Context context, ArrayList<String> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void setmList(ArrayList<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public String getDate(int position){
        return mList.get(position);
    }


    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public DateOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_date_order, parent, false);
        return new DateOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateOrderHolder holder, int position) {
        String date = mList.get(position);
        System.out.println(date);
        String[] time = date.split("-");
        holder.tvDate.setText("Ngày " + String.valueOf(time[0]) + " tháng " + String.valueOf(time[1]) + " năm " + String.valueOf(time[2]));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class DateOrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvDate;

        public DateOrderHolder(@NonNull View view) {
            super(view);
            tvDate = view.findViewById(R.id.tvDate);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null){
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        void onItemClick(View view, int position);
    }
}
