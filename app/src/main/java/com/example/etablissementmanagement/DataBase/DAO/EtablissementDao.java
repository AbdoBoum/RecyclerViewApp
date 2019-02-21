package com.example.etablissementmanagement.DataBase.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

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
