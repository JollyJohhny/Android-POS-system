package com.example.madfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewCart extends AppCompatActivity {

    ListView listView ;
    BillAdapter myAdapter;
    String UserId;
    boolean flag = false;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        getSupportActionBar().setTitle("Your Cart"); // for set actionbar title

        listView = findViewById(R.id.ListId);

        databaseReference = FirebaseDatabase.getInstance().getReference("Inventory");

        firebaseAuth = FirebaseAuth.getInstance();

        UserId= FirebaseAuth.getInstance().getCurrentUser().getUid();

        ShowInListView();
    }


    public void ShowInListView(){
        myAdapter = new BillAdapter(this, MainActivity.CartProducts);
        listView.setAdapter(myAdapter);

        if(MainActivity.CartProducts.size() == 0 ){
            Toast.makeText(ViewCart.this, "No products to display!", Toast.LENGTH_SHORT).show();

        }

    }

    public void GenerateBill(){
        int total = 0;
        int total_products = 0;
        for (BillListtype var : MainActivity.CartProducts)
        {
            int quantity = Integer.parseInt(var.getQuantity());
            String q = String.valueOf(quantity-1);
            databaseReference.child(var.getProductId()).child("quantity").setValue(q);
            total = total + Integer.parseInt(var.Price);
            total_products++;
        }

        MainActivity.CartProducts = new ArrayList<BillListtype>();

        new AlertDialog.Builder(this)
                .setTitle("Reciept")
                .setMessage("You have purchased " + total_products + " products and your total bill is " + total + " Rupees")
                .setPositiveButton(android.R.string.ok, null)
                .show();

        AlertDialog.Builder builder = new AlertDialog.Builder(ViewCart.this);
        builder.setCancelable(true);
        builder.setTitle("Confirmation");
        builder.setMessage("You have purchased " + total_products + " products and your total bill is " + total + " Rupees");
        final int finalTotal_products = total_products;
        final int finalTotal = total;
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Here add bill to database

                        AddBillToFirebase(finalTotal, finalTotal_products);

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }

                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void AddBillToFirebase(int total_amount,int number_of_products){
        databaseReference = FirebaseDatabase.getInstance().getReference("Bills");

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String timestamp = df.format(c);

        Bill bill = new Bill(number_of_products,total_amount,timestamp,UserId);

        databaseReference.push().setValue(bill);

        Toast.makeText(ViewCart.this, "Bill saved!", Toast.LENGTH_SHORT).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu2, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;

        }
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        switch (item.getItemId()) {
            case R.id.Bill:{
                GenerateBill();
                return true;
            }
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }



}
