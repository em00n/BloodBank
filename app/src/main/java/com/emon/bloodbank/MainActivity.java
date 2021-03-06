package com.emon.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
private  long backPressedTime=1;
CardView donarList,searchDoner,post,rules;
ViewFlipper viewFlipper;
    String dist, blood, name, mobile,lastDonate,uid;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       donarList=findViewById(R.id.donerList);
       searchDoner=findViewById(R.id.searchDonner);
       post=findViewById(R.id.post);
       rules=findViewById(R.id.rules);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("BLOOD");


       name=getIntent().getStringExtra("name");
       blood=getIntent().getStringExtra("blood");
       dist=getIntent().getStringExtra("dist");
       mobile=getIntent().getStringExtra("mobile");
       lastDonate=getIntent().getStringExtra("lastDonate");
        viewFlipper=findViewById(R.id.slideVF);
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(dist) && !TextUtils.isEmpty(blood)&&!TextUtils.isEmpty(lastDonate)) {
            addData();
        }


        //image sliding
        int [] images={R.drawable.four,R.drawable.one,R.drawable.two,R.drawable.three,};
        for (int image:images){
            flipperImage(image);
        }


        donarList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BloodgrouplistActivity.class));
            }
        });
        searchDoner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));

            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ShowActivity.class));
            }
        });
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RulesActivity.class));
            }
        });

    }



    private void addData() {

        Model model = new Model(name, mobile, dist, blood,lastDonate,uid);
        databaseReference.push()
                .setValue(model);
    }

    //this methode use in slideing
    public void flipperImage(int image){
        ImageView imageView=new ImageView(this);
        imageView.setImageResource(image);
       imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000); // 2 sec
        viewFlipper.setAutoStart(true);
        //animation
        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            return true;
        }
        else  if (id == R.id.profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
