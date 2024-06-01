package com.example.teeplan.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
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
        holder.bind(item);
        //Log.d("ToDoAdapter", "Binding item: " + item.getTask() + " with status: " + item.getStatus());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;
        CheckBox taskCheckbox;
        ImageButton deleteTask;

        public ViewHolder(View view) {
            super(view);
            taskText = view.findViewById(R.id.taskText);
            taskCheckbox = view.findViewById(R.id.todoCheckbox);
            deleteTask = view.findViewById(R.id.deleteTask);

            deleteTask.setOnClickListener(v -> {
                int adapterPosition = getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    todoList.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                    notifyItemRangeChanged(adapterPosition, todoList.size());
                    ((ToDoFragment) fragment).saveToDoListToFile();
                }
            });
        }

        public void bind(ToDoModel item) {
            taskText.setText(item.getTask());
            taskCheckbox.setOnCheckedChangeListener(null);
            taskCheckbox.setChecked(item.getStatus() == 1);
            //Log.d("ToDoAdapter", "Setting checkbox for task: " + item.getTask() + " to: " + (item.getStatus() == 1));

            taskCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                //Log.d("ToDoAdapter", "Checkbox for task: " + item.getTask() + " changed to: " + isChecked);
                item.setStatus(isChecked ? 1 : 0);
                ((ToDoFragment) fragment).saveToDoListToFile();
            });
        }
    }

    @Override
    public int getItemCount() {
        return todoList != null ? todoList.size() : 0;
    }

    public void setTask(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

}
