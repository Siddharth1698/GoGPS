package com.siddharthm.gogps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InviteCodeActivity extends AppCompatActivity {
    private String name,email,password,date,isSharing,code;
    private Uri imageUri;
    private TextView t1,t2;
    Button b3;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    ProgressDialog dialog;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);
        t1 = (TextView)findViewById(R.id.tv123);
        t2 = (TextView)findViewById(R.id.textView22);
        b3 = (Button)findViewById(R.id.button8);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        dialog = new ProgressDialog(this);
        Intent myIntent = getIntent();
        if (myIntent!=null){
            name = myIntent.getStringExtra("name");
            email = myIntent.getStringExtra("email");
            password = myIntent.getStringExtra("password");
            code = myIntent.getStringExtra("code");
            isSharing = myIntent.getStringExtra("isSharing");
            imageUri = myIntent.getParcelableExtra("imageUri");

        }
        t1.setText(code);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }


    public void registerUser(){

        dialog.setMessage("Please wait while we are creating an Account for you");
        dialog.show();

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    CreateUsers createUsers = new CreateUsers(name,email,password,code,"false","na","na","na");
                    user = auth.getCurrentUser();
                    UserId = user.getUid();
                    reference.child(UserId).setValue(createUsers).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(InviteCodeActivity.this,"Users Registered Sucessfully",Toast.LENGTH_SHORT).show();
                                Intent mYiNTENT = new Intent(InviteCodeActivity.this,UserLocationMainActivity.class);
                                startActivity(mYiNTENT);
                            }else {
                                dialog.dismiss();
                                Toast.makeText(InviteCodeActivity.this,"Registration failed. Try Again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }

}
