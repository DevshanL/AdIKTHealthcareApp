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
import com.tech.adikthealthcare.Models.OrderedItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderedDeliveryItems extends AppCompatActivity {

    ListView listView;
    List<OrderedItems> user;
    DatabaseReference ref;
    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_delivery_items);

        listView = (ListView) findViewById(R.id.orderlist);
        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("OrdereDeliveries");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    OrderedItems orderedItems = studentDatasnap.getValue(OrderedItems.class);
                    user.add(orderedItems);

                }

                MyAdapter adapter = new MyAdapter(OrderedDeliveryItems.this, R.layout.custom_delivery_ordered_deails, (ArrayList<OrderedItems>) user);
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
    }

    class MyAdapter extends ArrayAdapter<OrderedItems> {
        LayoutInflater inflater;
        Context myContext;
        List<OrderedItems> user;


        public MyAdapter(Context context, int resource, ArrayList<OrderedItems> objects) {
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
                view = inflater.inflate(R.layout.custom_delivery_ordered_deails, null);

                holder.COL1 = (TextView) view.findViewById(R.id.item_idd);
                holder.COL2 = (TextView) view.findViewById(R.id.item_Namee);
                holder.COL3 = (TextView) view.findViewById(R.id.item_Pricce);
                holder.COL4 = (TextView) view.findViewById(R.id.item_UNamee);
                holder.COL5 = (TextView) view.findViewById(R.id.item_UNicc);
                holder.COL6 = (TextView) view.findViewById(R.id.item_Ucont);
                holder.COL7 = (TextView) view.findViewById(R.id.item_Ucadd);
                holder.COL8 = (TextView) view.findViewById(R.id.item_Uqty);
                holder.imageView1 = (ImageView) view.findViewById(R.id.item_imageee);
                holder.button1 = (Button) view.findViewById(R.id.itemedit);
                holder.button2 = (Button) view.findViewById(R.id.itemdelete);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText(user.get(position).getId());
            holder.COL2.setText(user.get(position).getName());
            holder.COL3.setText(user.get(position).getPrice());
            holder.COL4.setText(user.get(position).getUserName());
            holder.COL5.setText(user.get(position).getUserNIC());
            holder.COL6.setText(user.get(position).getUserContact());
            holder.COL7.setText(user.get(position).getUserAddress());
            holder.COL8.setText(user.get(position).getQty());
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
                                    FirebaseDatabase.getInstance().getReference("OrdereDeliveries").child(idd).removeValue();
                                    //remove function not written
                                    Toast.makeText(myContext, "Item deleted successfully", Toast.LENGTH_SHORT).show();

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
                    View view1 = inflater.inflate(R.layout.custom_update_orders, null);
                    dialogBuilder.setView(view1);

                    final TextView textView1 = (TextView) view1.findViewById(R.id.updateId);
                    final TextView textView2 = (TextView) view1.findViewById(R.id.updateName);
                    final TextView textView3 = (TextView) view1.findViewById(R.id.updateprice);
                    final EditText editText1 = (EditText) view1.findViewById(R.id.updateUname);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.updateUnic);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.updateUcontact);
                    final EditText editText4 = (EditText) view1.findViewById(R.id.updateUaddress);
                    final EditText editText5 = (EditText) view1.findViewById(R.id.updateUqty);
                    final ImageView imageView1 = (ImageView) view1.findViewById(R.id.updateimage);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.updatebtn);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("OrdereDeliveries").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = (String) snapshot.child("id").getValue();
                            String name = (String) snapshot.child("name").getValue();
                            String price = (String) snapshot.child("price").getValue();
                            String qty = (String) snapshot.child("qty").getValue();
                            String image = (String) snapshot.child("image").getValue();
                            String username = (String) snapshot.child("userName").getValue();
                            String userNic = (String) snapshot.child("userNIC").getValue();
                            String userContact = (String) snapshot.child("userContact").getValue();
                            String userAddress = (String) snapshot.child("userAddress").getValue();

                            textView1.setText(id);
                            textView2.setText(name);
                            textView3.setText(price);
                            editText1.setText(username);
                            editText2.setText(userNic);
                            editText3.setText(userContact);
                            editText4.setText(userAddress);
                            editText5.setText(qty);
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
                            String qty = editText5.getText().toString();
                            Integer qtyy = Integer.valueOf(editText5.getText().toString());
                            Integer price = Integer.valueOf(textView3.getText().toString());

                            if (name.isEmpty()) {
                                editText1.setError("Name is required");
                            }else if (nic.isEmpty()) {
                                editText2.setError("NIC is required");
                            }else if (contact.isEmpty()) {
                                editText3.setError("Contact Number is required");
                            }else if (address.isEmpty()) {
                                editText4.setError("Address is required");
                            }else if (qty.isEmpty()) {
                                editText5.setError("Quantity is required");
                            } else {

                                HashMap map = new HashMap();
                                map.put("userName", name);
                                map.put("userNIC", nic);
                                map.put("userContact", contact);
                                map.put("userAddress", address);
                                map.put("userqtyAddress", qty);

                                String finalval = String.valueOf(price*qtyy);
                                map.put("price",finalval);
                                reference.updateChildren(map);

                                Toast.makeText(OrderedDeliveryItems.this, "Update successfully", Toast.LENGTH_SHORT).show();

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