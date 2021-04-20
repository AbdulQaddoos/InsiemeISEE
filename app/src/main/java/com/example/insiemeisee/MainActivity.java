package com.example.insiemeisee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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

    public void upload(View v){
        final String codice = codeCase.getText().toString();
        if (codice.isEmpty()){
            codeCase.setError("code required");
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
                            textView.setText("CODE: " + codice + " already exist");
                        }
                        else{
                            ref.setValue(newList).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        textView.setText("CODE: '" + codice + "' added successfully");
                                    }
                                    else{
                                        textView.setText("CODE: '" + codice + "' not added");
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
        final String codice = codeCase.getText().toString();
        if (codice.isEmpty()){
            codeCase.setError("code required");
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
                                    Toast.makeText(getApplicationContext(), "List edited", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "List not edited", Toast.LENGTH_SHORT).show();
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
        final String codice = codeCase.getText().toString();
        if (codice.isEmpty()){
            codeCase.setError("code required");
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
                                    Toast.makeText(getApplicationContext(), "List edited", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "List not edited", Toast.LENGTH_SHORT).show();
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
    public void
    getStatus(View view) {
        final String codice = codeCase.getText().toString();
        if (codice.isEmpty()){
            codeCase.setError("code required");
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
                            textView.setText("CODE: '" + codice + "' doesn't exist");
                        }
                        else{
                            textView.setText("CODE: '" + codice + "' - ISEE: "+ status.name());
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