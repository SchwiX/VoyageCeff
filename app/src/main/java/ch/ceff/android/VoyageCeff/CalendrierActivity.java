package ch.ceff.android.VoyageCeff;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private LocalDateParceable currentDate; // Date actuellement ouverte
    // Pour sauvegarder dans la base de donnee
    private LocalDateParceableViewModel localDateParceableViewModel;
    private ActiviteViewModel actualActiviteViewModel;

    //TODO : Regarder le truc qui fait que les activités d'un jour se déplacent au jour qu'on rajoute

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

        dayList = new ArrayList<>();

        //Initialisation le recyclerview
        mRecyclerView = findViewById(R.id.recyclerview);
        //Crée un objet DayListAdapter avec la source de donnée
        mAdapter = new DayListAdapter(this, dayList, this);
        //Connexion du recyclerview qui est connecté avec l'adapter qui contient la liste de donnée
        mRecyclerView.setAdapter(mAdapter);
        //Définir un gestionnaire de layout par défaut pour RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // View Model
        localDateParceableViewModel = ViewModelProviders.of(this).get(LocalDateParceableViewModel.class);
        localDateParceableViewModel.getAllLocalDatesParceable().observe(this, new Observer<List<LocalDateParceable>>() {
            @Override
            public void onChanged(@Nullable List<LocalDateParceable> localDateParceables) {
                // Ajoute la list dans la base
                mAdapter.setDayList((ArrayList<LocalDateParceable>) localDateParceables);
                Log.d(TAG, "Update la liste depuis la base de donnée");
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int i) {
                int position = target.getAdapterPosition();
                //dayList.remove(position);
                localDateParceableViewModel.delete(mAdapter.getLocalDateAtPosition(position));
                mAdapter.notifyDataSetChanged();
            }
        });

        helper.attachToRecyclerView(mRecyclerView);
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
                Activite ajoutActivite = new Activite(startHour, startMinute, endHour, endMinute, titreActivite);

                // Donne l'id du jour actuellement ouvert a l'activité
                dayViewHolderOpen.getActiviteArrayList().add(ajoutActivite);
                sortActivityArray(dayViewHolderOpen.getActiviteArrayList());
                dayViewHolderOpen.getmRecyclerViewActivite().setVisibility(View.VISIBLE);

                ajoutActivite.setIdDay(currentDate.getId()); // Ajoute l'id du jour a l'activite

                dayViewHolderOpen.getActiviteViewModel().insert(ajoutActivite); // Ajoute l'activite dans la base

                dayViewHolderOpen.getmAdapterActivite().notifyDataSetChanged(); // Notifie que on a ajouté une activité
            }
        }
    }

    public void addDay(LocalDateParceable localDate){
        dayList.add(localDate);
        sortDayListArray(dayList);
        localDateParceableViewModel.insert(localDate); // Ajoute la date dans la base de donnée
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
    public void dayListClick(DayListAdapter.DayViewHolder dayViewHolder, LocalDateParceable currentDate) {
        if(dayViewHolderOpen != null){ // On a déja ouvert un jour
            hideChildrenSinceSecondView(dayViewHolderOpen); // Ferme le jour
        }
        this.dayViewHolderOpen = dayViewHolder; // Init le jour actuellement ouvert
        Log.d(TAG, "Mets a jour le dayviewholder actuel");
        this.currentDate = currentDate; // Met a jour la date actuelle
        Log.d(TAG, "Mets a jour la date actuelle");
        dayViewHolderOpen.getDayInsideAdd().setVisibility(View.VISIBLE); // Affiiche la flèche pour ajouter une activité
        dayViewHolderOpen.getmRecyclerViewActivite().setVisibility(View.VISIBLE);
        dayViewHolderOpen.setLiveDataObserver(currentDate.getId()); // Met l'observeur a jour sur le live data de la liste d'activites si pas encore fait
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
}
