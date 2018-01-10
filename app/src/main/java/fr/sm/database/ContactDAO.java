package fr.sm.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import sm.fr.localsqlapp.model.Contact;

public class ContactDAO {

    private DatabaseHandler db;

    public ContactDAO(DatabaseHandler db) {
        this.db = db;
    }

    /**
     * Récupération d'un contact en fonction de sa clé primaire (id)
     * @param id
     * @return
     */
    public Contact findOneById(int id) throws SQLiteException{
        //Exécution de la requête
        String[] params = {String.valueOf(id)};
        String sql = "SELECT * FROM contact WHERE id=?";
        Cursor cursor = this.db.getReadableDatabase().rawQuery(sql, params);
        //Instanciation d'un contact
        Contact contact = new Contact();

        //Hydratation du contact
        if (cursor.moveToNext()) {
            contact = hydrateContact(cursor);
        }
        //Fermeture du curseur
        cursor.close();
        return contact;
    }

    private Contact hydrateContact(Cursor cursor) {
        Contact contact = new Contact();

        contact.setId(cursor.getLong(0));
        contact.setName(cursor.getString(1));
        contact.setFirstname(cursor.getString(2));
        contact.setMail(cursor.getString(3));

        return contact;
    }

    /**
     *
     * @return
     */
    public List<Contact> findAll() throws SQLiteException{
        //Instanciation de la liste des contacts
        List<Contact> contactList = new ArrayList<>();

        //Exécution de la requête sql qui permet de récupérer tous les contacts
        String sql = "SELECT id, name, first_name,email FROM contacts";
        Cursor cursor = this.db.getReadableDatabase().rawQuery(sql, null);
        //Boucle sur le curseur pour parcourir l'ensemble des résultats de ma requête
        while(cursor.moveToNext()){
            contactList.add(this.hydrateContact(cursor));
        }

        //Fermeture du curseur
        cursor.close();

        return contactList;
    }
}

