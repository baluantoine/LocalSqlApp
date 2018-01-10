package sm.fr.localsqlapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import sm.fr.localsqlapp.model.Contact;


public class ContactArrayAdapter extends ArrayAdapter {
    //Déclaration des variables
    private Activity context;
    private List<Contact> data;
    private LayoutInflater inflater;


    public ContactArrayAdapter(@NonNull Context context,
                               @NonNull List<Contact> data) {
        super(context, 0, data);
        //Instanciation des variables
        this.data= data;
        this.context = (Activity) context;
        this.inflater =this.context.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        //Instanciation de la vue
        View view = this.inflater.inflate(R.layout.list_view_contact, parent, false);

        //Récupération des données d'une ligne
        Contact contactData = this.data.get(position);

        //Liaison entre les données et la vue
        TextView nameTextView = view.findViewById(R.id.listTextViewName);
        nameTextView.setText(contactData.getName());

        TextView firstNameTextView = view.findViewById(R.id.listTextViewFirstname);
        firstNameTextView.setText(contactData.getFirstname());

        TextView emailNameTextView = view.findViewById(R.id.listTextViewEmail);
        emailNameTextView.setText(contactData.getMail());

        return view;
    }
}

