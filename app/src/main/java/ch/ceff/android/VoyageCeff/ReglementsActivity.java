package ch.ceff.android.VoyageCeff;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ReglementsActivity extends AppCompatActivity {

    private static final String TAG = ReglementsActivity.class.getSimpleName();
    private ArrayList<String> mWordList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ReglementListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglements);

        /** PART 1 **/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //int texteCouleur = preferences.getInt("getTexteCouleur", R.style.AppTheme);
        int texteCouleur = preferences.getInt("getTexteCouleur", 0);
        setTheme(texteCouleur);
        /**  **/

        setContentView(R.layout.activity_reglements);

        /** PART 2 **/
        String couleur = preferences.getString("getCouleur", "#008577");
        int background = preferences.getInt("getBackground",R.color.blanc);
        this.getWindow().getDecorView().setBackgroundResource(background);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(couleur)));
        /** **/

        FloatingActionButton fab = findViewById(R.id.id_add_reglements);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
            }
        });

        mRecyclerView = findViewById(R.id.id_recyclerview_reglements);
        mAdapter = new ReglementListAdapter(this, mWordList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //TODO : Rename Variables
    //TODO : FIXER TOUTE CETTE CHOSE, voir aussi -> ReglementListAdapter
    private void init(String title, String content){
        int wordListSize = mWordList.size();
        //TODO : Change add ? poour pas qu'il y en ai 2 chaque fois
        mWordList.add(title);
        mWordList.add(content);
        // TILL HERE
        mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
        mRecyclerView.smoothScrollToPosition(wordListSize);
    }

    //TODO : Sort and add comments
    protected Dialog onCreateDialog(int id){

        final AlertDialog.Builder DialogBox = new AlertDialog.Builder(this);

        //Layout pour avoir 2 input
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText title = new EditText(this);
        final EditText content = new EditText(this);
        linearLayout.addView(title);
        linearLayout.addView(content);
        DialogBox.setView(linearLayout);

        //Titre de la pop-up
        DialogBox.setTitle("Ajouter un règlement");

        //Bouton "ok" pour la boite de dialogue
        DialogBox.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //TODO : Gestion de la création
                String titre = title.getText().toString().trim();
                String contenu = content.getText().toString().trim();
                init(titre, contenu);
        }
        });
        DialogBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        return DialogBox.create();
    }
}
