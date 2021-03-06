package com.forgetgot.selftie.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.forgetgot.selftie.Database.DatabaseHandler;
import com.forgetgot.selftie.R;
import com.forgetgot.selftie.Database.Task;
import com.forgetgot.selftie.Database.TaskAdapter;

import java.util.List;

public class FinishedTasks extends AppCompatActivity {
    ListView simpleList;
    List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("  SELFTIE");

        simpleList = (ListView)findViewById(R.id.simpleListView);

        DatabaseHandler db = new DatabaseHandler(this);

        taskList = db.getAllFinishedTasks();

        TaskAdapter adapter = new TaskAdapter(getApplicationContext(), taskList);
        simpleList.setAdapter(adapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = (Task) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(view.getContext(), FinishedTask.class);
                intent.putExtra(Task.TASK_EXTRA_ID, task.getID());

                startActivity(intent);
            }
        });
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        View empty = findViewById(R.id.empty);
        ListView list = (ListView) findViewById(R.id.simpleListView);
        list.setEmptyView(empty);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about_option) {
            Intent myIntent = new Intent(this, AboutActivity.class);
            startActivity(myIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
