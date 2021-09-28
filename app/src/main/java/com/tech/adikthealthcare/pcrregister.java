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
import android.util.Patterns;


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

        String pname= name.getText().toString().trim();
        String pnic = nic.getText().toString().trim();
        String pnopcr = noPcr.getText().toString().trim();
        String paddress= address.getText().toString().trim();
        String  pmobile= mobile.getText().toString().trim();
        String pemail = email.getText().toString().trim();

        if(pname.isEmpty()) {
            name.setError("Name is required");
            name.requestFocus();
        }
        else if(pnic.isEmpty()){
            nic.setError("NIC is required");
        }
        else if(!pnic.matches("[0-9+]{10}[vV|xX]$")){
            nic.setError("Please enter valid NIC");
            nic.requestFocus();

        }
        else if (pnopcr.isEmpty()) {
            noPcr.setError("No PCR is required");
            noPcr.requestFocus();
        }else if(paddress.isEmpty()){
            address.setError("Address is required ");
            address.requestFocus();
        }else if(pmobile.isEmpty()){
            mobile.setError("Mobile is Required");
            mobile.requestFocus();
        }
        else if(!pmobile.matches("[0-9]{10}$")){
            mobile.setError("Please enter valid Mobile number");
            mobile.requestFocus();

        }

        else if(pemail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
        }
        else if (!pemail.matches("[a-zA-Z0-9.!#$%&'+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)$")){
            email.setError("Please enter valid Email");
            email.requestFocus();
        }


        else {


            Map<String, Object> map = new HashMap<>();
            map.put("name", name.getText().toString());
            map.put("nic", nic.getText().toString());
            map.put("noPcr", noPcr.getText().toString());
            map.put("address", address.getText().toString());
            map.put("mobile", mobile.getText().toString());
            map.put("email", email.getText().toString());

            Integer no = Integer.parseInt(noPcr.getText().toString());
            String pcrPrice = String.valueOf(no * 1500);
            map.put("pcrPrice", String.valueOf(pcrPrice));


            FirebaseDatabase.getInstance("https://adikt-healthcare-default-rtdb.firebaseio.com/")
                    .getReference().child("patients_pcr").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(pcrregister.this, "Data Added Successfully", Toast.LENGTH_LONG).show();
                            clearPCR();
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