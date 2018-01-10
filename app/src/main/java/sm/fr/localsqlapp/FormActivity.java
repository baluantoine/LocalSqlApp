package sm.fr.localsqlapp;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
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
    private EditText nameEditext;
    private EditText firstNameEditext;
    private EditText nemailEditext;
    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // ActionBar vue qui affiche le titre de l’activité, des actions, et un menu tout à droite pour les actions supplémentaires
        ActionBar actionBar = getActionBar();
        if (actionBar != null){//actionBar different de null
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Récupération des données
        Intent intent        = getIntent();
        String name         = getIntent().getStringExtra("name");
        String firstName    = getIntent().getStringExtra("first_name");
        String email        = getIntent().getStringExtra("email");
        String id           = getIntent().getStringExtra("id");

        //Reference des EdiText
        this.contactId = id;
        this.nameEditext = findViewById(R.id.editTextNom);
        this.firstNameEditext = findViewById(R.id.editTextNom);
        this.nemailEditext = findViewById(R.id.editTextMail);

        //Affichage des données dans les champs de saisie
        this.nameEditext.setText(name);
        this.firstNameEditext.setText(firstName);
        this.nemailEditext.setText(email);
    }

    public void onValid(View v){


        Button clickedButton = (Button) v;

        //Récupération de la saisie de l'utilisateur
        String name         = ((EditText) findViewById(R.id.editTextNom)).getText().toString();
        String firstName    = ((EditText) findViewById(R.id.editTextPrenom)).getText().toString();
        String email        = ((EditText) findViewById(R.id.editTextMail)).getText().toString();
        String isMessage    = " ";

        //Instanciation de la connexion à la BDD
        DatabaseHandler db = new DatabaseHandler(this); //responsable de la gestion des demandes CRUD

        //Définition des données à insérer
        ContentValues insertValues = new ContentValues();
        insertValues.put("first_name", firstName);
        insertValues.put("name", name);
        insertValues.put("email", email);

        //Insertion des données
        try{

            if  (this.contactId != null){
                //Mise à jour d'un contact existant
                String[] params = {contactId};
                db.getWritableDatabase().update("contacts", insertValues, "id=?", params);
                setResult(RESULT_OK);
                finish();

            }
            else{
                //Insertion d'un nouveau contact
                db.getWritableDatabase().insert("contacts", null, insertValues);
                isMessage = "Insertion Réussie";
            }
        }

        catch (SQLiteException ex){
            Log.e("SQL_EXCEPTION", ex.getMessage());
            isMessage = "Erreur insertion";
            //Pour Réinitialiser les champs du formulaire
            ((EditText) findViewById(R.id.editTextNom)).setText("");
            ((EditText) findViewById(R.id.editTextPrenom)).setText("");
            ((EditText) findViewById(R.id.editTextMail)).setText("");
        }
        Toast.makeText(this, isMessage, Toast.LENGTH_LONG).show();
        //Pour effacer les zones de saisies lorsqu'on clique sur valider
        ((EditText) findViewById(R.id.editTextNom)).setText("");
        ((EditText) findViewById(R.id.editTextPrenom)).setText("");
        ((EditText) findViewById(R.id.editTextMail)).setText("");
    }
}
