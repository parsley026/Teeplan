package com.example.teeplan.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teeplan.R;

import java.util.ArrayList;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<EventModel> eventModels;

    public EventRecyclerViewAdapter(Context context, ArrayList<EventModel> eventModels) {
        this.context = context;
        this.eventModels = eventModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_event, parent, false);

        return new EventRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(eventModels.get(position).getName());
        holder.tvDescription.setText(eventModels.get(position).getDescription());
        holder.tvDate.setText(eventModels.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return eventModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDescription, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.EventName);
            tvDescription = itemView.findViewById(R.id.EventDescription);
            tvDate = itemView.findViewById(R.id.EventDate);
        }
    }
}
