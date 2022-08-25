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

public class RegisterList extends AppCompatActivity {
    EditText etListeId;
    Button btnKaydet;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    String email,listeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_list);

        etListeId = (EditText) findViewById(R.id.listeId);
        btnKaydet = (Button) findViewById(R.id.add);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseAuth.getCurrentUser().getEmail();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Harcamalar");


        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listeId = etListeId.getText().toString().trim();

                myRef.child(listeId).child("email").setValue(email);
                Intent intent = new Intent(RegisterList.this,ListelerActivity.class);
                startActivity(intent);
            }
        });


    }
}