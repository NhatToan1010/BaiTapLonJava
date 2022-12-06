package com.acer.baitaplonjava.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.acer.baitaplonjava.database.AppDatabase;
import com.acer.baitaplonjava.database.MarkDAO;
import com.acer.baitaplonjava.model.Mark;

import java.util.List;

public class MarkRepo {
    private MarkDAO dao;
    private LiveData<List<Mark>> mList;

    public MarkRepo(Application application) {
        this.dao = AppDatabase.getDatabase(application).markDAO();
        this.mList = dao.findAll();
    }

    public LiveData<List<Mark>> getAllList() {
        return mList;
    }

    public void insert(Mark mark){
        new insertAsyncTask(dao).execute(mark);
    }

    public static class insertAsyncTask extends AsyncTask<Mark, Void, Void>{

        private MarkDAO dao;

        public insertAsyncTask(MarkDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Mark... marks) {
            dao.insert(marks[0]);
            return null;
        }
    }
    public void update(Mark mark){
        new updateAsyncTask(dao).execute(mark);
    }

    public static class updateAsyncTask extends AsyncTask<Mark, Void, Void>{

        private MarkDAO dao;

        public updateAsyncTask(MarkDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Mark... marks) {
            dao.update(marks[0]);
            return null;
        }
    }
    public void delete(Mark mark){
        new deleteAsyncTask(dao).execute(mark);
    }

    public static class deleteAsyncTask extends AsyncTask<Mark, Void, Void>{

        private MarkDAO dao;

        public deleteAsyncTask(MarkDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Mark... marks) {
            dao.delete(marks[0]);
            return null;
        }
    }
    public void deleteAll(){
        dao.deleteAll();
    }

    public List<Float> getMarkById(String stuId){
        return dao.getMarkById(stuId);
    }

    public int getCountOfMark(String stuId){
        return dao.getCountOfMark(stuId);
    }

    public float getAVGMark(String stuId){
        return dao.getAVGMark(stuId);
    }
}
