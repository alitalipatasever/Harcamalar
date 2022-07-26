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

import java.util.ArrayList;
import java.util.List;

public class RegisterList extends AppCompatActivity {
    EditText etEmail,etListeAdi;
    Button btnKaydet, btnVazgec;

    FirebaseDatabase database;
    DatabaseReference myRef,myRef2;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    String email,registerEmail,registerListeAdi,gelenListeAdi;

    ArrayList<User> gelenUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_list);

        etEmail = (EditText) findViewById(R.id.email);
        etListeAdi = (EditText) findViewById(R.id.listeAdi);
        btnKaydet = (Button) findViewById(R.id.add);
        btnVazgec = (Button) findViewById(R.id.vazgec);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseAuth.getCurrentUser().getEmail();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Harcamalar");
        myRef2 = database.getReference("Listeler");

        Intent intent = getIntent();

        gelenListeAdi = intent.getStringExtra("listeAdi");
        gelenUsers = intent.getParcelableArrayListExtra("users");

        btnVazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEmail = etEmail.getText().toString().trim();

                String replaceEmail1 = registerEmail.replace("@","_");
                String replaceEmail = replaceEmail1.replace(".","_");

                List<User> arrayList = new ArrayList<>();
                if (gelenUsers != null) {
                    arrayList = gelenUsers;
                }
                User user = new User();
                user.setUserEmail(email);
                arrayList.add(user);
                myRef.child(gelenListeAdi).child(replaceEmail).setValue(replaceEmail);
                myRef2.child(gelenListeAdi).child(replaceEmail).setValue(replaceEmail);
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