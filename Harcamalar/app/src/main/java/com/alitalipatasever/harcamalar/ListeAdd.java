package com.alitalipatasever.harcamalar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListeAdd extends AppCompatActivity {

    EditText etListeAdi;
    Button btnListeEkle, btnVazgec;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String listeAdi, tarih, email;

    ArrayList listeListesi = new  ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_add);

        etListeAdi = (EditText) findViewById(R.id.listeAdi);
        btnListeEkle = (Button) findViewById(R.id.add);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseAuth.getCurrentUser().getEmail();

        String replaceEmail1 = email.replace("@","_");
        String replaceEmail = replaceEmail1.replace(".","_");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        tarih = simpleDateFormat.format(new Date());

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Harcamalar");

        String key = database.getReference("Harcamalar").push().getKey();

        btnListeEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listeAdi = etListeAdi.getText().toString().trim();

                Listeler liste = new Listeler();
                liste.setEmail(email);
                liste.setId(key);
                liste.setlisteAdi(listeAdi);
                liste.setTarih(tarih);

                //myRef.child(replaceEmail).setValue(liste);
                myRef.child(replaceEmail).push().setValue(liste);

                Intent intent = new Intent(ListeAdd.this,ListelerActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}