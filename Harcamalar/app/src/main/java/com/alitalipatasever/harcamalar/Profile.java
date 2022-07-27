package com.alitalipatasever.harcamalar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    TextView txtEmail;
    Button btnEmail,btnPassword;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtEmail = (TextView) findViewById(R.id.txtEmail);
        btnEmail = (Button) findViewById(R.id.email);
        btnPassword = (Button) findViewById(R.id.password);



    }
}