package ch.ceff.android.VoyageCeff;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ActiviteListAdapter extends RecyclerView.Adapter<ActiviteListAdapter.ActiviteViewHolder> {
    private final ArrayList<Activite> activiteList; // Lien sur la source de données
    private LayoutInflater mInflater; // LayoutInflater pour utiliser le contenu du fichier xml wordlist_item

    // Le constructeur permet de mettre à jour la source de données
    public ActiviteListAdapter(Context context, ArrayList<Activite> activiteList) {
        mInflater = LayoutInflater.from(context); // LayoutInflater pour utiliser le contenu du fichier xml
        this.activiteList = activiteList; //mets a jour la source de données
    }

    @NonNull
    @Override
    public ActiviteListAdapter.ActiviteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Cette méthode crée une view (layout + textview) à partir du modèle du fichier xml, et avec cette vue elle crée un ActiviteViewHolder
        // Elle donne aussi une référence sur l'adapter qui est la classe actuelle
        View mItemView = mInflater.inflate(R.layout.daylist_activite_inside_item, viewGroup, false);
        return new ActiviteViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(ActiviteViewHolder activiteViewHolder, int i) {
        Activite currentActivite = activiteList.get(i);
        activiteViewHolder.getActiviteViewContentText().setText(currentActivite.getTitreActivite()); // Texte de la to string de l'activite
        activiteViewHolder.getActiviteViewTimeEnd().setText(currentActivite.getEndTimeStr()); // Texte de la to string de l'activite
        activiteViewHolder.getActiviteViewTimeStart().setText(currentActivite.getStartTimeStr()); // Texte de la to string de l'activite
    }

    @Override
    public int getItemCount() {
        // Retourne le nombre d'éléments dans la lsite
        return activiteList.size();
    }

    /*
    Un objet ViewHolder est utilisé pour identifier
    un composant View dans la liste ainsi que sa position dans cette dernière.
    Dans notre cas le composant identifié sera Word (id de la textView)
    */
    class ActiviteViewHolder extends RecyclerView.ViewHolder {
        //représente le composant qui affichera le mot dans le recyclerview (linearlayout + textview)
        private TextView activiteViewTimeStart;
        private TextView activiteViewTimeEnd;
        private EditText activiteViewContentText; // Edit Text
        private ActiviteListAdapter mAdapter; //représente l'adapter (classe parent)

        public ActiviteViewHolder(View itemView, ActiviteListAdapter adapter) {
            super(itemView);
            activiteViewContentText = itemView.findViewById(R.id.TextViewInsideActiviteContentText); // Recherche la textview dans le layout
            activiteViewTimeStart = itemView.findViewById(R.id.TextViewInsideActiviteStartTime); // Recherche la textview dans le layout
            activiteViewTimeEnd = itemView.findViewById(R.id.TextViewInsideActiviteEndTime); // Recherche la textview dans le layout
            this.mAdapter = adapter; // initialise l'adapteur de la view
            activiteViewContentText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // Quand on selectionne un autre EditText
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    activiteList.get(getLayoutPosition()).setTitreActivite(((EditText)v).getText().toString()); // Sauvegarde le texte dans l'activité
                }
            });
        }

        public TextView getActiviteViewTimeStart() {
            return activiteViewTimeStart;
        }

        public TextView getActiviteViewTimeEnd() {
            return activiteViewTimeEnd;
        }

        public EditText getActiviteViewContentText() {
            return activiteViewContentText;
        }

        public ActiviteListAdapter getmAdapter() {
            return mAdapter;
        }
    }

}
