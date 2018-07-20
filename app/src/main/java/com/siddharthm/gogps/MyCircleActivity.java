package com.siddharthm.gogps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyCircleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    FirebaseAuth auth;
    FirebaseUser user;
    CreateUsers createUsers;
    ArrayList<CreateUsers> nameList;
    DatabaseReference reference,user_reference;
    String circlememebrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        nameList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("CircleMember");
        user_reference = FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot dss:dataSnapshot.getChildren()){
                        circlememebrid = dss.child("circlememberid").getValue(String.class);
                        user_reference.child(circlememebrid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                createUsers = dataSnapshot.getValue(CreateUsers.class);
                                nameList.add(createUsers);
                                adapter.notifyDataSetChanged();;
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                Toast.makeText(MyCircleActivity.this,"Eroor Ocured",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


                Toast.makeText(MyCircleActivity.this,"Eroor Ocured",Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new MembersAdapter(nameList,getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
