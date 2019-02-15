package com.example.etablissementmanagement.DataBase.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.etablissementmanagement.Models.Etablissement;

import java.util.List;

@Dao
public interface EtablissementDao {

    @Insert
    void addEtablissement(Etablissement etablissement);

    @Delete
    void deleteEtablissement(Etablissement etablissement);

    @Query("SELECT * FROM ETABLISSEMENT_TABLE")
    List<Etablissement> getEtablissements();

}
