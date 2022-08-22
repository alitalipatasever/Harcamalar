package com.alitalipatasever.harcamalar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListelerActivity extends AppCompatActivity {

    String email;
    Button btnListeKayit, btnYeniListe;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    ListView listView;
    List<Listeler> listeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listeler);

        listView = (ListView) findViewById(R.id.listview);
        btnYeniListe = (Button) findViewById(R.id.yeniListe);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseAuth.getCurrentUser().getEmail();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Harcamalar").child("");

        listeList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Listeler listeler1 = listeList.get(position);

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("id",listeler1.getId());
                startActivity(intent);

            }
        });
        btnYeniListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListelerActivity.this,ListeAdd.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listeList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Listeler listeler1 = dataSnapshot.getValue(Listeler.class);
                    listeList.add(listeler1);
                }

                CustomAdapterListeler adapter = new CustomAdapterListeler(ListelerActivity.this, listeList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}