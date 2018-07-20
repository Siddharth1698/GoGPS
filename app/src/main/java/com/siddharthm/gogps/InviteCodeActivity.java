package com.siddharthm.gogps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);
        t1 = (TextView)findViewById(R.id.tv123);
        t2 = (TextView)findViewById(R.id.textView22);
        b3 = (Button)findViewById(R.id.button8);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference().child("User_Images");
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
                    user = auth.getCurrentUser();
                    CreateUsers createUsers = new CreateUsers(name,email,password,code,"false","na","na","na",user.getUid());
                    UserId = user.getUid();
                    reference.child(UserId).setValue(createUsers).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                                StorageReference sr = storageReference.child(user.getUid()+ ".jpg");
                                sr.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if (task.isSuccessful()){
                                            String download_image_patch = task.getResult().getDownloadUrl().toString();
                                            reference.child(user.getUid()).child("imageUrl").setValue(download_image_patch).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        dialog.dismiss();
                                                        Intent myIntent = new Intent(InviteCodeActivity.this,MainActivity.class);
                                                        sendVerificationEmail();
                                                        startActivity(myIntent);
                                                    }
                                                    else{
                                                        Toast.makeText(InviteCodeActivity.this,"Error occured while registration",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }
                                    }
                                });
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

    private void sendVerificationEmail() {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(InviteCodeActivity.this,"Email Sent for Verification",Toast.LENGTH_SHORT).show();
                        finish();
                        auth.signOut();
                    }else {
                        Toast.makeText(InviteCodeActivity.this,"Could not send verication mail",Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

}
