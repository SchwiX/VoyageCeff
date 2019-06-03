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
    private WordListAdapter mAdapter;

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
        mAdapter = new WordListAdapter(this, mWordList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
//TODO: LOTS
    private void init(String title, String contain){
        int wordListSize = mWordList.size();
        mWordList.add(title);
        mWordList.add(contain);
        mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
        mRecyclerView.smoothScrollToPosition(wordListSize);
    }

    protected Dialog onCreateDialog(int id)
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        LinearLayout lila1= new LinearLayout(this);
        lila1.setOrientation(LinearLayout.VERTICAL);
        final EditText input = new EditText(this);
        final EditText input1 = new EditText(this);
        lila1.addView(input);
        lila1.addView(input1);
        alert.setView(lila1);

        //alert.setIcon(R.drawable.icon);
        alert.setTitle("Login");

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString().trim();
                String value1 = input1.getText().toString().trim();
                init(value, value1);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        return alert.create();
    }
}
