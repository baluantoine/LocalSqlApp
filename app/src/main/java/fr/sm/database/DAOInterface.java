package fr.sm.database;

import android.database.sqlite.SQLiteException;

import java.util.List;

import sm.fr.localsqlapp.model.Contact;

/**
 * Created by Formation on 11/01/2018.
 */

//Générique ou marqeur DTO, possibilité d'insérer plusieurs génériques
interface DAOInterface <DTO> {
    Contact findOneById(int id) throws SQLiteException;

    List<DTO> findAll() throws SQLiteException;

    //Suppression d'un contact en fonction de sa clé primaire
    void deleteOneById(Long id) throws SQLiteException;

    void persist(DTO entity);
}
