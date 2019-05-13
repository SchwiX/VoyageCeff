package ch.ceff.android.VoyageCeff;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;

public class ReglementsActivity extends AppCompatActivity {

    private static final String TAG = ReglementsActivity.class.getSimpleName();
    private ArrayList<String> mWordList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglements);

        FloatingActionButton fab = findViewById(R.id.id_add_reglements);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        /* TODO : Finir
        private void init(){
            for(int i = 0; i < 20; i++) {
                mWordList.add("Mot " + i);
            }
        }*/

        //Initialisation de la variable mRecyclerView
        mRecyclerView = findViewById(R.id.id_recyclerview_reglements);
        //Création d'un Adapter avec la source de données
        mAdapter = new WordListAdapter(this, mWordList);
        //Connexion de l'Adapter avec le composant RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        //Définir un gestionnaire de layout par défaut pour RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
