package com.example.teeplan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teeplan.R;
import com.example.teeplan.ToDoFragment;
import com.example.teeplan.ToDoModel.ToDoModel;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoModel> todoList;
    private Fragment fragment;

    public ToDoAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDoModel item = todoList.get(position);
        holder.taskText.setText(item.getTask());
        holder.taskCheckbox.setChecked(toBoolean(item.getStatus()));

        holder.taskCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setStatus(isChecked ? 1 : 0);
            ((ToDoFragment) fragment).saveToDoListToFile(); // Save the list whenever a task status changes
        });
    }

    @Override
    public int getItemCount() {
        return todoList != null ? todoList.size() : 0;
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    public void setTask(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        CheckBox taskCheckbox;

        ViewHolder(View view) {
            super(view);
            taskText = view.findViewById(R.id.taskText);
            taskCheckbox = view.findViewById(R.id.todoCheckbox);
        }
    }
}
