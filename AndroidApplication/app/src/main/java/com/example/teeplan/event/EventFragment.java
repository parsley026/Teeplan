package com.example.teeplan.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teeplan.R;

import java.util.ArrayList;

public class EventFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventFragment() {
        // Required empty public constructor
    }

    public static EventFragment newInstance(String param1, String param2) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    ArrayList<EventModel> eventModels = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.eventsRecylerView);
        setUpEventModels();

        EventRecyclerViewAdapter adapter = new EventRecyclerViewAdapter(getActivity(), eventModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void setUpEventModels() {
        eventModels.add(new EventModel("Event", "coś opis jakiś", "29-05-2024"));
        eventModels.add(new EventModel("Koncert", "coś opis jakiś", "22-05-2024"));
        eventModels.add(new EventModel("Juwenalia", "coś opis jakiś", "26-05-2024"));

        eventModels.add(new EventModel("Event", "coś opis jakiś", "29-05-2024"));
        eventModels.add(new EventModel("Koncert", "coś opis jakiś", "22-05-2024"));
        eventModels.add(new EventModel("Juwenalia", "coś opis jakiś", "26-05-2024"));

        eventModels.add(new EventModel("Event", "coś opis jakiś", "29-05-2024"));
        eventModels.add(new EventModel("Koncert", "coś opis jakiś", "22-05-2024"));
        eventModels.add(new EventModel("Juwenalia", "coś opis jakiś", "26-05-2024"));

        eventModels.add(new EventModel("Event", "coś opis jakiś", "29-05-2024"));
        eventModels.add(new EventModel("Koncert", "coś opis jakiś", "22-05-2024"));
        eventModels.add(new EventModel("Juwenalia", "coś opis jakiś", "26-05-2024"));

    }
}