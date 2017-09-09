package com.forgetgot.selftie.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.forgetgot.selftie.Database.DatabaseHandler;
import com.forgetgot.selftie.Database.SubTask;
import com.forgetgot.selftie.R;
import com.forgetgot.selftie.Database.Task;

import java.util.List;

public class FinishedTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_task);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("  SELFTIE");

        Bundle extras = getIntent().getExtras();
        int id;

        if (extras != null && (id  = extras.getInt(Task.TASK_EXTRA_ID, -1)) != -1) {
            DatabaseHandler db = new DatabaseHandler(this);

            Task task = db.getTask(id);

            TextView t=(TextView)findViewById(R.id.task_name);
            t.setText(task.getName());

            t=(TextView)findViewById(R.id.task_category);
            t.setText(task.getCategory());

            t=(TextView)findViewById(R.id.task_prediction);
            t.setText(getString(R.string.hour_format, task.getPrediction()));

            t=(TextView)findViewById(R.id.task_realtime);
            t.setText(getString(R.string.hour_format, calculateRealTime(db, id)));

            t=(TextView)findViewById(R.id.task_error);
            t.setText(getString(R.string.percentage_format, calculateError(db,task)*100));
        }
    }

    public static double calculateError(DatabaseHandler db, Task task){
        return Math.abs(calculateRealTime(db,task.getID()) - task.getPrediction()) / task.getPrediction();
    }

    public static double calculateRealTime(DatabaseHandler db, int id){
        double realTime = 0;
        List<SubTask> subTasks = db.getAllSubTasks(id);
        for (SubTask subTask: subTasks) realTime += subTask.getTime();
        return realTime;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
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
