package com.siddharthm.gogps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyCircleActivity extends AppCompatActivity {
    RecyclerView mUsersList;

    FirebaseAuth auth;
    FirebaseUser user;
    CreateUsers createUsers;
    DatabaseReference reference,user_reference;
    String circlememebrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle);
        mUsersList = (RecyclerView)findViewById(R.id.recyclerView);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Circles").child(user.getUid()).child("CircleMembers");
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerAdapter<CreateUsers, MyCircleActivityViewHolder> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<CreateUsers, MyCircleActivityViewHolder>(CreateUsers.class,R.layout.card_layout
                ,MyCircleActivityViewHolder.class,
                user_reference) {
            @Override
            protected void populateViewHolder(MyCircleActivityViewHolder viewHolder, CreateUsers model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setImage(model.getImageUrl(),getApplicationContext());


            }
        };
        mUsersList.setAdapter(firebaseRecyclerAdapter);
    }
    public static class MyCircleActivityViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public MyCircleActivityViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name){
            TextView mUserNameView = mView.findViewById(R.id.item_Title);
            mUserNameView.setText(name);

        }

        public void setImage(String image, Context ctx) {
            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.circleimageview);
            Picasso.get().load(image).into(userImageView);
        }
    }

}


