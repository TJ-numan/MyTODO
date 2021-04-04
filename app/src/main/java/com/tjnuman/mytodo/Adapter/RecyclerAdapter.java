package com.tjnuman.mytodo.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tjnuman.mytodo.Activites.MainActivity;
import com.tjnuman.mytodo.HelperClasses.AddNewTask;
import com.tjnuman.mytodo.Model.TodoModel;
import com.tjnuman.mytodo.R;
import com.tjnuman.mytodo.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<TodoModel> todoList;
    private MainActivity mainActivity;
    private DatabaseHandler db;

    public RecyclerAdapter(List<TodoModel> todoList, MainActivity mainActivity, DatabaseHandler db) {
        this.todoList = todoList;
        this.mainActivity = mainActivity;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        db.openDatabase();
        TodoModel item =  todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(),1);
                }
                else
                    db.updateStatus(item.getId(),0);
            }
        });
    }
    private boolean toBoolean (int n)
    {
        return n != 0;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void setTask(List<TodoModel> todoList){
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return mainActivity;
    }

 public void editItem(int position){
        TodoModel item = todoList.get(position);
     Bundle bundle  = new Bundle();
     bundle.putInt("id",item.getId());
     bundle.putString("task",item.getTask());
     AddNewTask fragment = new AddNewTask();
     fragment.setArguments(bundle);
     fragment.show(mainActivity.getSupportFragmentManager(),AddNewTask.TAG);
 }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.chekcbox);

        }
    }
}
