package com.application.todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.application.todo.Helpers.DatabaseHelper;
import com.application.todo.Helpers.SaveSharedPreferences;
import com.application.todo.Model.Todo;

public class AddTodoActivity extends AppCompatActivity {

    private EditText titleText;
    private EditText contentText;
    private String titleData;
    private String contentData;
    private String user_email;
    private DatabaseHelper database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        this.user_email = SaveSharedPreferences.getEmail(getApplication());
        initObjects();
    }

    private void initObjects() {
        titleText = findViewById(R.id.title);
        contentText = findViewById(R.id.content);
        titleData = titleText.getText().toString();
        contentData = contentText.getText().toString();
        database = new DatabaseHelper(AddTodoActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addtodoview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.confirm:
                add_todo_to_db();
                break;
            case R.id.cancel:
                confirm_cancel();

                break;
        }
        return true;
    }

    private void confirm_cancel() {
    }

    private void add_todo_to_db() {
        Todo todo = new Todo();
        Log.d("add to the db", contentData);
        Log.d("add to the db", titleData);
        todo.setContent(contentData);
        todo.setTitle(titleData);
        todo.setUser_id(user_email);
        database.addTodo(todo);
    }


    @Override
    public void onBackPressed() {
    }

}
