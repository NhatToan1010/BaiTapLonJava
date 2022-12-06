package com.acer.baitaplonjava.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.acer.baitaplonjava.activities.InfoActivity;
import com.acer.baitaplonjava.adapters.InfoRVAdapter;
import com.acer.baitaplonjava.fragments.HomeViewModel;
import com.acer.baitaplonjava.R;
import com.acer.baitaplonjava.model.Mark;
import com.acer.baitaplonjava.model.Student;
import com.acer.baitaplonjava.model.Subject;
import com.acer.baitaplonjava.model.SubjectMark;

import java.util.ArrayList;
import java.util.List;

public class DisplayInfoDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private HomeViewModel mViewModel;
    private EditText edtSubject, edtMark;
    private Student stu;
    private AutoCompleteTextView spSubID;
    private String spSub;
    private List<String> mList;
    private InfoRVAdapter mAdapter;
    private List<SubjectMark> smList;

    public DisplayInfoDialog(@NonNull Context context, Student stu, InfoRVAdapter mAdapter, List<SubjectMark> smList) {
        super(context);
        this.context = context;
        this.mViewModel = new ViewModelProvider((ViewModelStoreOwner) context)
                .get(HomeViewModel.class);
        this.stu = stu;
        this.mAdapter = mAdapter;
        this.smList = smList;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_info);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getAttributes().windowAnimations = R.style.DialogScale;

        edtSubject = findViewById(R.id.edtSubject);
        edtMark = findViewById(R.id.edtMark);
        spSubID = findViewById(R.id.spSubId);

        findViewById(R.id.btnDiaClose).setOnClickListener(this);
        findViewById(R.id.btnDiaSave).setOnClickListener(this);

        fillToSpinner();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnDiaClose:
                dismiss();
                break;
            case R.id.btnDiaSave:
                String strMark = (edtMark.getText().toString().trim());
                if (TextUtils.isEmpty(strMark) || TextUtils.isEmpty(spSub)){
                    Toast.makeText(context, "All information must be filled!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                float flMark = Float.parseFloat(strMark);
                Mark mark = new Mark(stu.getID(), spSub, flMark);
                mViewModel.insertMark(mark);
                Toast.makeText(context, "Insert success!",
                        Toast.LENGTH_SHORT).show();
                intentToInfo();
                dismiss();
                break;
        }
    }
    @SuppressLint("ResourceType")
    private void fillToSpinner(){
        mList = mViewModel.getSubId();
        ArrayAdapter<String> subjectArrayAdapter = new ArrayAdapter<>(context,
                R.layout.dropdown_items, mList);
        spSubID.setAdapter(subjectArrayAdapter);
        spSubID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                spSub = item;
                edtSubject.setText(mViewModel.findById(item));
            }
        });
    }

    private void intentToInfo(){
        Intent intent = new Intent(context, InfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Student_key", stu);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @SuppressLint("NotifyDataSetChanged")
    public synchronized void setList(List<SubjectMark> mList){
//        mAdapter.notifyDataSetChanged();
//        mAdapter.setData(smList);
        mList.notify();
    }
}
