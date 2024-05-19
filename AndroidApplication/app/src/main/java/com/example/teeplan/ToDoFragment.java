package com.example.teeplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teeplan.Adapter.ToDoAdapter;
import com.example.teeplan.ToDoModel.ToDoModel;

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

        tasksAdapter.setTask(taskList);

        return view;
    }
}
