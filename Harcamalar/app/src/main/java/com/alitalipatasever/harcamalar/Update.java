package com.alitalipatasever.harcamalar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Update extends AppCompatActivity {

    EditText ETaciklama, ETtutar;
    Button btnDelete, btnUpdate;
    FirebaseDatabase db;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String email, tarih, gelenListeAdi,gelenId,gelenEmail,eskiLog;
    Context context;

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


        db = FirebaseDatabase.getInstance();
        //String readChildKey = db.getReference("Harcamalar").child("").getKey();

        Intent intent = getIntent();

        String gelenAciklama = intent.getStringExtra("aciklama");
        String gelenTutar = intent.getStringExtra("tutar");
        gelenId = intent.getStringExtra("harcamaId");
        String gelenListeId = intent.getStringExtra("listeId");
        gelenListeAdi = intent.getStringExtra("listeAdi");
        gelenEmail = intent.getStringExtra("email");

        ETaciklama.setText(gelenAciklama);
        ETtutar.setText(gelenTutar);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tarih = tarih + " \n"+email+" güncelledi.";
                String aciklama = ETaciklama.getText().toString();
                String tutar = ETtutar.getText().toString();

                myRef = FirebaseDatabase.getInstance().getReference("Harcamalar").child(gelenListeAdi).child(gelenId);

                Harcamalar harcama = new Harcamalar(gelenEmail,tarih,aciklama,tutar,gelenId);
                myRef.setValue(harcama).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });

            }
        });

        myRef = FirebaseDatabase.getInstance().getReference("Harcamalar").child(gelenListeAdi).child("log");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eskiLog = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Update.this);
                dialog.setContentView(R.layout.activity_custom_dialog);
                TextView textView = (TextView)findViewById(R.id.TVtitle);
                //textView.setText("Silmek istediğinize emin misiniz?");
                Button btnEvet = (Button)dialog.findViewById(R.id.BtnTamam);
                btnEvet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myRef = FirebaseDatabase.getInstance().getReference("Harcamalar").child(gelenListeAdi).child(gelenId);
                        myRef.removeValue();
                        myRef = FirebaseDatabase.getInstance().getReference("Harcamalar").child(gelenListeAdi);
                        String log = "Id: "+gelenId+" Tarih: "+tarih+" Açıklama: "+gelenAciklama
                                +" Tutar: "+gelenTutar+" E-mail: "+email+" SİLDİ";
                        myRef.child("log").setValue(eskiLog+"\n---\n"+log);
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



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}