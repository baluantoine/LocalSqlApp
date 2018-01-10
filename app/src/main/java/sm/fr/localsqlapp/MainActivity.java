package sm.fr.localsqlapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.sm.database.ContactDAO;
import fr.sm.database.DatabaseHandler;
import sm.fr.localsqlapp.model.Contact;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView contactListView;
    private List<Map<String,String>> contactList;
    private Integer selectedIndex;
    private Map<String,String> selectedPerson;
    private final String LIFE_CYCLE = "cycle de vie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LIFE_CYCLE,"onCreate");

        //référence au widget ListView sur le layout
        contactListView = findViewById(R.id.contactListView);
        contactListInit();

        //Récupération des données persistées dans le Bundle
        if (savedInstanceState != null){
            this.selectedIndex = savedInstanceState.getInt("selectedIndex");
            if (this.selectedIndex != null){
                this.selectedPerson = this.contactList.get(this.selectedIndex);
                contactListView.setSelection(this.selectedIndex);
                Log.i(LIFE_CYCLE,"selection : " + contactListView.getSelectedItem());
            }
        }

    }
    private void  testDAO(){

        try{
            ContactDAO dao = new ContactDAO(new DatabaseHandler(this));
            Contact contact = dao.findOneById(1);
            if (contact.getName() == null){
                Log.i("DAO", "Contact inconnu");
            }
            Log.i("DAO", contact.getName());
        }catch(SQLiteException ex){
            Log.i("DEBUG", ex.getMessage());
        }
    }

    private void contactListInit() {
        //récupération de la liste des contacts
        contactList = this.getAllContacts();
        //Création d'un contactArrayAdapter
        ContactArrayAdapter contactAdapter = new ContactArrayAdapter(this,contactList);
        //Définition de l'adapter de notre listView
        contactListView.setAdapter(contactAdapter);

        //Définition d'un écouteur d'évenement pour OnItemSelectedListener
        contactListView.setOnItemClickListener(this);
    }


    /**
     * Création du menu d'option
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Ajout des entrées du fichier main_option_menu
        //au menu contextuel de l'activité
        getMenuInflater().inflate(R.menu.main_option_menu, menu);
        return true;
    }

    /**
     * Gestion des choix d'un élément de menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainMenuItemDelete:
                this.deleteSelectedContact();
                break;
            case R.id.mainMenuOptionEdit:
                this.editSelectedContact();
                break;
        }
        return true;
    }
    private void editSelectedContact(){
        if (selectedIndex != null) {

            //Création d'une intention
            Intent FormIntent = new Intent(this, FormActivity.class);
            //Passage des paramètres à l'intention
            FormIntent.putExtra("id",selectedPerson.get("id"));
            FormIntent.putExtra("first_name",selectedPerson.get("first_name"));
            FormIntent.putExtra("name",selectedPerson.get("name"));
            FormIntent.putExtra("email",selectedPerson.get("email"));
            //Lancement de l'activité FormActivity
            startActivityForResult(FormIntent,1);
        }
        else {
            Toast.makeText(this,
                    "Veuillez selectionner une contact",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            Toast.makeText(this, "Mise à jour effectuée", Toast.LENGTH_SHORT).show();
            //Reinitialisation de l'affichage de la liste
            this.contactListInit();
        }
    }

    /**
     * Suppression du contact selectionné
     */
    private void deleteSelectedContact(){
        //supprimer uniquement si un contact est selectionné
        if (selectedIndex != null){

            try {
                //Définition de la requête sql et des paramètres
                String sql = "DELETE FROM contacts WHERE id=?";
                String[] params = {this.selectedPerson.get("id")};
                //Exécution de la requëte
                DatabaseHandler db = new DatabaseHandler(this);
                db.getWritableDatabase().execSQL(sql, params);
                //Réinitialisation de la liste des contacts
                this.contactList = this.getAllContacts();
                this.contactListInit();
            }
            catch(SQLiteException ex){
                Toast.makeText(this,
                        "Impossible de supprimer",
                        Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this,
                    "Veuillez selectionner une contact",
                    Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Lancement de l'activité formulaire au clic sur un bouton
     * @param view
     */

    public void onAddContact(View view){
        if (view == findViewById(R.id.buttonAddContact)) { // facultaif car onAddContact est utilisé pour un seul bouton
            Intent FormIntent = new Intent(this, FormActivity.class);
            startActivity(FormIntent);
        }

    }
    //récupérer les enregistrements de la base de données contacts
    private List<Map<String,String>> getAllContacts(){
        //Instanciation de la connexion à la base de données
        DatabaseHandler db = new DatabaseHandler(this);
        //Exécute une requete de sélection
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT name, first_name, email,id FROM contacts", null);
        //Instanciation de la liste qui recevra les données
        List<Map<String,String>> contactList = new ArrayList<>();


        //Parcourir les résultats de la requête (parcours du curseur)
        while (cursor.moveToNext()){
            Map<String,String> contactCols = new HashMap<>();
            //Remplissage du tableau associatif en fonction des données du curseur
            contactCols.put("name",cursor.getString(0));
            contactCols.put("first_name",cursor.getString(1));
            contactCols.put("email",cursor.getString(2));
            contactCols.put("id",cursor.getString(3));

            //Ajout du map à la liste
            contactList.add(contactCols);
        }


        return contactList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        this.selectedIndex = position;
        this.selectedPerson = contactList.get(position);
        Toast.makeText(this, "Ligne "+ position + " cliquée", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LIFE_CYCLE, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LIFE_CYCLE,"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LIFE_CYCLE,"onReseume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LIFE_CYCLE,"onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LIFE_CYCLE, "onStop");
    }

    /**
     * Persistance des données avant destruction de l'activité
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("selectedIndex", this.selectedIndex);
        super.onSaveInstanceState(outState);
    }
}

