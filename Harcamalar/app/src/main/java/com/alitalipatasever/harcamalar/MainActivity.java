package com.alitalipatasever.harcamalar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
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
    TextView txtToplam, txtListeAdi, txtKisiToplamHarcama;
    Button btnRegisterList,btnListeSil;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String email, gelenListeAdi;
    ArrayList<String> gelenUsers;
    FirebaseDatabase database;
    DatabaseReference myRef;

    ListView listView;
    List<Harcamalar> harcamaList;
    String replaceEmail;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fabAdd = findViewById(R.id.fabAdd);
        listView = (ListView) findViewById(R.id.listview);
        txtToplam = (TextView) findViewById(R.id.txtToplam);
        txtKisiToplamHarcama = (TextView) findViewById(R.id.kisiToplamHarcama);
        txtListeAdi = (TextView) findViewById(R.id.listeAdi);
        btnRegisterList = (Button) findViewById(R.id.kisiEkle);
        btnListeSil = (Button) findViewById(R.id.listeSil);

        harcamaList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseAuth.getCurrentUser().getEmail();

        String replaceEmail1 = email.replace("@","_");
        replaceEmail = replaceEmail1.replace(".","_");

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
        btnListeSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);
                TextView textView = (TextView)findViewById(R.id.TVtitle);
                textView.setText("Silmek istediğinize emin misiniz?");
                Button btnEvet = (Button)dialog.findViewById(R.id.BtnEvet);
                btnEvet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myRef = FirebaseDatabase.getInstance().getReference("Harcamalar").child(gelenListeAdi);
                        myRef.removeValue();
                        finish();
                    }
                });
                Button btnHayir = (Button)dialog.findViewById(R.id.BtnHayir);
                btnHayir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Harcamalar harcama = harcamaList.get(position);

                Intent intent = new Intent(getApplicationContext(),Update.class);
                intent.putExtra("aciklama",harcama.getAciklama());
                intent.putExtra("tutar",harcama.getTutar());
                intent.putExtra("harcamaId",harcama.getId());
                intent.putExtra("listeAdi",gelenListeAdi);
                intent.putExtra("email",harcama.getEmail());
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

                float toplamTutar = 0, kisiToplamHarcama = 0;

                for (int i = 0; i < harcamaList.size(); i++) {
                    if (harcamaList.get(i).getTutar() != null) {
                        toplamTutar += Float.parseFloat(harcamaList.get(i).getTutar().replace(",","."));
                    }
                    if(harcamaList.get(i).getEmail().equals(email)){
                        kisiToplamHarcama += Float.parseFloat(harcamaList.get(i).getTutar().replace(",","."));
                    }
                }
                txtToplam.setText(String.valueOf(toplamTutar).replace(".0","")+" ₺");
                txtKisiToplamHarcama.setText("Ben: "+String.valueOf(kisiToplamHarcama).replace(".0","")+" ₺");

                CustomAdapter adapter = new CustomAdapter(MainActivity.this, harcamaList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}