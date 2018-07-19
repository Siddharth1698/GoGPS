package com.siddharthm.gogps;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InviteCodeActivity extends AppCompatActivity {
    private String name,email,password,date,isSharing,code;
    private Uri imageUri;
    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);
        t1 = (TextView)findViewById(R.id.textView);
        Intent myIntent = getIntent();
        if (myIntent!=null){
            name = myIntent.getStringExtra("name");
            email = myIntent.getStringExtra("email");
            password = myIntent.getStringExtra("password");
            date = myIntent.getStringExtra("date");
            isSharing = myIntent.getStringExtra("isSharing");
            imageUri = myIntent.getParcelableExtra("imageUri");

        }
        t1.setText(code);
    }

}
