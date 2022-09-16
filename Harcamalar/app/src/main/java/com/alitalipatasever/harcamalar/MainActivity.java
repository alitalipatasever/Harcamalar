package com.alitalipatasever.harcamalar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabAdd;
    TextView txtToplam, txtListeAdi;
    Button btnRegisterList;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String email, gelenListeAdi;
    ArrayList<String> gelenUsers;
    FirebaseDatabase database;
    DatabaseReference myRef;

    ListView listView;
    List<Harcamalar> harcamaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fabAdd = findViewById(R.id.fabAdd);
        listView = (ListView) findViewById(R.id.listview);
        txtToplam = (TextView) findViewById(R.id.txtToplam);
        txtListeAdi = (TextView) findViewById(R.id.listeAdi);
        btnRegisterList = (Button) findViewById(R.id.kisiEkle);

        harcamaList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseAuth.getCurrentUser().getEmail();

        String replaceEmail1 = email.replace("@","_");
        String replaceEmail = replaceEmail1.replace(".","_");

        Intent intent = getIntent();
        gelenListeAdi = intent.getStringExtra("listeAdi");
        gelenUsers = intent.getParcelableExtra("users");

        txtListeAdi.setText(gelenListeAdi);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Harcamalar").child(gelenListeAdi);

        btnRegisterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterList.class);
                intent.putExtra("listeAdi",gelenListeAdi);
                startActivity(intent);
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Add.class);
                intent.putExtra("listeAdi",gelenListeAdi);
                intent.putExtra("users",gelenUsers);
                startActivity(intent);
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Harcamalar harcama = harcamaList.get(position);

                Intent intent = new Intent(getApplicationContext(),Update.class);
                intent.putExtra("aciklama",harcama.getAciklama());
                intent.putExtra("tutar",harcama.getTutar());
                intent.putExtra("id",harcama.getId());
                intent.putExtra("listeAdi",gelenListeAdi);
                //intent.putExtra("listeId",gelenListeId);
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

                harcamaList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        Harcamalar harcama = dataSnapshot.getValue(Harcamalar.class);
                        harcamaList.add(harcama);
                    } catch (Exception e) {
                     e.printStackTrace();
                    }
                }

                float toplamTutar = 0;

                for (int i = 0; i < harcamaList.size(); i++) {
                    if (harcamaList.get(i).getTutar() != null) {
                        toplamTutar += Float.parseFloat(harcamaList.get(i).getTutar().replace(",","."));
                    }
                }
                txtToplam.setText(String.valueOf(toplamTutar).replace(".0","")+" ₺");
                //Toast.makeText(MainActivity.this, "Toplam Tutar: " + String.valueOf(toplamTutar).replace(".0",""), Toast.LENGTH_SHORT).show();

                CustomAdapter adapter = new CustomAdapter(MainActivity.this, harcamaList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}