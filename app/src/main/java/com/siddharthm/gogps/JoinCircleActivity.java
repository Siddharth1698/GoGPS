package com.siddharthm.gogps;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JoinCircleActivity extends AppCompatActivity {
    Pinview pinview;
    DatabaseReference reference,currentReference;
    FirebaseUser user;
    String current_user_id,join_userId;
    FirebaseAuth auth;
    private String name,image,name1,image1;
    DatabaseReference circleReference,circleReference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_circle);
        pinview = (Pinview)findViewById(R.id.pinview);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        currentReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        current_user_id = user.getUid();
        FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = (String) dataSnapshot.child("name").getValue();
                image = (String) dataSnapshot.child("imageUrl").getValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void submitButtonClick(View v){
        Query query = reference.orderByChild("code").equalTo(pinview.getValue());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    CreateUsers createUsers = null;
                    for (DataSnapshot childDss: dataSnapshot.getChildren() ){
                        createUsers = childDss.getValue(CreateUsers.class);
                        join_userId = createUsers.user_id;

                        circleReference = FirebaseDatabase.getInstance().getReference().child("Circles").child(join_userId).child("CircleMembers");

                        circleReference1 = FirebaseDatabase.getInstance().getReference().child("Circles").child(current_user_id).child("CircleMembers");

                        FirebaseDatabase.getInstance().getReference().child("Users").child(join_userId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                name1 = (String) dataSnapshot.child("name").getValue();
                                image1 = (String) dataSnapshot.child("imageUrl").getValue();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                        CircleJoin circleJoin = new CircleJoin(current_user_id);
                        CircleJoin circleJoin1 = new CircleJoin(join_userId);

                        circleReference.child(user.getUid()).setValue(circleJoin).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    circleReference.child(current_user_id).child("name").setValue(name);
                                    circleReference.child(current_user_id).child("imageUrl").setValue(image);
                                    Toast.makeText(JoinCircleActivity.this,"User Joined Circle Succesfully",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });




                        circleReference1.child(join_userId).setValue(circleJoin1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    circleReference1.child(join_userId).child("name").setValue(name1);
                                    circleReference1.child(join_userId).child("imageUrl").setValue(image1);
                                    Toast.makeText(JoinCircleActivity.this,"User Joined Circle Succesfully",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                } else {
                    Toast.makeText(JoinCircleActivity.this,"Circle Code not valid",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
