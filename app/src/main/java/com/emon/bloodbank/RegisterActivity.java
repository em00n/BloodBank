package com.emon.bloodbank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText mEmailEdit, mPasswordEdit, nameET, mobileET;
    Spinner distSP, bloodSP;
    private Button mRegister;
    private Button mLogin;
    private TextView gotoLogin;
    private FirebaseAuth mAuth;

    String dist, blood, name, mobile;
    String []bloodGroup;
    String []distGroup;
    ArrayAdapter<String>adapter;
    ArrayAdapter<String>adapter2;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailEdit = (EditText) findViewById(R.id.email);
        mPasswordEdit = (EditText) findViewById(R.id.password);
        nameET = findViewById(R.id.name);
        mobileET = findViewById(R.id.number);
        distSP = findViewById(R.id.dist);
        bloodSP = findViewById(R.id.blood);

        bloodGroup=getResources().getStringArray(R.array.blood_array);
        adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,bloodGroup);
        bloodSP.setAdapter(adapter);

        distGroup=getResources().getStringArray(R.array.dist_array);
        adapter2=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,distGroup);
        distSP.setAdapter(adapter2);




        mRegister = (Button) findViewById(R.id.register);
        mLogin = (Button) findViewById(R.id.login);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("BLOOD");

        mAuth = FirebaseAuth.getInstance();

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, BloodgrouplistActivity.class));
            finish();
        }
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameET.getText().toString().trim();
                mobile = mobileET.getText().toString().trim();
                dist = distSP.getSelectedItem().toString();
                blood = bloodSP.getSelectedItem().toString();
                String email = mEmailEdit.getText().toString().trim();
                String password = mPasswordEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(dist) && !TextUtils.isEmpty(blood)) {
                    addData();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Intent intent = new Intent(RegisterActivity.this, BloodgrouplistActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void addData() {

        Post post = new Post(name, mobile, dist, blood);
        databaseReference.push()
                .setValue(post);
    }
}
