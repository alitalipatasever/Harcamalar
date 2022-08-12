package com.alitalipatasever.harcamalar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Add extends AppCompatActivity {

    EditText ETtutar, ETaciklama;
    Button btnEkle, btnSil;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String aciklama,tutar, tarih, email;

    ArrayList harcamaListesi = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ETaciklama = (EditText) findViewById(R.id.aciklama);
        ETtutar = (EditText) findViewById(R.id.tutar);
        btnEkle = (Button) findViewById(R.id.add);
        btnSil = (Button) findViewById(R.id.delete);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseAuth.getCurrentUser().getEmail();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        tarih = simpleDateFormat.format(new Date());

        //Toast.makeText(getApplicationContext(),tarih,Toast.LENGTH_SHORT).show();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Harcamalar");

        String key = database.getReference("Harcamalar").push().getKey();


        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aciklama = ETaciklama.getText().toString();
                tutar = ETtutar.getText().toString();

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        myRef.child(key).child("aciklama").setValue(aciklama);
                        myRef.child(key).child("tutar").setValue(tutar);
                        myRef.child(key).child("email").setValue(email);
                        myRef.child(key).child("tarih").setValue(tarih);
                        myRef.child(key).child("id").setValue(key);

                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            String readChildKey = ds.getKey();
                            HashMap<String, String> readChild = (HashMap<String, String>) ds.getValue();
                            //Toast.makeText(getApplicationContext(),readChildKey,Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                finish();
            }
        });



        //myRef.setValue("Hello, World!");


    }
}