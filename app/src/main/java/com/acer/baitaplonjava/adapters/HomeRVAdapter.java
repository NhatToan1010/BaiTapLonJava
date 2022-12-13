package com.acer.baitaplonjava.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.acer.baitaplonjava.activities.EditActivity;
import com.acer.baitaplonjava.fragments.HomeViewModel;
import com.acer.baitaplonjava.R;
import com.acer.baitaplonjava.activities.InfoActivity;
import com.acer.baitaplonjava.model.Student;

import java.util.List;

public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.HomeViewHolder> {
    private List<Student> mList;
    private final Context context;
    private HomeViewModel mViewModel;
    private Student stu;
    private boolean isEdit = false;

    public HomeRVAdapter(Context context) {
        this.context = context;
        mViewModel = new ViewModelProvider((ViewModelStoreOwner) context)
                .get(HomeViewModel.class);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_rv_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        stu = mList.get(position);
        if (stu == null){
            return;
        }
        holder.tvName.setText(stu.getName());
        holder.tvStuID.setText(stu.getID());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student chooseStu = mList.get(holder.getAdapterPosition());
                sendItemEdit(chooseStu, context);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student chooseStu = mList.get(holder.getAdapterPosition());
                deleteItem(chooseStu);
            }
        });
        holder.cvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student chooseStu = mList.get(holder.getAdapterPosition());
                sendItemInfo(chooseStu, context);
            }
        });
    }

    private void sendItemEdit(Student stu, Context context){
        isEdit = true;
        Intent intent = new Intent(context, EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Student_key", stu);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void sendItemInfo(Student stu, Context context){
        Intent intent = new Intent(context, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Student_key", stu);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private void deleteItem(Student stu){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete "+stu.getName()+"?");
        builder.setMessage("This student will be remove from system.");
        builder.setNegativeButton("Keep", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mViewModel.deleteStudent(stu);
                mViewModel.deleteById(stu.getID());
                Toast.makeText(context, stu.getName()+" has been deleted!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Student> list){
        mList = list;
        notifyDataSetChanged();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName, tvStuID;
        private ImageView btnDelete, btnEdit;
        private CardView cvHome;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvSetName);
            tvStuID = itemView.findViewById(R.id.tvSetID);

            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);

            cvHome = itemView.findViewById(R.id.cvHome);
        }
    }
}
