package ch.ceff.android.VoyageCeff;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ReglementListAdapter extends RecyclerView.Adapter<ReglementListAdapter.WordViewHolder>{

    private final ArrayList<String> mWordList;
    private LayoutInflater mInflater;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView titleItemView; //représente le composant qui affichera le mot dans le layout
        final TextView containItemView;
        final ReglementListAdapter mAdapter; //représente l'adapter hfhdtf

        public WordViewHolder(View itemView, ReglementListAdapter adapter) {
            super(itemView);
            titleItemView = itemView.findViewById(R.id.title);
            containItemView = itemView.findViewById(R.id.contain);
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

    public ReglementListAdapter(Context context, ArrayList<String> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }

    @NonNull
    @Override
    public ReglementListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Layout custom pour la pop-up
        View mItemView = mInflater.inflate(R.layout.reglement_list_item, viewGroup, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ReglementListAdapter.WordViewHolder wordViewHolder, int i) {
        String mCurrent = mWordList.get(i);
        //TODO : Regarder comment ça marche ?
        wordViewHolder.titleItemView.setText(mCurrent);
        wordViewHolder.containItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}
