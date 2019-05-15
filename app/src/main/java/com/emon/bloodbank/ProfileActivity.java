package com.emon.bloodbank;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
TextView nameTv,numberTv,addressTv,lastDonateTv;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String uid;
    String name,number,address,lastDonate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameTv=findViewById(R.id.proNameTv);
        numberTv=findViewById(R.id.proNumberTv);
        addressTv=findViewById(R.id.proLocationTv);
        lastDonateTv=findViewById(R.id.proLastdonateTv);

      uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BLOOD");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("BLOOD");
        databaseReference.keepSynced(true);
        final Query data = reference.orderByChild("uid").equalTo(uid);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Model model = snapshot.getValue(Model.class);
                    name = model.getName();
                    number=model.getMobile();
                    address=model.getDisc();
                    lastDonate=model.getLastDonate();
                    nameTv.setText("Name : "+name);
                    numberTv.setText("Number : "+number);
                    addressTv.setText("Address : "+address);
                    lastDonateTv.setText("Last Donate : "+lastDonate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
