package com.tech.adikthealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class pcrregister extends AppCompatActivity {

    EditText name,nic,noPcr,address,mobile,email;
    Button buttonSave,buttonBack;
    TextView pcrPrice;
    pcrModel pcrpatient;

    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcrregister);
        name= findViewById(R.id.textpnamepcr);
        nic= findViewById(R.id.txtnic);
        noPcr=findViewById(R.id.txtnopcr);
        address=findViewById(R.id.txtaddress);
        mobile=findViewById(R.id.txtmobile);
        email=findViewById(R.id.txtemail2);

        pcrPrice= findViewById(R.id.pcrprice);


        buttonSave=findViewById(R.id.buttonSave);
        buttonBack=findViewById(R.id.buttonBack);

        pcrpatient =new pcrModel();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertpcrData();

                clearPCR();



            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private  void insertpcrData(){
        if(TextUtils.isEmpty(name.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Please Enter Name",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(nic.getText().toString().trim())) {
            Toast.makeText(getApplicationContext(), "Please Enter NIC", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(noPcr.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "Please Enter Count of PCR", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(address.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Please Enter Address",Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(mobile.getText().toString().trim())){
            Toast.makeText(getApplicationContext(),"Please Enter Mobile NO",Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(email.getText().toString().trim())){
            Toast.makeText(getApplicationContext(), "Please Enter  Email ", Toast.LENGTH_LONG).show();
        }else {

            Map<String, Object> map = new HashMap<>();
            map.put("name", name.getText().toString());
            map.put("nic", nic.getText().toString());
            map.put("noPcr",noPcr.getText().toString());
            map.put("address", address.getText().toString());
            map.put("mobile", mobile.getText().toString());
            map.put("email", email.getText().toString());

            Integer no = Integer.valueOf(noPcr.getText().toString());
            String pcrPrice  = String.valueOf(no*1500);
            map.put("pcrPrice",String.valueOf(pcrPrice));





            FirebaseDatabase.getInstance("https://adikt-healthcare-default-rtdb.firebaseio.com/")
                    .getReference().child("patients_pcr").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(pcrregister.this, "Data Added Successfully", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(pcrregister.this, "Error!!!", Toast.LENGTH_LONG).show();

                        }
                    });
        }

    }
    private void clearPCR(){
        name.setText("");
        nic.setText("");
        noPcr.setText("");
        address.setText("");
        mobile.setText("");
        email.setText("");



    }




}