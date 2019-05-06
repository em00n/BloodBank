package com.emon.bloodbank;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BloodgrouplistActivity extends AppCompatActivity {
    Button addBTN,updateBTN;
    RecyclerView recyclerView;

    String number="";
    private static final int REQUEST_CALL =1;
    String callnum="";
//firebase

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Post> option;
    FirebaseRecyclerAdapter<Post,MyRecyclerViewHolder> adapter;

    Post selectedpost;
    String selectedkey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodgrouplist);

        //recyclerview
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //firebase
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("BLOOD");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        showData();

    }
    @Override
    protected void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showData();
    }

    //show data
    private void showData() {
        option= new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(databaseReference,Post.class)
                .build();
        adapter= new FirebaseRecyclerAdapter<Post, MyRecyclerViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position, @NonNull final Post model) {
                holder.nameTV.setText(model.getName());
                holder.distTV.setText(model.getDisc());
                holder.bloodTV.setText(model.getBlood());

                holder.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public void onLongClick(View view, int position) {
                       // selectedkey=getSnapshots().getSnapshot(position).getKey();
                        callnum=model.getMobile();
                        call(position);
                    }

//                            @Override
//                            public void onClick(View view, int position) {
//
//                            }
                });
            }

            @NonNull
            @Override
            public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView= LayoutInflater.from(getBaseContext()).inflate(R.layout.item_post,parent,false);
                return new MyRecyclerViewHolder(itemView);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    //update and delete
    public void call(int view) {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Call Blood Doner");
        alert.setCancelable(false);

        alert.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                number=callnum;
                makePhoneCall();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        final AlertDialog alertDialog=alert.create();
        alertDialog.show();

    }



    //phoncall make
    private void makePhoneCall(){

        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(BloodgrouplistActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(BloodgrouplistActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(BloodgrouplistActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
