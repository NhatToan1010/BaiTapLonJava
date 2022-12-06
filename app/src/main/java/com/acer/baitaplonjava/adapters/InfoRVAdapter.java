package com.acer.baitaplonjava.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.acer.baitaplonjava.fragments.HomeViewModel;
import com.acer.baitaplonjava.R;
import com.acer.baitaplonjava.model.Mark;
import com.acer.baitaplonjava.model.Student;
import com.acer.baitaplonjava.model.Subject;
import com.acer.baitaplonjava.model.SubjectMark;

import java.util.ArrayList;
import java.util.List;

public class InfoRVAdapter extends RecyclerView.Adapter<InfoRVAdapter.InfoViewHolder>{

    private List<SubjectMark> mList;
    private Context context;
    private HomeViewModel mViewModel;
    private Student stu;
    public InfoRVAdapter(Context context, Student stu,List<SubjectMark> subjectMark) {
        this.context = context;
        this.mViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(HomeViewModel.class);
        this.stu = stu;
        this.mList = subjectMark;
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_rv_info, parent, false);
        return new InfoViewHolder(view);
    }


    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        SubjectMark subjectMark = mList.get(position);

        holder.tvSubject.setText(subjectMark.getSubject());
        holder.tvMark.setText(subjectMark.toString());
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<SubjectMark> list){
        mList = list;
        notifyDataSetChanged();
    }


    public static class InfoViewHolder extends RecyclerView.ViewHolder{

        private TextView tvSubject, tvMark;
        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvMark = itemView.findViewById(R.id.tvMark);
        }
    }
}
