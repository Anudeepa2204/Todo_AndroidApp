package com.application.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.application.todo.Helpers.DatabaseHelper;
import com.application.todo.Helpers.SaveSharedPreferences;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText emailID;
    DatabaseHelper db;
    EditText passWord;
    Button login_button;
    TextView signup_prompt;

    String email_id;
    String pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initObject();
        initListeners();

    }

    private void initObject() {
        db = new DatabaseHelper(LoginActivity.this);
        emailID = (EditText) findViewById(R.id.login_email);
        passWord = (EditText) findViewById(R.id.login_password);
        login_button = (Button) findViewById(R.id.login_button);
        signup_prompt = (TextView) findViewById(R.id.signup_prompt);
    }

    private void initListeners() {
        login_button.setOnClickListener(this);
        signup_prompt.setOnClickListener(this);
    }

    private void login_user() {
        email_id = emailID.getText().toString();
        pass = passWord.getText().toString();
        if (!validate_data()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Incorrect details! Please check the email and password!", Toast.LENGTH_SHORT);
            toast.show();
        }
        if (!user_exists()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Incorrect details! Please check the email and password!", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            SaveSharedPreferences.setEmail(getApplication(), email_id);
            clearInputFields();
            Intent intent = new Intent(this, ViewTodo.class);
            intent.putExtra("Email", email_id);
            startActivity(intent);
        }

    }

    private void clearInputFields() {
        emailID.setText("");
        passWord.setText("");
    }

    private boolean user_exists() {
        return db.validateUser(email_id, pass);
    }

    private boolean validate_data() {
        if (email_id.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email_id).matches()) {
            return false;
        }
        if (pass.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                login_user();
                break;
            case R.id.signup_prompt:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intentRegister);
                break;
        }
    }
}
