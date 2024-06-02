package com.example.teeplan.event;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teeplan.R;
import com.example.teeplan.event.EventModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventFragment extends Fragment {

    private ArrayList<EventModel> eventModels = new ArrayList<>();
    private EventRecyclerViewAdapter adapter;

    public EventFragment() {
    }

    public static EventFragment newInstance() {
        return new EventFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.eventsRecylerView);
        setUpRecyclerView();

        adapter = new EventRecyclerViewAdapter(getActivity(), eventModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        retrieveEventData();

        return view;
    }

    private void setUpRecyclerView() {

    }

    private void retrieveEventData() {
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("events");

        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String description = snapshot.child("description").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);

                    eventModels.add(new EventModel(name, description, date));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("EventFragment", "Failed to retrieve data", databaseError.toException());
            }
        });
    }
}
