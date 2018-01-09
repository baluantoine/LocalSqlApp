package sm.fr.localsqlapp;

import android.app.ActionBar;
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

        // ActionBar vue qui affiche le titre de l’activité, des actions, et un menu tout à droite pour les actions supplémentaires
        ActionBar actionBar = getActionBar();
        if (actionBar != null){//actionBar different de null
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onValid(View v){

        Button clickedButton = (Button) v;

        //Récupération de la saisie de l'utilisateur
        String name = ((EditText) findViewById(R.id.editTextNom)).getText().toString();
        String firstName  = ((EditText) findViewById(R.id.editTextPrenom)).getText().toString();
        String email  = ((EditText) findViewById(R.id.editTextMail)).getText().toString();
        String isMessage = " ";

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
           isMessage = "Insertion Réussie";
        }

        catch (SQLiteException ex){
            Log.e("SQL_EXCEPTION", ex.getMessage());
            isMessage = "Erreur insertion";
        }
        Toast.makeText(this, isMessage, Toast.LENGTH_LONG).show();

        //Pour effacer les zones de saisies lorsqu'on clique sur valider
        ((EditText) findViewById(R.id.editTextNom)).setText("");
        ((EditText) findViewById(R.id.editTextPrenom)).setText("");
        ((EditText) findViewById(R.id.editTextMail)).setText("");
    }
}
