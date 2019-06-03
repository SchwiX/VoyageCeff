package ch.ceff.android.VoyageCeff;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.ArrayList;

class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.WordViewHolder>{

    private static final String TAG = CheckListAdapter.class.getSimpleName();
    //private ArrayList<CheckListClass> myItems;
    private final ArrayList<String> mWordList;
    private LayoutInflater mInflater;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final CheckBox wordItemView; //représente le composant qui affichera le mot dans le layout
        final CheckListAdapter mAdapter; //représente l'adapter
        ImageView mImageView;

        public WordViewHolder(View itemView, CheckListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            mImageView = itemView.findViewById(R.id.button);

            wordItemView.setOnClickListener(this);
            mImageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.equals(mImageView)){
                removeAt(getAdapterPosition());
            }else if(v.equals(wordItemView)){
                if(wordItemView.isChecked()){
                    wordItemView.setPaintFlags(wordItemView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else if (!wordItemView.isChecked()){
                    wordItemView.setPaintFlags(wordItemView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }else if(v.equals(itemView.findViewById(R.id.check_list_menu))){
                if(itemView.findViewById(R.id.check_list_menu).getVisibility() == View.INVISIBLE){
                    mImageView.setVisibility(View.VISIBLE);
                }else if(itemView.findViewById(R.id.check_list_menu).getVisibility() == View.VISIBLE){
                    mImageView.setVisibility(View.INVISIBLE);
                }
            }
        }

    }

    private void removeAt(int adapterPosition) {
        mWordList.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition, mWordList.size());
    }

    public CheckListAdapter(Context context, ArrayList<String> wordList) {
        mInflater = LayoutInflater.from(context);

        /*
        ArrayList<CheckListClass> tempItemMake = new ArrayList<>();

        for ( String str : wordList){
            tempItemMake.add(new CheckListClass(str, false));
        }

        this.myItems = tempItemMake;
        */
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


        /*
        final CheckListClass mCurrent = myItems.get(i);

        wordViewHolder.wordItemView.setText(mCurrent.getTitre());

        //in some cases, it will prevent unwanted situations
        wordViewHolder.wordItemView.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        wordViewHolder.wordItemView.setChecked(mCurrent.isChecked());

        wordViewHolder.wordItemView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                mCurrent.setChecked(isChecked);
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}

