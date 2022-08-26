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

    ArrayList<User> gelenUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listeler);

        listView = (ListView) findViewById(R.id.listview);
        btnYeniListe = (Button) findViewById(R.id.yeniListe);
        btnListeKayit = (Button) findViewById(R.id.listeKayit);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseAuth.getCurrentUser().getEmail();

        String replaceEmail1 = email.replace("@","_");
        String replaceEmail = replaceEmail1.replace(".","_");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Harcamalar").child(replaceEmail);

        listeList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Listeler liste = listeList.get(position);

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("listeAdi",liste.getlisteAdi());
                intent.putParcelableArrayListExtra("users", liste.getUsers());

                gelenUsers = liste.getUsers();

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
        btnListeKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListelerActivity.this,RegisterList.class);
                intent.putParcelableArrayListExtra("users", gelenUsers);
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
                    try {
                        Listeler listeler1 = dataSnapshot.getValue(Listeler.class);
                        listeList.add(listeler1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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