package com.example.teeplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teeplan.Adapter.ToDoAdapter;
import com.example.teeplan.ToDoModel.ToDoModel;

import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialOverlayLayout;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends Fragment {
    private RecyclerView taskView;
    private ToDoAdapter tasksAdapter;
    private List<ToDoModel> taskList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);
        taskList = new ArrayList<>();

        taskView = view.findViewById(R.id.tasksRecyclerView);
        taskView.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksAdapter = new ToDoAdapter(this);
        taskView.setAdapter(tasksAdapter);

        ToDoModel task = new ToDoModel();
        task.setTask("test task");
        task.setStatus(0);
        task.setId(1);

        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);taskList.add(task);taskList.add(task);taskList.add(task);taskList.add(task);taskList.add(task);

        tasksAdapter.setTask(taskList);

        SpeedDialView speedDialView = view.findViewById(R.id.speedDial);
        speedDialView.setMainFabClosedBackgroundColor(getResources().getColor(R.color.colorGreenLighter));
        speedDialView.setMainFabOpenedBackgroundColor(getResources().getColor(R.color.colorGreenLighter));
        speedDialView.setDrawingCacheBackgroundColor(getResources().getColor(R.color.colorTransparent));
        speedDialView.setBackgroundColor(getResources().getColor(R.color.colorTransparent));

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_save, R.drawable.ic_save)
                        .setLabel("Save List")
                        .setFabBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreenLighter)) // Set FAB background color to transparent
                        .setFabImageTintColor(ContextCompat.getColor(getContext(), R.color.colorBlackOlive))
                        .setLabelBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorGreenLighter)) // Set label background color to transparent
                        .setLabelColor(ContextCompat.getColor(getContext(),R.color.colorBlackOlive))
                        .create()
        );

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_delete, R.drawable.ic_delete)
                        .setLabel("Delete List")
                        .setFabBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreenLighter))
                        .setFabImageTintColor(ContextCompat.getColor(getContext(),  R.color.colorBlackOlive))
                        .setLabelBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorGreenLighter)) // Set label background color to transparent
                        .setLabelColor(ContextCompat.getColor(getContext(),R.color.colorBlackOlive))
                        .create()
        );

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_load, R.drawable.ic_load)
                        .setLabel("Load List")
                        .setFabBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreenLighter))
                        .setFabImageTintColor(ContextCompat.getColor(getContext(), R.color.colorBlackOlive))
                        .setLabelBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorGreenLighter)) // Set label background color to transparent
                        .setLabelColor(ContextCompat.getColor(getContext(),R.color.colorBlackOlive))
                        .create()
        );

        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                int id = actionItem.getId();
                if (id == R.id.fab_save) {
                    // Implement save list logic here
                    Toast.makeText(getContext(), "Save List Clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.fab_delete) {
                    // Implement delete list logic here
                    Toast.makeText(getContext(), "Delete List Clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.fab_load) {
                    // Implement load list logic here
                    Toast.makeText(getContext(), "Load List Clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        return view;
    }
}
