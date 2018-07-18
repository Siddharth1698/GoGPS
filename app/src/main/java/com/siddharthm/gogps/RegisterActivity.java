package com.siddharthm.gogps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;

public class RegisterActivity extends AppCompatActivity {
    private EditText e1;
    private Button b1;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        e1 = (EditText)findViewById(R.id.editText3);
        b1 = (Button)findViewById(R.id.button3);
        progressDialog = new ProgressDialog(this);

    }
    public goToPasswordActivity(View v){
        progressDialog.setMessage("Checking Email Address");
        progressDialog.show();
        auth.fetchProvidersForEmail(e1.getText().toString()).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                  if (task.isSuccessful()){
                      progressDialog.dismiss();
                      boolean check = !task.getResult().getProviders().isEmpty();
                      if (!check){
                          Intent myIntent = new Intent(RegisterActivity.this,PasswordActivity.class);
                          myIntent.putExtra("email",e1.getText().toString());
                          startActivity(myIntent);

                      }else {
                          progressDialog.dismiss();
                          Toast.makeText(RegisterActivity.this,"This email is already registered",Toast.LENGTH_SHORT).show();

                      }
                  }
            }
        });

    }
}
