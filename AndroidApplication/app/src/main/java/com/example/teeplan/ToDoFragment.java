package com.example.teeplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    private ImageButton addTask;
    private RelativeLayout newTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);
        addTask = view.findViewById(R.id.addTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTaskDialog();

            }
        });
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
                    Toast.makeText(getContext(), "List saved", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.fab_delete) {
                    // Implement delete list logic here
                    Toast.makeText(getContext(), "List deleted", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.fab_load) {
                    // Implement load list logic here
                    Toast.makeText(getContext(), "List loaded", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        return view;
    }
    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.new_task, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        EditText taskEditText = dialogView.findViewById(R.id.newTaskText);
        Button addButton = dialogView.findViewById(R.id.newTaskButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = taskEditText.getText().toString().trim();
                if (!taskText.isEmpty()) {
                    ToDoModel newTask = new ToDoModel();
                    newTask.setTask(taskText);
                    newTask.setStatus(0);
                    taskList.add(newTask);
                    tasksAdapter.setTask(taskList);
                    tasksAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Please enter a task", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
}
