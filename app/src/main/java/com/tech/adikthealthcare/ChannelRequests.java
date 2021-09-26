package com.tech.adikthealthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tech.adikthealthcare.Models.ChannelDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChannelRequests extends AppCompatActivity {

    ListView listView;
    List<ChannelDetails> user;
    DatabaseReference ref;
    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_requests);

        listView = (ListView) findViewById(R.id.requestslist);
        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("ChannelRequests");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    ChannelDetails channelDetails = studentDatasnap.getValue(ChannelDetails.class);
                    user.add(channelDetails);

                }

                MyAdapter adapter = new MyAdapter(ChannelRequests.this, R.layout.custom_channel_reqests_details, (ArrayList<ChannelDetails>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        ImageView imageView1;
        Button button1;
        Button button2;
        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        TextView COL5;
        TextView COL6;
        TextView COL7;
        TextView COL8;
        TextView COL9;
    }

    class MyAdapter extends ArrayAdapter<ChannelDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<ChannelDetails> user;


        public MyAdapter(Context context, int resource, ArrayList<ChannelDetails> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_channel_reqests_details, null);

                holder.COL1 = (TextView) view.findViewById(R.id.cd_idd);
                holder.COL2 = (TextView) view.findViewById(R.id.cd_Namee);
                holder.COL3 = (TextView) view.findViewById(R.id.cd_Speacialistt);
                holder.COL4 = (TextView) view.findViewById(R.id.cd_timee);
                holder.COL5 = (TextView) view.findViewById(R.id.cd_UNamee);
                holder.COL6 = (TextView) view.findViewById(R.id.cd_UNicc);
                holder.COL7 = (TextView) view.findViewById(R.id.cd_Ucont);
                holder.COL8 = (TextView) view.findViewById(R.id.cd_Ucadd);
                holder.COL9 = (TextView) view.findViewById(R.id.cd_Upricee);
                holder.imageView1 = (ImageView) view.findViewById(R.id.cd_imagee);
                holder.button1 = (Button) view.findViewById(R.id.edit);
                holder.button2 = (Button) view.findViewById(R.id.delete);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText(user.get(position).getId());
            holder.COL2.setText(user.get(position).getDoctorName());
            holder.COL3.setText(user.get(position).getSpecial());
            holder.COL4.setText(user.get(position).getTime());
            holder.COL5.setText(user.get(position).getUserName());
            holder.COL6.setText(user.get(position).getUserNIC());
            holder.COL7.setText(user.get(position).getUserContact());
            holder.COL8.setText(user.get(position).getUserAddress());
            holder.COL9.setText(user.get(position).getPrice());
            Picasso.get().load(user.get(position).getImage()).into(holder.imageView1);
            System.out.println(holder);

            idd = user.get(position).getId();

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final String idd = user.get(position).getId();
                                    FirebaseDatabase.getInstance().getReference("ChannelRequests").child(idd).removeValue();
                                    //remove function not written
                                    Toast.makeText(myContext, " Channeling details deleted successfully", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.custom_update_requestdetils, null);
                    dialogBuilder.setView(view1);

                    final TextView textView1 = (TextView) view1.findViewById(R.id.updateId);
                    final TextView textView2 = (TextView) view1.findViewById(R.id.updateName);
                    final TextView textView3 = (TextView) view1.findViewById(R.id.updateSpeacial);
                    final TextView textView4 = (TextView) view1.findViewById(R.id.updateTime);
                    final EditText editText1 = (EditText) view1.findViewById(R.id.updateUname);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.updateUnic);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.updateUcontact);
                    final EditText editText4 = (EditText) view1.findViewById(R.id.updateUaddress);
                    final ImageView imageView1 = (ImageView) view1.findViewById(R.id.updateimage);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.updatebtn);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ChannelRequests").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = (String) snapshot.child("id").getValue();
                            String name = (String) snapshot.child("doctorName").getValue();
                            String speacial = (String) snapshot.child("special").getValue();
                            String time = (String) snapshot.child("time").getValue();
                            String image = (String) snapshot.child("image").getValue();
                            String username = (String) snapshot.child("userName").getValue();
                            String userNic = (String) snapshot.child("userNIC").getValue();
                            String userContact = (String) snapshot.child("userContact").getValue();
                            String userAddress = (String) snapshot.child("userAddress").getValue();

                            textView1.setText(id);
                            textView2.setText(name);
                            textView3.setText(speacial);
                            textView4.setText(time);
                            editText1.setText(username);
                            editText2.setText(userNic);
                            editText3.setText(userContact);
                            editText4.setText(userAddress);
                            Picasso.get().load(image).into(imageView1);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = editText1.getText().toString();
                            String nic = editText2.getText().toString();
                            String contact = editText3.getText().toString();
                            String address = editText4.getText().toString();


                            if (name.equals("")) {
                                editText1.setError("Name is required");
                            }else if (nic.isEmpty()) {
                                editText2.setError("NIC is required");
                            }else if (contact.isEmpty()) {
                                editText2.setError("Contact Number is required");
                            }else if (address.isEmpty()) {
                                editText2.setError("Address is required");
                            } else {

                                HashMap map = new HashMap();
                                map.put("userName", name);
                                map.put("userNIC", nic);
                                map.put("userContact", contact);
                                map.put("userAddress", address);
                                reference.updateChildren(map);

                                Toast.makeText(ChannelRequests.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                                alertDialog.dismiss();
                            }
                        }
                    });
                }
            });

            return view;

        }
    }
}