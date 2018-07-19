package com.siddharthm.gogps;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText e1,e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1 = (EditText)findViewById(R.id.editText);
        e2 = (EditText)findViewById(R.id.editText2);
        auth = FirebaseAuth.getInstance();
    }
    public void login(View v){
        auth.signInWithEmailAndPassword(e1.getText().toString(),e2.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(LoginActivity.this,"User has succesfully Logged in",Toast.LENGTH_SHORT).show();
                           Intent myIntent = new Intent(LoginActivity.this,MyNavigationActivity.class);
                           startActivity(myIntent);
                           finish();

                       }else {
                           Toast.makeText(LoginActivity.this,"Wrong email and password",Toast.LENGTH_SHORT).show();
                       }
                    }
                });
    }
}
