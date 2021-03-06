package com.siddharthm.gogps;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.sql.BatchUpdateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class NameActivity extends AppCompatActivity {
    String email,password;
    private CircleImageView profileImage;
    private EditText editText5;
    private Button button5;
    private Uri resultUri;
    private  String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        editText5 = (EditText)findViewById(R.id.editText5);
        button5 = (Button)findViewById(R.id.button5);
        profileImage = (CircleImageView)findViewById(R.id.profile_image);

        Intent myIntent = getIntent();
        if (myIntent!=null){
            email = myIntent.getStringExtra("email");
            password = myIntent.getStringExtra("password");
        }


    }
    public void generateCode(View v){
        Date myDate = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        String date = format1.format(myDate);
        Random r = new Random();
        int n = 100000 + r.nextInt(900000);
        code = String.valueOf(n);
        if (resultUri!=null){
            Intent myIntent = new Intent(NameActivity.this,InviteCodeActivity.class);
            myIntent.putExtra("name",editText5.getText().toString());
            myIntent.putExtra("email",email);
            myIntent.putExtra("password",password);
            myIntent.putExtra("date",date);
            myIntent.putExtra("isSharing","false");
            myIntent.putExtra("code",code);
            myIntent.putExtra("imageUri",resultUri);

            startActivity(myIntent);
            finish();

        }else{
            Toast.makeText(NameActivity.this,"Please Choose an image",Toast.LENGTH_SHORT).show();
        }

    }

    public void SelectImage(View v){
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 12 && resultCode == RESULT_OK && data!=null){
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                profileImage.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
