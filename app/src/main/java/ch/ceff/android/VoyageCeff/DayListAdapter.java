package ch.ceff.android.VoyageCeff;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.DayViewHolder>{
    private ArrayList<LocalDateParceable> dayList; // Lien sur la source de données
    private LayoutInflater mInflater; // LayoutInflater pour utiliser le contenu du fichier xml wordlist_item
    private DayListClickListener listener;
    private Context context;

    // Interface avec une méthode qui demande la date
    public interface DayListClickListener {
        void dayListClick(DayViewHolder dayViewHolder, LocalDateParceable currentDate);
        void addActivityInsideDay();
    }

    // Le constructeur permet de mettre à jour la source de données
    public DayListAdapter(Context context, ArrayList<LocalDateParceable> dayList, DayListClickListener listener) {
        mInflater = LayoutInflater.from(context); // LayoutInflater pour utiliser le contenu du fichier xml
        this.dayList = dayList; //mets a jour la source de données
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public DayListAdapter.DayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Cette méthode crée une view à partir du modèle du fichier xml, et avec cette vue elle crée un DayViewHolder
        // Elle donne aussi une référence sur l'adapter qui est la classe actuelle
        View mItemView = mInflater.inflate(R.layout.daylist_item, viewGroup, false);
        return new DayViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(final DayViewHolder dayViewHolder, int i) {
        // Objet actuellement cliqué
        final LocalDateParceable currentDate = dayList.get(i);
        dayViewHolder.getDayView().setText(currentDate.getLocalDate().format(DateTimeFormatter.ofPattern("EEEE d MMMM", Locale.FRANCE))); // format --> ex 21 Lundi

        // Set le listener sur la view --> layout avec tout dedans du holder
        dayViewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Quand on clic sur un jour
                // Appel la méthode dans la classe CalendrierActivity
                listener.dayListClick(dayViewHolder, currentDate);
            }
        });

        dayViewHolder.getDayInsideAdd().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Quand on ajoute une activite
                listener.addActivityInsideDay();
            }
        });
    }

    public LocalDateParceable getLocalDateAtPosition(int position){
        return dayList.get(position);
    }

    @Override
    public int getItemCount() {
        // Retourne le nombre d'éléments dans la lsite
        if(dayList != null){
            return dayList.size();
        }else{
            return 0;
        }
    }

    public void setDayList(ArrayList<LocalDateParceable> localDateParceables){
        dayList = localDateParceables;
        notifyDataSetChanged();
    }

    /*
    Un objet ViewHolder est utilisé pour identifier
    un composant View dans la liste ainsi que sa position dans cette dernière.
    Dans notre cas le composant identifié sera Word (id de la textView)
    */
    class DayViewHolder extends RecyclerView.ViewHolder {
        //représente le composant qui affichera le mot dans le recyclerview (linearlayout + textview)
        private final String TAG = CalendrierActivity.class.getSimpleName();
        private GridLayout grid;
        private TextView dayView;
        private TextView dayInsideAdd;
        private DayListAdapter mAdapter; //représente l'adapter (classe parent)
        private View itemView;
        private Context context;

        private ArrayList<Activite> activiteArrayList; // Array list qui contient les activite
        private RecyclerView mRecyclerViewActivite;
        private ActiviteListAdapter mAdapterActivite;

        // Pour sauvegarder dans la base de donnee
        private ActiviteViewModel activiteViewModel;
        boolean observerSet = false;

        public DayViewHolder(View itemView, DayListAdapter adapter) {
            super(itemView);
            this.grid = itemView.findViewById(R.id.GridView); // Init la grid du holder
            this.dayView = itemView.findViewById(R.id.TextViewAddDay); // Recherche la textview dans le layout
            this.dayInsideAdd = itemView.findViewById(R.id.TextViewInsideAdd); // Recherche la textview dans le layout
            this.mAdapter = adapter; // initialise l'adapteur de la view
            this.itemView = itemView;
            this.context = itemView.getContext();

            activiteArrayList = new ArrayList<>();

            // View model des activite
            activiteViewModel = ViewModelProviders.of((FragmentActivity) context).get(ActiviteViewModel.class);

            //Initialisation le recyclerview
            mRecyclerViewActivite = itemView.findViewById(R.id.recyclerviewActivite);
            //Crée un objet ActiviteListAdapter avec la source de donnée
            mAdapterActivite = new ActiviteListAdapter(itemView.getContext(), activiteArrayList);
            //Connexion du recyclerview qui est connecté avec l'adapter qui contient la liste de donnée
            mRecyclerViewActivite.setAdapter(mAdapterActivite);
            //Définir un gestionnaire de layout par défaut pour RecyclerView
            mRecyclerViewActivite.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

            ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder target, int i) {
                    int position = target.getAdapterPosition();
                    //activiteArrayList.remove(position);
                    activiteViewModel.delete(mAdapterActivite.getActiviteAtPosition(position));
                    mAdapter.notifyDataSetChanged();
                }
            });

            helper.attachToRecyclerView(mRecyclerViewActivite);
        }

        public void setLiveDataObserver(String dayId){
            if(!observerSet){ // Permet de set l'observeur une seul fois
                getActiviteViewModel().setDayId(dayId); // Set l'id du jour de l'activite au view model
                getActiviteViewModel().getAllActivites().observe((LifecycleOwner) context, new Observer<List<Activite>>() {
                    @Override
                    public void onChanged(@Nullable List<Activite> activites) {
                        getmAdapterActivite().setActiviteList((ArrayList<Activite>) activites);
                    }
                });
                observerSet = true;
            }
        }

        public ArrayList<Activite> getActiviteArrayList() {

            return activiteArrayList;
        }

        public View getItemView() {

            return itemView;
        }

        public GridLayout getGrid() {

            return grid;
        }

        public TextView getDayView() {
            return dayView;
        }

        public TextView getDayInsideAdd() {
            return dayInsideAdd;
        }

        public ActiviteListAdapter getmAdapterActivite() {
            return mAdapterActivite;
        }

        public RecyclerView getmRecyclerViewActivite() {
            return mRecyclerViewActivite;
        }

        public ActiviteViewModel getActiviteViewModel() {
            return activiteViewModel;
        }

        public void setActiviteViewModel(ActiviteViewModel activiteViewModel) {
            this.activiteViewModel = activiteViewModel;
        }
    }
}
