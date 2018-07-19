package com.siddharthm.gogps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null){
            setContentView(R.layout.activity_main);
        }else {
            Intent myIntent = new Intent(MainActivity.this,MyNavigationActivity.class);
            startActivity(myIntent);
            finish();
        }

    }
    public void goToLogin(View v){
        Intent myIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(myIntent);
    }
    public void goToRegister(View v){
        Intent myIntent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(myIntent);
    }

}
