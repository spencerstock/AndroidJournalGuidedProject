package com.lambdaschool.journalguidedproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

// S02M02-3 Create List Adapter class, and add methods
public class JournalListAdapter extends RecyclerView.Adapter<JournalListAdapter.JournalEntryViewHolder> {

    ArrayList<JournalEntry> entryData;

    // S02M02-5 constructor accept a list of data for our view
    public JournalListAdapter(ArrayList<JournalEntry> entryData) {
        this.entryData = entryData;
    }

    @NonNull
    @Override
    // S02M02-7 create an instance of our viewholder which is our connection to the layout
    public JournalEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_entry_list_item, parent, false);
        return new JournalEntryViewHolder(itemView);
    }

    @Override
    // S02M02-8 bind an element from our list of data to the provided viewholder
    public void onBindViewHolder(@NonNull JournalEntryViewHolder journalEntryViewHolder, int i) {
        final JournalEntry data = entryData.get(i);

        journalEntryViewHolder.entryDateView.setText(data.getDate());
        journalEntryViewHolder.entryRatingView.setText(Integer.toString(data.getDayRating()));

        // S02M02-12 Change background color based on the day's rating
        switch (data.getDayRating()) {
            case 0:
                journalEntryViewHolder.entryRatingView.setBackgroundColor(
                        Color.parseColor("#FF0000"));
                break;
            case 1:
                journalEntryViewHolder.entryRatingView.setBackgroundColor(
                        Color.parseColor("#F66403"));
                break;
            case 2:
                journalEntryViewHolder.entryRatingView.setBackgroundColor(
                        Color.parseColor("#EDBF07"));
                break;
            case 3:
                journalEntryViewHolder.entryRatingView.setBackgroundColor(
                        Color.parseColor("#B9E50B"));
                break;
            case 4:
                journalEntryViewHolder.entryRatingView.setBackgroundColor(
                        Color.parseColor("#60DC0E"));
                break;
            case 5:
                journalEntryViewHolder.entryRatingView.setBackgroundColor(
                        Color.parseColor("#11D411"));
                break;
        }


        final String substring = data.getEntryText().substring(
                0,
                data.getEntryText().length() > 30 ? 30 : data.getEntryText().length() - 1);
        journalEntryViewHolder.entryTextView.setText(substring + "...");

        journalEntryViewHolder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(v.getContext(), DetailsActivity.class);
                editIntent.putExtra(JournalEntry.TAG, data);
                ((Activity) v.getContext()).startActivityForResult(
                        editIntent,
                        JournalListActivity.EDIT_ENTRY_REQUEST);
            }
        });
    }

    @Override
    // S02M02-6 used by the recyclerview to know when to stop building views
    public int getItemCount() {
        return this.entryData.size();
    }

    // S02M02-4 our connection to the views in the layout
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
