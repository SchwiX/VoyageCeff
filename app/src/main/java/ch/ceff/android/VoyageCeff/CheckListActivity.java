package ch.ceff.android.VoyageCeff;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class CheckListActivity extends AppCompatActivity {

    private static final String TAG = CheckListActivity.class.getSimpleName();
    private ArrayList<String> mCheckList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CheckListAdapter mAdapter;
    private String m_Text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        init();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "I'm trying to add something");
                //TODO : Can delete after insert ! (Maybe add X button)
                showAddItemDialog(CheckListActivity.this);
            }
        });
    }

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
                //TODO: Hide checked checkboxes
                Log.d(TAG, "Hide checked");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAddItemDialog(Context c) {
        //TODO : Cleaner dialog box ! (Try use checklist_dialog_box.xml)
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Add an item");
        builder.setMessage("Name :");

        // Set up the input
        final EditText input = new EditText(c);
        final EditText input2 = new EditText(c);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        /*
        LinearLayout lila1= new LinearLayout(this);
        lila1.setOrientation(LinearLayout.VERTICAL);

        lila1.addView(input);
        lila1.addView(input2);
        builder.setView(lila1);
        */

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
