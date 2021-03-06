package com.example.etablissementmanagement.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import com.example.etablissementmanagement.DataBase.DAO.EtablissementDao;
import com.example.etablissementmanagement.DataBase.DAO.UserDao;
import com.example.etablissementmanagement.Models.Etablissement;
import com.example.etablissementmanagement.Models.User;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@androidx.room.Database(entities = {User.class, Etablissement.class}, version = 2, exportSchema = false)
public abstract class Database extends RoomDatabase {

    private static Database Instance;

    public abstract UserDao userDao();

    private static final String DB_NAME = "etablissement_database";

    public abstract EtablissementDao etablissementDao();

    public static synchronized Database getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return Instance;
    }

    public static RoomDatabase.Callback roomCallback = new Callback() {
        private UserDao userDao;

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(Instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;
        private EtablissementDao etablissementDao;

        private PopulateDbAsyncTask(Database db) {
            userDao = db.userDao();
            etablissementDao = db.etablissementDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.addUser(new User("admin", "admin"));
            userDao.addUser(new User("abdo", "abdo"));
            userDao.addUser(new User("ahmed", "ahmed"));

            return null;
        }
    }

}
