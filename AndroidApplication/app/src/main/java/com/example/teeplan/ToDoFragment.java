package com.example.teeplan;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teeplan.Adapter.ToDoAdapter;
import com.example.teeplan.ToDoModel.ToDoModel;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ToDoFragment extends Fragment {
    private RecyclerView taskView;
    private ToDoAdapter tasksAdapter;
    private List<ToDoModel> taskList;
    private ImageButton addTask;
    private static final AtomicInteger idGenerator = new AtomicInteger(0);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do, container, false);
        addTask = view.findViewById(R.id.addTask);
        addTask.setOnClickListener(v -> showAddTaskDialog());

        taskList = new ArrayList<>();

        taskView = view.findViewById(R.id.tasksRecyclerView);
        taskView.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksAdapter = new ToDoAdapter(this);
        taskView.setAdapter(tasksAdapter);
        taskView.setItemAnimator(null);

        loadToDoListFromFile();

        SpeedDialView speedDialView = view.findViewById(R.id.speedDial);
        speedDialView.setMainFabClosedBackgroundColor(getResources().getColor(R.color.colorGreenLighter));
        speedDialView.setMainFabOpenedBackgroundColor(getResources().getColor(R.color.colorGreenLighter));

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_save, R.drawable.ic_save)
                        .setLabel("Save List")
                        .setFabBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreenLighter))
                        .setFabImageTintColor(ContextCompat.getColor(getContext(), R.color.colorBlackOlive))
                        .setLabelBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreenLighter))
                        .setLabelColor(ContextCompat.getColor(getContext(), R.color.colorBlackOlive))
                        .create()
        );

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_delete, R.drawable.ic_delete)
                        .setLabel("Delete List")
                        .setFabBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreenLighter))
                        .setFabImageTintColor(ContextCompat.getColor(getContext(), R.color.colorBlackOlive))
                        .setLabelBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreenLighter))
                        .setLabelColor(ContextCompat.getColor(getContext(), R.color.colorBlackOlive))
                        .create()
        );

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_load, R.drawable.ic_load)
                        .setLabel("Load List")
                        .setFabBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreenLighter))
                        .setFabImageTintColor(ContextCompat.getColor(getContext(), R.color.colorBlackOlive))
                        .setLabelBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreenLighter))
                        .setLabelColor(ContextCompat.getColor(getContext(), R.color.colorBlackOlive))
                        .create()
        );

        speedDialView.setOnActionSelectedListener(actionItem -> {
            int id = actionItem.getId();
            if (id == R.id.fab_save) {
                saveToDoListToFile();
                Toast.makeText(getContext(), "List saved", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.fab_delete) {
                taskList.clear();
                tasksAdapter.setTask(taskList);
                tasksAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "List deleted", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.fab_load) {
                loadToDoListFromFile();
                Toast.makeText(getContext(), "List loaded", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        return view;
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.new_task, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        EditText taskEditText = dialogView.findViewById(R.id.newTaskText);
        Button addButton = dialogView.findViewById(R.id.newTaskButton);

        addButton.setOnClickListener(v -> {
            String taskText = taskEditText.getText().toString().trim();
            if (!taskText.isEmpty()) {
                ToDoModel newTask = new ToDoModel(idGenerator.incrementAndGet(), 0, taskText);
                taskList.add(newTask);
                tasksAdapter.setTask(taskList);
                tasksAdapter.notifyDataSetChanged();
                dialog.dismiss();
                saveToDoListToFile();
            } else {
                Toast.makeText(getContext(), "Please enter a task", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public void saveToDoListToFile() {
        FileOutputStream fos = null;
        try {
            File file = new File(getContext().getFilesDir(), "todolist.txt");
            fos = new FileOutputStream(file);
            for (ToDoModel task : taskList) {
                String line = task.getId() + "," + task.getTask() + "," + task.getStatus() + "\n";
                fos.write(line.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error saving list", Toast.LENGTH_SHORT).show();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadToDoListFromFile() {
        FileInputStream fis = null;
        try {
            File file = new File(getContext().getFilesDir(), "todolist.txt");
            if (!file.exists()) {
                return;
            }
            fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            taskList.clear();
            int highestId = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String taskText = parts[1];
                    int status = Integer.parseInt(parts[2]);
                    ToDoModel task = new ToDoModel(id, status, taskText);
                    taskList.add(task);
                    highestId = Math.max(highestId, id);
                }
            }
            idGenerator.set(highestId);
            tasksAdapter.setTask(taskList);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error loading list", Toast.LENGTH_SHORT).show();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
