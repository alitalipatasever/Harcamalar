package com.alitalipatasever.harcamalar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText ETusername,ETpassword;
    Button btnLogin, btnRegister;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String username,password;
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ETusername = (EditText) findViewById(R.id.username);
        ETpassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.register);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = ETusername.getText().toString().trim();
                password = ETpassword.getText().toString().trim();
                if(username.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Kullanıcı adı şifre alanları boş bırakılamaz!",Toast.LENGTH_SHORT).show();
                }else{
                    loginFunc();
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });


    }
    public void loginFunc(){
        firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    String email = firebaseAuth.getCurrentUser().getEmail();
                    String[] For_split_email = email.split("[@]");
                    for (int j = 0; j <= For_split_email.length - 1; j++)
                    {
                        //System.out.println("splited emails----------" + For_split_email[j]);
                        email = For_split_email[j];
                    }
                    email = For_split_email[0];

                    Toast.makeText(getApplicationContext(),"Hoşgeldin, "+email,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}