package sm.fr.localsqlapp;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.sm.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAddContact(View view) {
        if (view == findViewById(R.id.buttonAddContact)){ //Facultatif car la méthode onAddContact() n'a qu'un seul bouton
            Intent FormIntent = new Intent(this,FormActivity.class);
            startActivity(FormIntent);
        }
    }
    //Pour récuperer le données de la base de données
    private List<Map<String, String>> getAllContacts(){
        //Instanciation de la connexion à la bdd
        DatabaseHandler db = new DatabaseHandler(this);

        //Execution de la requete de selection
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM contacts", null);

        //Instanciation de la liste qui retourne qui recevra les données
        List<Map<String, String>> contactList = new ArrayList();
        Map<String, String> contactcols = new HashMap<>();

        //Parcours du curseur (position dans la liste)
        while (cursor.moveToFirst()){
            contactcols.put("Name", cursor.getString(0));
            contactcols.put("firstName", cursor.getString(1));
            contactcols.put("email", cursor.getString(2));

            contactList.add(contactcols);
        }

        return contactList;
    }
}