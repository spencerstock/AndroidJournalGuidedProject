package com.lambdaschool.journalguidedproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class JournalListAdapter extends RecyclerView.Adapter<JournalListAdapter.JournalEntryViewHolder> {
    @NonNull
    @Override
    public JournalEntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull JournalEntryViewHolder journalEntryViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class JournalEntryViewHolder extends RecyclerView.ViewHolder {

        public JournalEntryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
