package fr.sm.database;

import android.database.Cursor;

import sm.fr.localsqlapp.model.Contact;

/**
 * Created by Formation on 10/01/2018.
 */
public class ContactDAO {

    private DatabaseHandler db;

    public ContactDAO(DatabaseHandler db){
        this.db = db;
    }

    /**
     * Récuperation d'un contact en fonction de sa clé primaire (id)
     * @param id
     * @return
     */
    public Contact findOneById(int id) {
        String[] params = {String.valueOf(id)};
        String sql = "SELECT id, name, firstname, email FROM contacts WHERE id = ?";
        Cursor cursor = this.db.getReadableDatabase().rawQuery(sql, params);

        // Instanciation d'un contact
        Contact contact = new Contact();

        //Hydratation du contact
        if (cursor.moveToNext()) {
            contact.setId(cursor.getLong(0));
            contact.setName(cursor.getString(1));
            contact.setFirstname(cursor.getString(2));
            contact.setMail(cursor.getString(3));
        }

        //fermeture du curseur
        cursor.close();

        //Retourne le contact
        return contact;
    }
}
