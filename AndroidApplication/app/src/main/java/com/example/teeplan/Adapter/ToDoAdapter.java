package com.example.teeplan.Adapter;

import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
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
            if (item.getStatus() == 1) {
                SpannableString spannableString = new SpannableString(item.getTask());
                spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                taskText.setText(spannableString);
                taskText.setAlpha(0.4f);
            } else {
                taskText.setPaintFlags(taskText.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                taskText.setAlpha(1.0f);
            }
            taskCheckbox.setOnCheckedChangeListener(null);
            taskCheckbox.setChecked(item.getStatus() == 1);

            taskCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.setStatus(isChecked ? 1 : 0);
                ((ToDoFragment) fragment).saveToDoListToFile();
                if (isChecked) {
                    SpannableString spannableStringChecked = new SpannableString(item.getTask());
                    spannableStringChecked.setSpan(new StrikethroughSpan(), 0, spannableStringChecked.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    taskText.setText(spannableStringChecked);
                    taskText.setAlpha(0.4f);
                } else {
                    taskText.setText(item.getTask());
                    taskText.setAlpha(1.0f);
                }
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
