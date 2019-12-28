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

public class LesserProducts extends AppCompatActivity {

    ListView listView ;
    public static ArrayList<ProductsListtype> ListOfProducts;

    ProductAdapter myAdapter;
    String UserId;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesser_products);

        getSupportActionBar().setTitle("Lesser Products"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        listView = findViewById(R.id.ListId);

        databaseReference = FirebaseDatabase.getInstance().getReference("Inventory");

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
        ListOfProducts = new ArrayList<ProductsListtype>();
        GetProductsIntoList();

    }

    public void GetProductsIntoList(){
        ListOfProducts = new ArrayList<ProductsListtype>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("userId").getValue(String.class);
                    String quantity = snapshot.child("quantity").getValue(String.class);

                    if(UserId.equals(id) && Integer.parseInt(quantity) < 10){

                        String name = snapshot.child("name").getValue(String.class);
                        String price = snapshot.child("price").getValue(String.class);
                        String img = snapshot.child("image").getValue(String.class);
                        String proId = snapshot.getKey();

                        ProductsListtype product = new ProductsListtype(price,quantity,name,proId,img,id);
                        ListOfProducts.add(product);


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
        myAdapter = new ProductAdapter(this, ListOfProducts);
        listView.setAdapter(myAdapter);

        if(ListOfProducts.size() == 0 ){
            Toast.makeText(LesserProducts.this, "No products to display!", Toast.LENGTH_SHORT).show();

        }

    }

}
