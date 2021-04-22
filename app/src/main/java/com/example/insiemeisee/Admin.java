package com.example.insiemeisee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class Admin extends AppCompatActivity {

    List<Person> people;
    DatabaseReference ref;
    EditText codeCase;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ref = FirebaseDatabase.getInstance().getReference().child("People");
        people = new ArrayList<>();
        codeCase = (EditText)findViewById(R.id.code_case);
        textView = (TextView)findViewById(R.id.text);
    }

    public void home(View view) {
        Intent myIntent = new Intent(Admin.this, MainActivity.class);
        Admin.this.startActivity(myIntent);
    }

    public void upload(View v){
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
                        List<Person> newList = DB.addPerson(codice, people);
                        if(newList == null){
                            textView.setText("Il codice: '" + codice + "' esiste già.");
                        }
                        else{
                            ref.setValue(newList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        textView.setText("Il codice: '" + codice + "' aggiunto con successo");
                                    }
                                    else{
                                        textView.setText("Il codice: '" + codice + "' non aggiunto");
                                    }
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void setReady(View view) {
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
                        List<Person> newList = DB.setReady(codice, people);
                        ref.setValue(newList).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Elenco modificato", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Elenco non modificato", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void setNotReady(View view) {
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
                        List<Person> newList = DB.setNotReady(codice, people);
                        ref.setValue(newList).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Elenco modificato", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Elenco non modificato", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
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
                            textView.setText("Il codice: '" + codice + "' non esiste.");
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
}