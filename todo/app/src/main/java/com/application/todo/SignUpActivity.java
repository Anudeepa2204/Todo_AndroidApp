package com.application.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.application.todo.Helpers.DatabaseHelper;
import com.application.todo.Model.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    //All the components that are used in the view
    private TextView login;
    private EditText email;
    private EditText password;
    private EditText re_password;
    private EditText name;
    private Button signup_button;
    private DatabaseHelper database;

    //Strings in which all the input data from the users are saved
    private String email_id;
    private String pass;
    private String re_pass;
    private String user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initObjects();
        initListeners();
    }

    private void initObjects() {
        login = (TextView) findViewById(R.id.login_prompt);
        database = new DatabaseHelper(SignUpActivity.this);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        re_password = (EditText) findViewById(R.id.re_password);
        email = (EditText) findViewById(R.id.email);

        signup_button = (Button) findViewById(R.id.signup_button);
    }

    private void initListeners() {
        signup_button.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    private boolean valid_input() {
        email_id = email.getText().toString();
        pass = password.getText().toString();
        re_pass = re_password.getText().toString();
        user_name = name.getText().toString();
        if (email_id.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email_id).matches()) {
            email.setError("Invalid email");
            return false;
        } else if (pass.isEmpty() || re_pass.isEmpty() || !pass.equals(re_pass)) {
            re_password.setError("Password do not match");
            return false;
        } else if (user_name.isEmpty())
            return false;
        else return true;
    }

    private boolean check_user() {
        database.emailAlreadyExists(email_id);
        return false;
    }

    private void signup_user() {
        if (!valid_input()) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Registration Error",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        if (!check_user()) {
            User newUser = new User();
            newUser.setPassword(pass);
            newUser.setName(user_name);
            newUser.setEmail(email_id);
            database.addUser(newUser);
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Registration Successful",
                    Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "User already exists",
                    Toast.LENGTH_SHORT);
            toast.show();

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_button:
                signup_user();
                break;
            case R.id.login_prompt:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentRegister);
                break;
        }
    }
}
