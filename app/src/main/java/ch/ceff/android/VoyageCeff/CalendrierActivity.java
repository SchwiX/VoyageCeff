package ch.ceff.android.VoyageCeff;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CalendrierActivity extends AppCompatActivity implements DayListAdapter.DayListClickListener {

    private static final String TAG = CalendrierActivity.class.getSimpleName();
    public static final int ADD_DAY_REQUEST = 1;
    public static final int SELECT_TIME_REQUEST = 2;
    private FloatingActionButton fab;
    private Context context;
    private ArrayList<LocalDateParceable> dayList; // Liste pour l'adapter
    private RecyclerView mRecyclerView;
    private DayListAdapter mAdapter;
    private DayListAdapter.DayViewHolder dayViewHolderOpen; // DayViewHolder actuellement ouvert

    // Sauvegarde de l'adapter de daylist
    private static final String LIST_STATE = "list_state";
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    private Parcelable savedRecyclerLayoutState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** PART 1 **/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //int texteCouleur = preferences.getInt("getTexteCouleur", R.style.AppTheme);
        int texteCouleur = preferences.getInt("getTexteCouleur", 0);
        setTheme(texteCouleur);
        /**  **/

        setContentView(R.layout.activity_calendrier);

        /** PART 2 **/
        String couleur = preferences.getString("getCouleur", "#008577");
        int background = preferences.getInt("getBackground",R.color.blanc);
        this.getWindow().getDecorView().setBackgroundResource(background);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(couleur)));
        /** **/
        context = this;
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CalendrierAjouterJourActivity.class);
                Log.d(TAG, "Démarre l'activité CalendrierAjouterJourActivity pour ajouter un jour dans le calendrier");
                startActivityForResult(intent, ADD_DAY_REQUEST);
            }
        });

        // Initialise la dayList
        if(savedInstanceState != null){
            dayList = savedInstanceState.getParcelableArrayList(LIST_STATE);
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        }else{
            dayList = new ArrayList<>();
        }

        //Initialisation le recyclerview
        mRecyclerView = findViewById(R.id.recyclerview);
        //Crée un objet DayListAdapter avec la source de donnée
        mAdapter = new DayListAdapter(this, dayList, this);
        //Connexion du recyclerview qui est connecté avec l'adapter qui contient la liste de donnée
        mRecyclerView.setAdapter(mAdapter);
        //Définir un gestionnaire de layout par défaut pour RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Resorte layout manager position
        if(savedRecyclerLayoutState != null){
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_DAY_REQUEST) { // Identifie l'activité qui se termine
            if(resultCode == RESULT_OK) { // L'état final de l'activité
                Log.d(TAG, "L'activité ADD_DAY_REQUEST (CalendrierAjouterJourActivity) s'est terminée normalement.");
                int dateYear = data.getIntExtra(CalendrierAjouterJourActivity.EXTRA_REPLY_YEAR, 2019);
                int dateMonth = data.getIntExtra(CalendrierAjouterJourActivity.EXTRA_REPLY_MONTH, 1);
                int dateDay = data.getIntExtra(CalendrierAjouterJourActivity.EXTRA_REPLY_DAY, 1);
                LocalDateParceable localDate = new LocalDateParceable(dateYear, dateMonth, dateDay); // Crée la date
                //Toast.makeText(this, String.format("%tD", localDate), Toast.LENGTH_SHORT).show();
                addDay(localDate); // Ajoute le jour dans la dayList
            }
        }else if(requestCode == SELECT_TIME_REQUEST){
            if(resultCode == RESULT_OK){
                Log.d(TAG, "L'activité TimePickerActivity s'est terminée normalement.");
                int startHour = data.getIntExtra(TimePickerActivity.EXTRA_REPLY_START_HOUR, 12);
                int startMinute = data.getIntExtra(TimePickerActivity.EXTRA_REPLY_START_MINUTE, 30);
                int endHour = data.getIntExtra(TimePickerActivity.EXTRA_REPLY_END_HOUR, 13);
                int endMinute = data.getIntExtra(TimePickerActivity.EXTRA_REPLY_END_MINUTE, 30);
                String titreActivite = data.getStringExtra(TimePickerActivity.EXTRA_REPLY_TITRE_ACTIVITE);
                // Ajoute l'activité
                dayViewHolderOpen.getActiviteArrayList().add(new Activite(startHour, startMinute, endHour, endMinute, titreActivite));
                sortActivityArray(dayViewHolderOpen.getActiviteArrayList());
                dayViewHolderOpen.getmRecyclerViewActivite().setVisibility(View.VISIBLE);
                dayViewHolderOpen.getmAdapterActivite().notifyDataSetChanged(); // Notifie que on a ajouté une activité
            }
        }
    }


    public void addDay(LocalDateParceable localDate){
        dayList.add(localDate);
        sortDayListArray(dayList);
        mAdapter.notifyDataSetChanged(); // On notifie que on a ajoute qqch dedans
    }

    // Met l'attibut visibility Gone a tous les enfants sauf le jour
    public void hideChildrenSinceSecondView(DayListAdapter.DayViewHolder dayViewHolderOpen){
        for(int i = 1; i < dayViewHolderOpen.getGrid().getChildCount(); i++) {
            dayViewHolderOpen.getGrid().getChildAt(i).setVisibility(View.GONE);  // GONE --> n'est pas inclu dans le rendu
        }
    }

    // On redéfinit la méthode de DayListAdapter.DayListClickListener
    @Override
    public void dayListClick(DayListAdapter.DayViewHolder dayViewHolder) {
        if(dayViewHolderOpen != null){ // On a déja ouvert un jour
            hideChildrenSinceSecondView(dayViewHolderOpen); // Ferme le jour
        }
        dayViewHolderOpen = dayViewHolder; // Init le jour actuellement ouvert
        dayViewHolderOpen.getDayInsideAdd().setVisibility(View.VISIBLE); // Affiiche la flèche pour ajouter une activité
        dayViewHolderOpen.getmRecyclerViewActivite().setVisibility(View.VISIBLE);
    }

    // On redéfinit la méthode de DayListAdapter.DayListClickListener
    @Override
    public void addActivityInsideDay() {
        // Quand on veut ajouter une activité dans le jour cliqué sur DayListAdapter
        // Ouvre l'activité pour choisir l'heure de début et fin
        Log.d(TAG, "Démarre l'activité TimePickerActivity pour choisir l'heure de début et fin de l'activité");
        Intent intent = new Intent(context, TimePickerActivity.class);
        startActivityForResult(intent, SELECT_TIME_REQUEST); // Le resultat init activiteCurrent
    }

    public void sortActivityArray(ArrayList<Activite> activites){
        Collections.sort(activites, new Comparator<Activite>() {
            @Override
            public int compare(Activite object1, Activite object2) {
                return object1.getStartTime().compareTo(object2.getEndTime()) ;
            }
        });
    }

    public void sortDayListArray(ArrayList<LocalDateParceable> dayList){
        Collections.sort(dayList, new Comparator<LocalDateParceable>() {
            @Override
            public int compare(LocalDateParceable object1, LocalDateParceable object2) {
                return object1.getLocalDate().compareTo(object2.getLocalDate()) ;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        // Changement de configuration
        savedInstanceState.putParcelableArrayList(LIST_STATE, dayList);
        savedInstanceState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }
}
