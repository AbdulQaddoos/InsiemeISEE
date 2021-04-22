package com.example.insiemeisee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {


    EditText passwordButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        passwordButton = findViewById(R.id.password);

    }

    public void logIn(View view) {
        final String password = passwordButton.getText().toString();
        if (password.isEmpty()){
            passwordButton.setError("Obbligatorio");
        }
        else {
            if(password.equals("abbccc123")){
                Intent myIntent = new Intent(LoginActivity.this, Admin.class);
                LoginActivity.this.startActivity(myIntent);
            }
            else{
                Toast.makeText(getApplicationContext(), "Errato", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void home(View view) {
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(myIntent);
    }
}