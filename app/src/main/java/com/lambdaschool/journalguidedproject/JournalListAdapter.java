package com.lambdaschool.journalguidedproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class JournalListAdapter extends RecyclerView.Adapter<JournalListAdapter.JournalEntryViewHolder> {

    ArrayList<JournalEntry> entryData;

    // constructor accept a list of data for our view
    public JournalListAdapter(ArrayList<JournalEntry> entryData) {
        this.entryData = entryData;
    }

    @NonNull
    @Override
    // create an instance of our viewholder which is our connection to the layout
    public JournalEntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    // bind an element from our list of data to the provided viewholder
    public void onBindViewHolder(@NonNull JournalEntryViewHolder journalEntryViewHolder, int i) {

    }

    @Override
    // used by the recyclerview to know when to stop building views
    public int getItemCount() {
        return 0;
    }

    // our connection to the views in the layout
    class JournalEntryViewHolder extends RecyclerView.ViewHolder {

        TextView entryTextView, entryDateView, entryRatingView;
        View parentView;

        // bind the data members of our viewholder to the items in the layout
        public JournalEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            entryTextView = itemView.findViewById(R.id.item_entry_text);
            entryDateView = itemView.findViewById(R.id.item_date_view);
            entryRatingView = itemView.findViewById(R.id.item_entry_rating);

            parentView = itemView.findViewById(R.id.list_parent);
        }
    }
}
