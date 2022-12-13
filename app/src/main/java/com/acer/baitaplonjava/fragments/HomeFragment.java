package com.acer.baitaplonjava.fragments;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.baitaplonjava.R;
import com.acer.baitaplonjava.activities.EditActivity;
import com.acer.baitaplonjava.adapters.HomeRVAdapter;
import com.acer.baitaplonjava.model.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel mViewModel;
    private FloatingActionButton fabAdd, fabDeleteAll, fabShow;
    private RecyclerView rvMain;
    private HomeRVAdapter mAdapter;
    private TextView tvAnnounce;
    private boolean isClicked = false;
    private boolean hasStudent = true;

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mViewModel.getStudentList().observe(getViewLifecycleOwner(), new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                mAdapter.setData(students);
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new HomeRVAdapter(getActivity());
        rvMain = view.findViewById(R.id.rvHome);
        rvMain.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMain.setAdapter(mAdapter);

        fabAdd = view.findViewById(R.id.fabMakeNew);
        fabDeleteAll = view.findViewById(R.id.fabDeleteAll);
        fabShow = view.findViewById(R.id.fabShow);
        tvAnnounce = view.findViewById(R.id.tvAnnounce);

        rotateOpen = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim);

        fabAdd.setOnClickListener(this);
        fabDeleteAll.setOnClickListener(this);
        fabShow.setOnClickListener(this);

        if(mAdapter.getItemCount() > 1){
            tvAnnounce.setVisibility(View.INVISIBLE);
        }
    }

    private void deleteAllItem(){
        if (mAdapter.getItemCount() == 0){
            Toast.makeText(requireContext(), "There is no student to delete!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete all student?");
        builder.setMessage("All information will be remove from system.");
        builder.setNegativeButton("Keep All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mViewModel.deleteAllStudent();
                mViewModel.deleteAllMark();
                tvAnnounce.setVisibility(View.VISIBLE);
                Toast.makeText(requireContext(), "All student has been deleted!",
                        Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabMakeNew:
                Intent intent = new Intent(requireContext(), EditActivity.class);
                startActivity(intent);
                break;
            case R.id.fabDeleteAll:
                deleteAllItem();
                break;
            case R.id.fabShow:
                setFABAnim(isClicked);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter.getItemCount() >= 0){
            tvAnnounce.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter.getItemCount() >= 0){
            tvAnnounce.setVisibility(View.INVISIBLE);
        }
    }

    private void setFABAnim(boolean Clicked) {
        if (Clicked){
            fabShow.startAnimation(rotateOpen);
            fabDeleteAll.startAnimation(fromBottom);
            fabAdd.startAnimation(fromBottom);

            fabDeleteAll.setClickable(true);
            fabAdd.setClickable(true);
            isClicked = false;
        }
        else{
            fabShow.startAnimation(rotateClose);
            fabDeleteAll.startAnimation(toBottom);
            fabAdd.startAnimation(toBottom);

            fabDeleteAll.setClickable(false);
            fabAdd.setClickable(false);
            isClicked = true;
        }
    }
}