package ch.ceff.android.VoyageCeff;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class CheckListActivity extends AppCompatActivity {

    private static final String TAG = CheckListActivity.class.getSimpleName();
    private ArrayList<String> mCheckList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CheckListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** PART 1 **/
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //int texteCouleur = preferences.getInt("getTexteCouleur", R.style.AppTheme);
        int texteCouleur = preferences.getInt("getTexteCouleur", 0);
        setTheme(texteCouleur);
        /**  **/

        setContentView(R.layout.activity_check_list);
        /** **/
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(couleur)));
        this.getWindow().getDecorView().setBackgroundResource(background);
        int background = preferences.getInt("getBackground",R.color.blanc);
        String couleur = preferences.getString("getCouleur", "#008577");
        /** PART 2 **/


        init();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "I'm trying to add something");
                //TODO : Be able to choose the text !
                newBox("Test");
            }
        });
    }

    private void init() {
        mRecyclerView = findViewById(R.id.check_list_recycler_view);
        mAdapter = new CheckListAdapter(this, mCheckList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void newBox(String s) {
        int wordListSize = mCheckList.size();
        mCheckList.add(s);
        mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
        mRecyclerView.smoothScrollToPosition(wordListSize);
    }


}
