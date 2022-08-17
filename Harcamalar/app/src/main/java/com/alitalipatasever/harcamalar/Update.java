package com.alitalipatasever.harcamalar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Update extends AppCompatActivity {

    EditText ETaciklama, ETtutar;
    Button btnDelete, btnUpdate;
    FirebaseDatabase db;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String email, tarih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ETaciklama = (EditText) findViewById(R.id.aciklama);
        ETtutar = (EditText) findViewById(R.id.tutar);
        btnDelete = (Button) findViewById(R.id.delete);
        btnUpdate = (Button) findViewById(R.id.update);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseAuth.getCurrentUser().getEmail();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        tarih = simpleDateFormat.format(new Date());
        tarih = tarih + " güncellendi.";

        db = FirebaseDatabase.getInstance();
        //String readChildKey = db.getReference("Harcamalar").child("").getKey();

        Intent intent = getIntent();

        String gelenAciklama = intent.getStringExtra("aciklama");
        String gelenTutar = intent.getStringExtra("tutar");
        String gelenId = intent.getStringExtra("id");



        ETaciklama.setText(gelenAciklama);
        ETtutar.setText(gelenTutar);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String aciklama = ETaciklama.getText().toString();
                String tutar = ETtutar.getText().toString();

                myRef = FirebaseDatabase.getInstance().getReference("Harcamalar").child(gelenId);

                Harcamalar harcama = new Harcamalar(email,tarih,aciklama,tutar,gelenId);
                myRef.setValue(harcama).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = FirebaseDatabase.getInstance().getReference("Harcamalar").child(gelenId);
                myRef.removeValue();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}