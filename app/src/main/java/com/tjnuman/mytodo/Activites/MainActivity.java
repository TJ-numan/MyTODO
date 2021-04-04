package com.tjnuman.mytodo.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tjnuman.mytodo.Adapter.RecyclerAdapter;
import com.tjnuman.mytodo.DialogCloseListner;
import com.tjnuman.mytodo.HelperClasses.AddNewTask;
import com.tjnuman.mytodo.Model.TodoModel;
import com.tjnuman.mytodo.R;
import com.tjnuman.mytodo.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListner {
    private RecyclerView tasksRecyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<TodoModel> tasklist;
    private ImageView imageView;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        imageView = findViewById(R.id.actionbtn);
        tasklist = new ArrayList<>();
        db = new DatabaseHandler(this);
        db.openDatabase();
        tasksRecyclerView = findViewById(R.id.itemsrecycler);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerAdapter = new RecyclerAdapter(tasklist,this,db);

        tasksRecyclerView.setAdapter(recyclerAdapter);

       tasklist = db.getAllTask();
        Collections.reverse(tasklist);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

    }

    @Override
    public void handleDialogClos(DialogInterface dialog) {
        tasklist = db.getAllTask();
       Collections.reverse(tasklist);


        TodoModel task = new TodoModel();
        task.setTask("this is the first task");
        task.setStatus(0);
        task.setId(1);
        tasklist.add(task);

        TodoModel task1 = new TodoModel();
        task1.setTask("this is the second task");
        task1.setStatus(1);
        task1.setId(2);
        tasklist.add(task1);

        TodoModel task2 = new TodoModel();
        task2.setTask("this is the third task");
        task2.setStatus(0);
        task2.setId(3);
        tasklist.add(task2);

        TodoModel task3 = new TodoModel();
        task3.setTask("this is the fourth task");
        task3.setStatus(1);
        task3.setId(4);
        tasklist.add(task3);

        recyclerAdapter.setTask(tasklist);





    }
}