package sm.fr.localsqlapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.sm.database.DatabaseHandler;

import fr.sm.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView contactListView;
    private List<Map<String, String>> contactList;
    private Integer seletedIndex ;
    private Map<String, String> selectedPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Référence au widget listView sur le layout
        contactListView = findViewById(R.id.contactListView);
        contactListInit();
    }

    private void contactListInit() {
        //Recuperation de la liste des contacts
        contactList = this.getAllContacts();

        //création d'u contactArrayAdapter
        ContactArrayAdapter contactAdapter = new ContactArrayAdapter(this, contactList);

        //définition de l'adapter de notre
        contactListView.setAdapter(contactAdapter);

        //Définition d'un écouteur d'evenement
        contactListView.setOnItemClickListener(this);
    }

    /*
    @Override
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        //Ajout des entrées du fichier main_option_menu au menu contextuel de l'activité
        getMenuInflater().inflate(R.menu.main_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //
        switch (item.getItemId()){
            case R.id.mainMenuItemDelete:
                this.deleteSelectedContacts();
                break;

            case R.id.mainMenuOptionEdit:
                this.editSelectedContacts();
                break;
        }
        return true;
    }

    private void  editSelectedContacts(){
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);


        Intent intent1 = new Intent(this, MainActivity.class);

        intent1.putExtra("id", selectedPerson.get("id"));
        intent1.putExtra("name", selectedPerson.get("name"));
        intent1.putExtra("first_name", selectedPerson.get("first_name"));
        intent1.putExtra("email", selectedPerson.get("email"));

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            Toast.makeText(this, "Mise à jour effetuée", Toast.LENGTH_SHORT).show();
            //Réinitialisation de la liste
            this.contactListInit();
        }
    }

    /**
     * Suppression du contact selectionné
     */
    private void  deleteSelectedContacts(){
        if (this.seletedIndex != null){

            try{
                //Définition de la requete sql et des paralmetres
                String sql = "DELETE FROM contacts WHERE id = ?";
                String[] params = {this.selectedPerson.get("id")};
                //Exécutionde la requete
                DatabaseHandler db = new DatabaseHandler(this);
                db.getWritableDatabase().execSQL(sql, params);

                //Réinitialiser ou regenerer la liste des contacts
                this.contactList = this.getAllContacts();
                this.contactListInit();
            }
            catch (SQLiteException ex){
                Toast.makeText(this, "Impossible de supprimer", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Vous devez selectionner un contact", Toast.LENGTH_LONG).show();
        }
        //Toast.makeText(this, "Suppression réussie", Toast.LENGTH_LONG).show();
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
        List<Map<String, String>> contactList = new ArrayList<>();

        //Parcours du curseur (position dans la liste)
        while (cursor.moveToNext()){
            Map<String, String> contactcols = new HashMap<>();
            contactcols.put("Name", cursor.getString(0));
            contactcols.put("first_name", cursor.getString(1));
            contactcols.put("email", cursor.getString(2));
            contactcols.put("id", cursor.getString(3));

            //Ajout
            contactList.add(contactcols);
        }

        return contactList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        this.seletedIndex = position;
        this.selectedPerson = contactList.get(position);
        Toast.makeText(this, "Ligne " + position + " cliquée ", Toast.LENGTH_SHORT).show();
    }
}