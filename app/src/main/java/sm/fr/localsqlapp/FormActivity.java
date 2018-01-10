package sm.fr.localsqlapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ActionBar;

import fr.sm.database.DatabaseHandler;

public class FormActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText firstNameEditText;
    private EditText emailEditText;
    private String contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        //création d'une barre d'action retour
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //Récupération des données envoyées pour modification
        Intent intention = getIntent();
        String name = intention.getStringExtra("name");
        String first_name = intention.getStringExtra("first_name");
        String email = intention.getStringExtra("email");
        String id = intention.getStringExtra("id");

        //Récupération de L'ID dans la variable globale
        this.contactId = id;
        //Référence aux edittext
        this.nameEditText = findViewById(R.id.editTextNom);
        this.firstNameEditText = findViewById(R.id.editTextPrenom);
        this.emailEditText = findViewById(R.id.editTextEmail);

        //Affichage des données dans les champs de saisie
        this.firstNameEditText.setText(first_name);
        this.nameEditText.setText(name);
        this.emailEditText.setText(email);


    }
    public void onValid(View v){

        Button clickedButton = (Button) v;
        //récupération de la saisie de l'utilisateur
        String name = ((EditText) findViewById(R.id.editTextNom)).getText().toString();
        String firstName = ((EditText) findViewById(R.id.editTextPrenom)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String isMessage = "";

        //Instanciation de la connexion à la base de données
        DatabaseHandler db = new DatabaseHandler(this);//responsable de la gestion des demandes CRUD
        //définition des données à insérer
        ContentValues insertValues = new ContentValues();
        insertValues.put("first_name",firstName);
        insertValues.put("name",name);
        insertValues.put("email", email);

        //insertion des données
        try {
            if (contactId != null){
                String[] params = {contactId};
                db.getWritableDatabase().update("contacts",
                        insertValues,
                        "id=?",
                        params);
                setResult(RESULT_OK);
                finish();
            }
            else {
                db.getWritableDatabase().insert("contacts", null, insertValues);
                isMessage = "Insertion réussie";

            }
        } catch (SQLiteException ex) {
            Log.e("SQL EXCEPTION", ex.getMessage());
            isMessage = "Erreur insertion";
        }
        Toast.makeText(this, isMessage, Toast.LENGTH_LONG).show();
        //on efface les zones de saisies
        ((EditText) findViewById(R.id.editTextNom)).setText("");
        ((EditText) findViewById(R.id.editTextPrenom)).setText("");
        ((EditText) findViewById(R.id.editTextEmail)).setText("");
    }


}
