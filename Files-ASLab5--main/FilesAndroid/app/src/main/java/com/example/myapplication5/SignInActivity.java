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

public class SignInActivity extends AppCompatActivity
{
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.signInEmail);
        password = findViewById(R.id.signInPassword);
        password.setOnEditorActionListener((v, actionId, event) ->
        {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE))
                signIn();
            return false;
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        if (intent.hasExtra("email"))
        {
            this.email.setText(intent.getStringExtra("email"));
            password.requestFocus();
            Toast.makeText(this, "Successfully registered!", Toast.LENGTH_SHORT).show();
        } else this.email.setText("");
        if (!password.getText().toString().trim().equals("")) password.setText("");
    }

    public void signIn(View view)
    {
        signIn();
    }

    private void signIn()
    {
        if (!password.getText().toString().trim().equals("") && com.example.myapplication5.Controller.getInstance().signIn(email.getText().toString(), password.getText().toString()) != -1)
        {
            startCalculator();
        } else Toast.makeText(this, getString(R.string.signInError), Toast.LENGTH_SHORT).show();
    }

    private void startCalculator()
    {
        Intent logInIntent = new Intent(com.example.myapplication5.SignInActivity.this, MainActivity.class);
        startActivity(logInIntent);
        finish();
    }

    public void signUp(View view)
    {
        Intent signUpIntent = new Intent(com.example.myapplication5.SignInActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
    }
}