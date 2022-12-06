package com.acer.baitaplonjava.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.acer.baitaplonjava.model.Account;
import com.acer.baitaplonjava.model.Mark;
import com.acer.baitaplonjava.model.Student;
import com.acer.baitaplonjava.model.Subject;

@Database(entities = {Student.class, Subject.class, Mark.class, Account.class}, version = 4)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase instance;
    private static final  String DATABASE_NAME = "STUDENT_MANAGEMENT";
    public abstract StudentDAO studentDAO();
    public abstract SubjectDAO subjectDAO();
    public abstract MarkDAO markDAO();
    public abstract AccountDAO accountDAO();

    private static RoomDatabase.Callback subCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateData(instance).execute();
        }
    };
    public static synchronized AppDatabase getDatabase(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(subCallback)
                    .build();
        }
        return instance;
    }

    public static class populateData extends AsyncTask<Void, Void, Void>{
        private SubjectDAO subDao;
        public populateData(AppDatabase app) {
            this.subDao = app.subjectDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String[] subID = {"CT101", "CT200", "CT173", "CT176", "CT177"};
            String[] subName = {"Lap trinh can ban A",
                                "Nen tang cong nghe thong tin",
                                "Kien truc may tinh",
                                "Lap trinh huong doi tuong",
                                "Cau truc du lieu"};
            int i = 0;
            while (i<subID.length){
                Subject sub = new Subject(subID[i], subName[i]);
                subDao.insert(sub);
                i++;
            }
            return null;
        }
    }
}
