package com.example.insiemeisee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import db.manager.DB;
import db.manager.Person;
import db.manager.Status;

public class MainActivity extends AppCompatActivity {

    List<Person> people;
    DatabaseReference ref;
    EditText codeCase;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ref = FirebaseDatabase.getInstance().getReference().child("People");
        people = new ArrayList<>();
        codeCase = (EditText)findViewById(R.id.codeCase);
        textView = (TextView)findViewById(R.id.textView);
    }


    public void getStatus(View view) {
        final String codice = codeCase.getText().toString().toLowerCase();
        if (codice.isEmpty()){
            codeCase.setError("Codice Richiesto");
        }
        else {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        people.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Person person = dataSnapshot.getValue(Person.class);
                            people.add(person);
                        }
                        Status status = DB.getStatus(codice, people);
                        if(status == null){
                            textView.setText("Lei non risulta nei nostri archivi. Si prega di contattare l'amministratore: 3885843856");
                        }
                        else{
                            if(status.equals(Status.READY)){
                                textView.setText("LA RICHIESTA E STATA FATTA");
                            }
                            else{
                                textView.setText("LA RICHIESTA NON E STATA ANCORA FATTA");
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void go_admin(View view) {
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
}