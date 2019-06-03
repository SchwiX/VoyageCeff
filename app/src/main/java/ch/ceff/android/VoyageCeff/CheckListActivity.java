package ch.ceff.android.VoyageCeff;

import android.content.Context;
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
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class CheckListActivity extends AppCompatActivity {

    // Variables
    private static final String TAG = CheckListActivity.class.getSimpleName();
    private ArrayList<String> mCheckList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CheckListAdapter mAdapter;
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //app design and style
        /** PART 1 **/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //int texteCouleur = preferences.getInt("getTexteCouleur", R.style.AppTheme);
        int texteCouleur = preferences.getInt("getTexteCouleur", 0);
        setTheme(texteCouleur);
        /**  **/

        setContentView(R.layout.activity_check_list);

        /** PART 2 **/
        String couleur = preferences.getString("getCouleur", "#008577");
        int background = preferences.getInt("getBackground",R.color.blanc);
        this.getWindow().getDecorView().setBackgroundResource(background);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(couleur)));
        /** **/


        init();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "I'm trying to add something");
                showAddItemDialog(CheckListActivity.this);
            }
        });
    }

    //TODO : Change to card view boxes
    private void init() {
        mRecyclerView = findViewById(R.id.check_list_recycler_view);
        mAdapter = new CheckListAdapter(this, mCheckList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checklist_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check_list_menu:
                //TODO: Edit -> Shows the [X] buttons
                Log.d(TAG, "Hide/Show delete buttons");

                /*
                if(mRecyclerView.findViewById(R.id.button).getVisibility() == View.INVISIBLE){
                    mRecyclerView.findViewById(R.id.button).setVisibility(View.VISIBLE);
                }else if(mRecyclerView.findViewById(R.id.button).getVisibility() == View.VISIBLE){
                    mRecyclerView.findViewById(R.id.button).setVisibility(View.INVISIBLE);
                }*/

                /**
                 * WIP:
                 * 1. Gerer tout ici -> Fail : marque que sure le premier
                 * 2. Pointer sur checkListAdapter -> Fail : Can't access ViewHolder
                 * 3. Give up ... ?
                 **/

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAddItemDialog(Context c) {
        //TODO : Cleaner dialog box ! (Try use checklist_dialog_box.xml)
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Ajout d'une tâche");
        builder.setMessage("Titre de la tâche");

        // Set up the input
        final EditText input = new EditText(c);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                newBox(m_Text);
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void newBox(String s) {
        int wordListSize = mCheckList.size();
        mCheckList.add(s);
        mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
        mRecyclerView.smoothScrollToPosition(wordListSize);
    }
}
