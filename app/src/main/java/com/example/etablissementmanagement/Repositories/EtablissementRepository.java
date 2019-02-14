package com.example.etablissementmanagement.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.example.etablissementmanagement.DAO.EtablissementDao;
import com.example.etablissementmanagement.DataBase.Database;
import com.example.etablissementmanagement.Models.Etablissement;
import com.example.etablissementmanagement.OnLoadCompleted;

import java.util.List;

public class EtablissementRepository {

    private EtablissementDao etablissementDao;
    private List<Etablissement> etablissements;

    public EtablissementRepository(Application application) {
        Database database = Database.getInstance(application);
        etablissementDao = database.etablissementDao();
        //etablissements = etablissementDao.getEtablissements();
    }

    public EtablissementDao getEtablissementDao() {
        return etablissementDao;
    }

    public void addEtablissement(Etablissement etablissement) {
        new addEtablissementAsyncTask(etablissementDao).execute(etablissement);
    }

    public List<Etablissement> getEtablissements() {
        return etablissements;
    }

    public void deleteEtablissement(Etablissement etablissement) {
        new deleteEtablissementAsyncTask(etablissementDao).execute(etablissement);
    }

    public static class deleteEtablissementAsyncTask extends AsyncTask<Etablissement, Void, Void> {

        private EtablissementDao etablissementDao;

        private deleteEtablissementAsyncTask(EtablissementDao etablissementDao) {
            this.etablissementDao = etablissementDao;
        }

        @Override
        protected Void doInBackground(Etablissement... etablissements) {
            etablissementDao.deleteEtablissement(etablissements[0]);
            return null;
        }
    }

    public static class addEtablissementAsyncTask extends AsyncTask<Etablissement, Void, Void> {

        private EtablissementDao etablissementDao;

        private addEtablissementAsyncTask(EtablissementDao etablissementDao) {
            this.etablissementDao = etablissementDao;
        }

        @Override
        protected Void doInBackground(Etablissement... etablissements) {
            etablissementDao.addEtablissement(etablissements[0]);
            return null;
        }
    }

    public static class getEtablissementAsyncTask extends AsyncTask<Void, Void, List<Etablissement>> {

        private EtablissementDao etablissementDao;
        private List<Etablissement> etablissementsTask;
        OnLoadCompleted callback;

        public getEtablissementAsyncTask(EtablissementDao etablissementDao, OnLoadCompleted callback) {
            this.etablissementDao = etablissementDao;
            this.callback = callback;
        }

        @Override
        protected List<Etablissement> doInBackground(Void... voids) {
            return etablissementDao.getEtablissements();
        }

        @Override
        protected void onPostExecute(List<Etablissement> etablissements) {
            super.onPostExecute(etablissements);
            etablissementsTask = etablissements;
            this.callback.OnLoadCompleted();
        }

        public List<Etablissement> getEtablissements() {
            return etablissementsTask;
        }
    }
}
