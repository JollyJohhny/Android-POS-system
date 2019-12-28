package com.example.madfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PreviousBills extends AppCompatActivity {

    ListView listView ;
    public static ArrayList<Bill> ListOfBills;

    BillShowAdapter myAdapter;
    String UserId;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_bills);

        getSupportActionBar().setTitle("Previous Bills"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar


        listView = findViewById(R.id.ListId);

        databaseReference = FirebaseDatabase.getInstance().getReference("Bills");

        firebaseAuth = FirebaseAuth.getInstance();

        UserId= FirebaseAuth.getInstance().getCurrentUser().getUid();

        ViewProducts();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void ViewProducts(){
        ListOfBills = new ArrayList<Bill>();
        GetProductsIntoList();

    }

    public void GetProductsIntoList(){
        ListOfBills = new ArrayList<Bill>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("userId").getValue(String.class);
                    if(UserId.equals(id)){
                        int TotalProducts = snapshot.child("total_Products").getValue(Integer.class);
                        int TotalAmount = snapshot.child("total_Amount").getValue(Integer.class);
                        String Time = snapshot.child("timeStamp").getValue(String.class);


                        Bill bill = new Bill(TotalProducts,TotalAmount,Time,id);
                        ListOfBills.add(bill);


                    }
                }
                ShowInListView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }

        });
    }

    public void ShowInListView(){
        myAdapter = new BillShowAdapter(this, ListOfBills);
        listView.setAdapter(myAdapter);

        if(ListOfBills.size() == 0 ){
            Toast.makeText(PreviousBills.this, "No bills to display!", Toast.LENGTH_SHORT).show();

        }

    }


}
