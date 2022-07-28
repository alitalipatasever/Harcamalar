package com.alitalipatasever.harcamalar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    TextView txtEmail;
    Button btnEmail,btnPassword;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseAuth.AuthStateListener authStateListener;
    String str;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtEmail = (TextView) findViewById(R.id.txtEmail);
        btnEmail = (Button) findViewById(R.id.email);
        btnPassword = (Button) findViewById(R.id.password);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    finish();
                }
            }
        };

        txtEmail.setText(firebaseAuth.getCurrentUser().getEmail());
//        signOutBttn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signOutFunc(); // sign out
//            }
//        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = "Yeni e-posta adresinizi giriniz!";
                changeEmailOrPassword(str,true);
            }
        });
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = "Yeni şifrenizi giriniz!";
                changeEmailOrPassword(str,false);
            }
        });

    }
    private void signOutFunc() {
        firebaseAuth.signOut();
    }

    private void changeEmailOrPassword(String title, final boolean option){
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        final EditText edit = new EditText( Profile.this);
        builder.setPositiveButton("evet",null);
        builder.setNegativeButton("hayır",null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        edit.setLayoutParams(lp);
        if(!option){
            edit.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        builder.setTitle("Değiştir");
        builder.setView(edit);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edit.getText().toString().isEmpty()){
                            edit.setError("Lütfen ilgili alanı doldurunuz!");
                        }else{
                            if(option){
                                changeEmail();
                            }else{
                                changePassword();
                            }
                        }
                    }
                });
            }
            private void changeEmail(){
                firebaseUser.updateEmail(edit.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"E-posta değiştirildi.",Toast.LENGTH_SHORT).show();
                            signOutFunc();
                        }else{
                            edit.setText("");
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            private void changePassword() {

                firebaseUser.updatePassword(edit.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Profile.this, "Şifre değiştirildi.", Toast.LENGTH_LONG).show();
                                    signOutFunc();
                                } else {
                                    edit.setText("");
                                    Toast.makeText(Profile.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        alertDialog.show();
    }

}