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

public class Register extends AppCompatActivity {

    EditText ETusername, ETpassword;
    Button register,vazgec;
    FirebaseAuth firebaseAuth;
    private String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ETusername = (EditText) findViewById(R.id.username);
        ETpassword = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        vazgec = (Button) findViewById(R.id.back);

        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = ETusername.getText().toString();
                password = ETpassword.getText().toString();

                if(username.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Kullanıcı adı şifre alanları boş bırakılamaz!",Toast.LENGTH_SHORT).show();
                }else{
                    registerFunc();
                }

            }
        });
        vazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void registerFunc(){
        firebaseAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(Register.this,Login.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}