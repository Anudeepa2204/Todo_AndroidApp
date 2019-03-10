package com.application.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.application.todo.Helpers.SaveSharedPreferences;

public class ViewTodo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SaveSharedPreferences.getEmail(getApplicationContext()).length() == 0) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_view);
            initObjects();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu_settings, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                load_add_todo_activity();
                break;
            case R.id.logout:
                logout();
                break;
            case R.id.profile:
                break;
        }
        return true;
    }

    private void load_add_todo_activity() {
        Intent intent = new Intent(getApplicationContext(), AddTodoActivity.class);
//        intent.putExtra("EMAIL", user_email);
        startActivity(intent);
    }

    public void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                ViewTodo.this
        );
        builder.setTitle("Logout?");
        builder.setMessage("Do you want to logout from the app?");
        builder.setIcon(R.drawable.dialog_icon);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SaveSharedPreferences.clear(getApplication());
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void initObjects() {
    }
}
