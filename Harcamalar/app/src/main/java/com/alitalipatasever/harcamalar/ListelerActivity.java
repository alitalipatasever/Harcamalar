package com.alitalipatasever.harcamalar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    ImageButton btnProfile;
    String email,gelenListeAdi, str, replaceEmail, normalEmail;
    Button btnListeKayit, btnYeniListe;
    TextView txtEmail;

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    //GridView listView;
    RecyclerView listView;
    List<Listeler> listeList;

    ArrayList<User> gelenUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listeler);

        txtEmail = (TextView) findViewById(R.id.txtEmail);
        btnProfile = (ImageButton) findViewById(R.id.profile);
        //listView = (GridView) findViewById(R.id.listview);
        listView = (RecyclerView) findViewById(R.id.listview);
        listView.setLayoutManager(new GridLayoutManager(this, 2));
        btnYeniListe = (Button) findViewById(R.id.yeniListe);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        email = firebaseAuth.getCurrentUser().getEmail();
        normalEmail = email;
        //Email ön ad
        String[] For_split_email = email.split("[@]");
        for (int j = 0; j <= For_split_email.length - 1; j++)
        {
            //System.out.println("splited emails----------" + For_split_email[j]);
            email = For_split_email[j];
        }
        email = For_split_email[0];
        txtEmail.setText(email);

        String replaceEmail1 = email.replace("@","_");
        replaceEmail = replaceEmail1.replace(".","_");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Harcamalar").child("");

        listeList = new ArrayList<>();


        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Listeler liste = listeList.get(position);

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("listeAdi",liste.getlisteAdi());
                intent.putParcelableArrayListExtra("users", liste.getUsers());

                gelenUsers = liste.getUsers();

                startActivity(intent);

            }
        });*/

        btnYeniListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListelerActivity.this,ListeAdd.class);
                startActivity(intent);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListelerActivity.this,Profile.class);
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

                        if (listeler1.email.equals(normalEmail) ||
                                dataSnapshot.getValue().toString().contains(replaceEmail)) {
                            listeList.add(listeler1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //CustomAdapterListeler adapter = new CustomAdapterListeler(ListelerActivity.this, listeList);
                //listView.setAdapter(adapter);
                CustomRecyclerAdapter customRecyclerAdapter = new CustomRecyclerAdapter(ListelerActivity.this, listeList, new CustomRecyclerAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(Listeler listeler) {
                        Listeler liste = listeler;
                        gelenListeAdi = liste.getlisteAdi();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("listeAdi",gelenListeAdi);
                        intent.putParcelableArrayListExtra("users", liste.getUsers());

                        gelenUsers = liste.getUsers();

                        startActivity(intent);
                    }
                });
                listView.setAdapter(customRecyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}