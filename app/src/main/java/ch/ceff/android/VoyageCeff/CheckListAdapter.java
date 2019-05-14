package ch.ceff.android.VoyageCeff;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.WordViewHolder>{

    private final ArrayList<String> mWordList;
    private LayoutInflater mInflater;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final CheckBox wordItemView; //représente le composant qui affichera le mot dans le layout
        final CheckListAdapter mAdapter; //représente l'adapter

        public WordViewHolder(View itemView, CheckListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            String element = mWordList.get(mPosition);
            mWordList.set(mPosition, "Cliqué! " + element);
            mAdapter.notifyDataSetChanged();
        }
    }

    public CheckListAdapter(Context context, ArrayList<String> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }

    @NonNull
    @Override
    public CheckListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.checklist_item, viewGroup, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListAdapter.WordViewHolder wordViewHolder, int i) {
        String mCurrent = mWordList.get(i);
        wordViewHolder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}

