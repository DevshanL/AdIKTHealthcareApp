package com.tech.adikthealthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainpcrActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MainpcrAdapter mainAdapter;

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pcrmain);


        recyclerView = (RecyclerView)findViewById(R.id.rvpcr);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setLayoutManager(new CustomLinearLayoutManager(this));
        FirebaseRecyclerOptions<pcrModel> options =
                new FirebaseRecyclerOptions.Builder<pcrModel>()
                        .setQuery(FirebaseDatabase.getInstance("https://adikt-healthcare-default-rtdb.firebaseio.com/")
                                .getReference().child("patients_pcr"), pcrModel.class)
                        .build();

        mainAdapter=new MainpcrAdapter(options);
        recyclerView.setAdapter(mainAdapter);

        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),pcrregister.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.layout.searchpcr,menu);
        MenuItem item= menu.findItem(R.id.searchpcr);
        SearchView searchView= (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearchpcr(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearchpcr(query);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearchpcr(String strpcr){
        FirebaseRecyclerOptions<pcrModel> options =
                new FirebaseRecyclerOptions.Builder<pcrModel>()
               .setQuery(FirebaseDatabase.getInstance("https://adikt-healthcare-default-rtdb.firebaseio.com/").getReference().child("patients_pcr").orderByChild("name").startAt(strpcr).endAt(strpcr+"~"), pcrModel.class)
               .build();

        mainAdapter= new MainpcrAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }
}