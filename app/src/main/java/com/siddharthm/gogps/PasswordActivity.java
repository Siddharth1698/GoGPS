package com.siddharthm.gogps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {
    private String email;
    private EditText e4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        e4 = (EditText)findViewById(R.id.editText4);
        Intent myIntent  = getIntent();
        if (myIntent!=null){
            email = myIntent.getStringExtra("email");
        }
    }

    public void goToNamePickActivity(View v){
        if (e4.getText().toString().length()>6){
            Intent myIntemt = new Intent(PasswordActivity.this,NameActivity.class);
            myIntemt.putExtra("email",email);
            myIntemt.putExtra("password",e4.getText().toString());
            startActivity(myIntemt);
            finish();
        }else{
            Toast.makeText(PasswordActivity.this,"Password length should be more than 6",Toast.LENGTH_SHORT).show();
        }
    }
}
