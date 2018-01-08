package sm.fr.localsqlapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.sm.database.DatabaseHandler;

public class FormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
    }

    public void onValid(View v){

        Button clickedButton = (Button) v;

        //Récupération de la saisie de l'utilisateur
        String name = ((EditText) findViewById(R.id.editNom)).getText().toString();
        String firstName  = ((EditText) findViewById(R.id.editPrenom)).getText().toString();
        String email  = ((EditText) findViewById(R.id.editMail)).getText().toString();

        //Instanciation de la connexion à la BDD
        DatabaseHandler db = new DatabaseHandler(this); //responsable de la gestion des demandes CRUD

        //Définition des données à insérer
        ContentValues insertValues = new ContentValues();
        insertValues.put("first_name", firstName);
        insertValues.put("name", name);
        insertValues.put("email", email);

        //insertion des données
        try{
            db.getWritableDatabase().insert("contacts", null, insertValues);
            Toast.makeText(this, "Insertion OK", Toast.LENGTH_LONG).show();
        } catch (SQLiteException ex){
            Log.e("SQL_EXCEPTION", ex.getMessage());
            Toast.makeText(this, "Insertion KO", Toast.LENGTH_LONG).show();
        }
       // Toast.makeText(this, "Réussi", Toast.LENGTH_LONG).show();

    }
}
