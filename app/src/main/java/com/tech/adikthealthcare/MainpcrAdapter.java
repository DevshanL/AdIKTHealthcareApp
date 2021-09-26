package com.tech.adikthealthcare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainpcrAdapter extends FirebaseRecyclerAdapter<pcrModel, MainpcrAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public <FirebaseRecyclerOptions> MainpcrAdapter(@NonNull FirebaseRecyclerOptions options) {
        super((com.firebase.ui.database.FirebaseRecyclerOptions<pcrModel>) options) ;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull pcrModel model) {
        holder.name.setText((model.getName()));
        holder.nic.setText(model.getNic());
         holder.noPcr.setText(model.getNoPcr());
        holder.address.setText(model.getAddress());
        holder.mobile.setText(model.getMobile());
        holder.email.setText(model.getEmail());
        holder.pcrPrice.setText(model.getPcrPrice());

        







      holder.buttonedit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              final DialogPlus dialogPlus= DialogPlus.newDialog(holder.name.getContext())
                      .setContentHolder(new ViewHolder(R.layout.updatepcr))
                      .setExpanded(true,1700)

                      .create();



             View view1 = dialogPlus.getHolderView();

              EditText name = view1.findViewById(R.id.textpnamepcr);
              EditText nic = view1.findViewById(R.id.txtnic);
              EditText noPcr= view1.findViewById(R.id.txtnopcr);
              EditText address= view1.findViewById(R.id.txtaddress);
              EditText mobile= view1.findViewById(R.id.txtmobile);
              EditText email = view1.findViewById(R.id.txtemail2);


              Button buttonUpdate = view1.findViewById(R.id.buttonUpdate);

              name.setText(model.getName());
              nic.setText(model.getNic());
              noPcr.setText(model.getNoPcr());
              address.setText(model.getAddress());
              mobile.setText(model.getMobile());
              email.setText(model.getEmail());

               dialogPlus.show();

               buttonUpdate.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Map<String,Object> map= new HashMap<>();
                       map.put("name",name.getText().toString());
                       map.put("nic",nic.getText().toString());
                       map.put("noPcr",noPcr.getText().toString());
                       map.put("address",address.getText().toString());
                       map.put("mobile",mobile.getText().toString());
                       map.put("email",email.getText().toString());

                       FirebaseDatabase.getInstance("https://adikt-healthcare-default-rtdb.firebaseio.com/")
                               .getReference().child("patients_pcr")
                               .child(Objects.requireNonNull(getRef(position).getKey())).updateChildren(map)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {
                                       Toast.makeText(holder.name.getContext(),"Update Successfully",Toast.LENGTH_LONG).show();
                                       dialogPlus.dismiss();

                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       Toast.makeText(holder.name.getContext(),"Error Updating",Toast.LENGTH_LONG).show();
                                   }
                               });




                   }
               });



          }
      });

      holder.buttondelete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              AlertDialog.Builder builder= new AlertDialog.Builder(holder.name.getContext());
              builder.setTitle("Are you sure?");
              builder.setMessage("Delete can not be undo!");

              builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      FirebaseDatabase.getInstance("https://adikt-healthcare-default-rtdb.firebaseio.com/").getReference().child("patients_pcr")
                              .child(getRef(position).getKey()).removeValue();

                  }
              });
              builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      Toast.makeText(holder.name.getContext(),"Delete cancelled",Toast.LENGTH_LONG).show();

                  }
              });
              builder.show();


          }
      });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }



    class myViewHolder extends RecyclerView.ViewHolder{

        
        TextView name,nic,noPcr,address,mobile,email,pcrPrice;
        Button buttonedit,buttondelete;

        public myViewHolder (View itemView){
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.pnamepcr);
            nic=(TextView)itemView.findViewById(R.id.pnicpcr) ;
            noPcr=(TextView)itemView.findViewById(R.id.pnopcr);
            address=(TextView)itemView.findViewById(R.id.paddresspcr);
            mobile=(TextView)itemView.findViewById(R.id.pmobilepcr);
            email=(TextView) itemView.findViewById(R.id.pemailpcr);
            pcrPrice=(TextView) itemView.findViewById(R.id.pcrprice);
            

            buttonedit= (Button) itemView.findViewById(R.id.buttonedit);
            buttondelete=(Button)itemView.findViewById(R.id.buttondelete);
        }
    }
}
