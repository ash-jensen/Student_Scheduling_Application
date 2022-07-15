package com.ajensen.studentscheduleapp.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ajensen.studentscheduleapp.Entity.Term;
import com.ajensen.studentscheduleapp.R;

import java.util.List;

// This fills TermList.java with a recycleView of terms, XML is list_item_term
public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    private List<Term> termsList;
    private final Context context;
    private final LayoutInflater inflater;

    // TermView class and constructor
    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termItemView;
        // Constructor for the viewHolder
        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.textView);
            // Click takes you to term details activity, holds all clicked term details
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = termsList.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("id", current.getTermId());
                    intent.putExtra("name", current.getTermName());
                    // change to use date picker, here and in term details
                    intent.putExtra("startDate", (current.getStartDate()).toString());
                    intent.putExtra("endDate", (current.getEndDate()).toString());
                    context.startActivity(intent);
                }
            });
        }
    }

    // Constructor for adapter
    public TermAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    // Inflate the list item that you want to show
    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_item_term, parent, false);
        return new TermViewHolder(itemView);
    }

    // Binds term to the textView
    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(termsList != null) {
            Term current = termsList.get(position);
            String name = current.getTermName();
            holder.termItemView.setText(name);
        }
        else {
            holder.termItemView.setText("No term name");
        }
    }

    // Fill termsList with terms
    public void setTermsList(List<Term> terms) {
        termsList = terms;
        notifyDataSetChanged();
    }

    // If termsList is empty, return zero, else return size
    @Override
    public int getItemCount() {
        if(termsList != null) {
            return termsList.size();
        }
        else {
            return 0;
        }
    }
}
