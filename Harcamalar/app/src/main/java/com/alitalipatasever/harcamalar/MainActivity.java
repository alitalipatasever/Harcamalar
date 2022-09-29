package com.alitalipatasever.harcamalar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabAdd;
    TextView txtToplam, txtListeAdi, txtKisiToplamHarcama,txtLog;
    Button btnRegisterList,btnListeSil;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String email, gelenListeAdi;
    ArrayList<String> gelenUsers;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myRef2;

    ListView listView;
    List<Harcamalar> harcamaList;
    String replaceEmail,yeniLog;
    static String log;
    Context context;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fabAdd = findViewById(R.id.fabAdd);
        listView = (ListView) findViewById(R.id.listview);
        txtToplam = (TextView) findViewById(R.id.txtToplam);
        txtLog = (TextView) findViewById(R.id.tvLog);
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
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_custom_dialog);
                TextView textView = (TextView)findViewById(R.id.TVtitle);
                //textView.setText("Silmek istediğinize emin misiniz?");
                Button btnEvet = (Button)dialog.findViewById(R.id.BtnTamam);
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

        txtLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.activity_custom_dialog_tamam);
                TextView textView = (TextView) dialog.findViewById(R.id.TVtitle);
                textView.setText(yeniLog);
                Button btnTamam = (Button)dialog.findViewById(R.id.BtnTamam);
                btnTamam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
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

        myRef2 = FirebaseDatabase.getInstance().getReference("Harcamalar").child(gelenListeAdi).child("log");
        // Read from the database
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                yeniLog = dataSnapshot.getValue(String.class);
                //txtLog.setText(yeniLog);
                //Toast.makeText(MainActivity.this, yeniLog, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }

}