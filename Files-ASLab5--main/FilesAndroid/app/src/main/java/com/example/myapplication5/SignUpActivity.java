package com.example.myapplication5;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity
{
    EditText name;
    EditText surname;
    EditText email;
    EditText password;
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = findViewById(R.id.signUpName);
        surname = findViewById(R.id.signUpSurname);
        email = findViewById(R.id.signUpEmail);
        password = findViewById(R.id.signUpPassword);
        confirmPassword = findViewById(R.id.signUpConfirmPassword);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        confirmPassword.setOnEditorActionListener((v, actionId, event) ->
        {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE))
            {
                signUp();
            }
            return false;
        });
    }

    public void signUp(View view)
    {
        signUp();
    }

    private void signUp()
    {
        Database data = new Database(name.getText().toString().trim(),
                surname.getText().toString().trim(), email.getText().toString().trim(),
                password.getText().toString().trim());
        if (checkData(data, confirmPassword.getText().toString().trim()))
        {
            com.example.myapplication5.Controller.getInstance().addToList(data);
            Intent intent = new Intent(com.example.myapplication5.SignUpActivity.this, com.example.myapplication5.SignInActivity.class);
            intent.putExtra("email", data.getEmail());
            startActivity(intent);
            finish();
        }
    }

    boolean checkData(Database data, String repeatPass)
    {
        if (data.getName().trim().equals(""))
        {
            Toast.makeText(this, "Input name...", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (data.getSurname().trim().equals(""))
        {
            Toast.makeText(this, "Input surname...", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (com.example.myapplication5.Controller.getInstance().emailExist(data.getEmail()) != -1)
        {
            Toast.makeText(this, "E-Mail already registered!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!com.example.myapplication5.Validation.email(data.getEmail()))
        {
            Toast.makeText(this, "Wrong E-Mail format!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!data.getPassword().equals(repeatPass))
        {
            Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void goToSignIn(View view)
    {
        Intent goBackIntent = new Intent(com.example.myapplication5.SignUpActivity.this, com.example.myapplication5.SignInActivity.class);
        startActivity(goBackIntent);
        finish();
    }
}