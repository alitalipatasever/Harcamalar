package com.alitalipatasever.harcamalar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnProfile,btnAdd;

    FirebaseDatabase database;
    DatabaseReference myRef;

    ListView listView;
    List<Harcamalar> harcamaList;
    String readChildKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProfile = (Button) findViewById(R.id.profile);
        btnAdd = (Button) findViewById(R.id.add);
        listView = (ListView) findViewById(R.id.listview);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Harcamalar").child("");


        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Profile.class);
                startActivity(intent);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Add.class);
                startActivity(intent);
            }
        });

        harcamaList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Harcamalar harcama = harcamaList.get(position);

//                myRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        String readChildKey = snapshot.getKey();
//                        HashMap<String, String> readChild = (HashMap<String, String>) snapshot.getValue();
//                        Toast.makeText(getApplicationContext(),readChildKey,Toast.LENGTH_SHORT).show();
//                        //readChildKey = snapshot.getKey();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

                Intent intent = new Intent(getApplicationContext(),Update.class);
                intent.putExtra("aciklama",harcama.getAciklama());
                intent.putExtra("tutar",harcama.getTutar());
                intent.putExtra("id",harcama.getId());
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                harcamaList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Harcamalar harcama = dataSnapshot.getValue(Harcamalar.class);
                    harcamaList.add(harcama);
                }

                CustomAdapter adapter = new CustomAdapter(MainActivity.this, harcamaList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    public void Goster(String gunAdi){
//
//        DatabaseReference okuma = db.getReference(gunAdi);
//        okuma.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                Iterable<DataSnapshot> keys = snapshot.getChildren();
//                for(DataSnapshot key: keys){
//                    //txtcesit1.append(key.getValue().toString()+"\n");
//                    harcama.add(key.getValue().toString());
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}