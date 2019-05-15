package ch.ceff.android.VoyageCeff;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ReglementsActivity extends AppCompatActivity {

    private static final String TAG = ReglementsActivity.class.getSimpleName();
    private ArrayList<String> mWordList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    /*
    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
            .setTitle("Nouvelle règle")
            .setMessage("Veuillez insérer votre nouvelle règle.")
            .setView(taskEditText)
            .setPositiveButton("Entrer", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String task = String.valueOf(taskEditText.getText());
                    int wordListSize = mWordList.size();
                    mWordList.add(task);
                    mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
                    mRecyclerView.smoothScrollToPosition(wordListSize);
                }
            })
            .setNegativeButton("Annuler", null)
            .create();
        dialog.show();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglements);

        FloatingActionButton fab = findViewById(R.id.id_add_reglements);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
                //showAddItemDialog(ReglementsActivity.this);
            }
        });

        mRecyclerView = findViewById(R.id.id_recyclerview_reglements);
        mAdapter = new WordListAdapter(this, mWordList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void init(String text){
        int wordListSize = mWordList.size();
        mWordList.add(text);
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
                init(value);
                init(value1);
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
