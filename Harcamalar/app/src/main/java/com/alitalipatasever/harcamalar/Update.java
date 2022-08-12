package com.alitalipatasever.harcamalar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Update extends AppCompatActivity {

    EditText ETaciklama, ETtutar;
    Button btnDelete, btnUpdate;
    FirebaseDatabase db;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        ETaciklama = (EditText) findViewById(R.id.aciklama);
        ETtutar = (EditText) findViewById(R.id.tutar);
        btnDelete = (Button) findViewById(R.id.delete);
        btnUpdate = (Button) findViewById(R.id.update);

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
                myRef = FirebaseDatabase.getInstance().getReference();


            }
        });



    }

}