package com.acer.baitaplonjava.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.baitaplonjava.MainActivity;
import com.acer.baitaplonjava.fragments.HomeViewModel;
import com.acer.baitaplonjava.R;
import com.acer.baitaplonjava.adapters.InfoRVAdapter;
import com.acer.baitaplonjava.dialogs.DisplayInfoDialog;
import com.acer.baitaplonjava.helpers.DateTimeHelper;
import com.acer.baitaplonjava.model.Student;
import com.acer.baitaplonjava.model.SubjectMark;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements OnClickListener {

    private TextView tvID, tvName, tvGender, tvMajor, tvAddress, tvDob;
    private Button btnDisplay, btnRating;
    private Student stu;
    private HomeViewModel mViewModel;
    private String rank = "A";
    private float avgMark;
    private List<String> subName;
    private List<Float> subMark;
    private List<SubjectMark> subjectMark;
    private InfoRVAdapter mAdapter;
    private RecyclerView rvInfo;


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        toolbar.setTitle("Information");
        setSupportActionBar(toolbar);

        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        tvID = findViewById(R.id.tvID);
        tvName = findViewById(R.id.tvName);
        tvGender = findViewById(R.id.tvGender);
        tvMajor = findViewById(R.id.tvMajor);
        tvAddress = findViewById(R.id.tvAddress);
        tvDob = findViewById(R.id.tvDob);

        btnDisplay = findViewById(R.id.btnShowMark);
        btnRating = findViewById(R.id.btnRate);

        btnRating.setOnClickListener(this);
        btnDisplay.setOnClickListener(this);

        getIdFromEdit();
        addItemsToList();
        mAdapter = new InfoRVAdapter(InfoActivity.this, stu, subjectMark);
        rvInfo = findViewById(R.id.rvInfo);
        rvInfo.setLayoutManager(new LinearLayoutManager(this));
        rvInfo.setAdapter(mAdapter);
        subjectMark = null;
    }

    private void addItemsToList(){
        subName = mViewModel.getSubNameById(stu.getID());
        subMark = mViewModel.getMaryById(stu.getID());
        int count = mViewModel.getCountOfMark(stu.getID()) - 1;
        subjectMark = new ArrayList<SubjectMark>();
        for (int i = 0; i < mViewModel.getCountOfMark(stu.getID()); i++) {
            subjectMark.add(new SubjectMark(subName.get(i), subMark.get(i)));
        }
    }

    @SuppressLint("SetTextI18n")
    private void getIdFromEdit(){
        if (getIntent().getExtras() != null) {
            stu = (Student) getIntent().getExtras().get("Student_key");
            tvID.setText(stu.getID());
            tvName.setText(stu.getName());
            tvMajor.setText(stu.getMajor());
            tvAddress.setText(stu.getAddress());
            tvDob.setText(DateTimeHelper.toString(stu.getDob()));
            if (stu.isGender()) {
                tvGender.setText("Male");
            } else {
                tvGender.setText("Female");
            }
        }
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnShowMark:
                DisplayInfoDialog dialog = new DisplayInfoDialog(InfoActivity.this, stu, mAdapter, subjectMark);
                dialog.show();
                break;
            case R.id.btnRate:
                if(mViewModel.getCountOfMark(stu.getID()) <= 0){
                    Toast.makeText(this, "You haven't enroll any class yet!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                avgMark = (float) (Math.round(mViewModel.getAVGMark(stu.getID()) * 10) * 0.04);
                getRank();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Average Mark Count");
                builder.setMessage("Your average: "+avgMark+"\n\nYour rank: "+rank);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog avgDialog = builder.create();
                avgDialog.show();
                break;
        }
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(this, MainActivity.class);
        startActivity(a);
    }

    private void getRank(){
        if(avgMark > 3.0 && avgMark <= 3.5){
            rank = "B+";
        }
        else if(avgMark > 2.5 && avgMark <= 3.0){
            rank = "B";
        }
        else if(avgMark > 2.0 && avgMark <= 2.5){
            rank = "C+";
        }
        else if(avgMark > 1.5 && avgMark <= 2.0){
            rank = "C";
        }
        else if(avgMark > 1.0 && avgMark <= 1.5){
            rank = "D+";
        }
        else if(avgMark > 0.5 && avgMark <= 1.0){
            rank = "D";
        }
        else if(avgMark < 0.5){
            rank = "F";
        }
    }
}