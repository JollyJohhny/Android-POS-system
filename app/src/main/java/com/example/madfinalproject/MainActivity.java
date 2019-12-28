package com.example.madfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView ;
    public static ArrayList<ProductsListtype> ListOfProducts;
    public static ArrayList<BillListtype> CartProducts;

    ProductAdapter myAdapter;
    String UserId;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("List of all products"); // for set actionbar title

        listView = findViewById(R.id.ListId);

        databaseReference = FirebaseDatabase.getInstance().getReference("Inventory");

        firebaseAuth = FirebaseAuth.getInstance();

        UserId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        CartProducts = new ArrayList<BillListtype>();

        ViewProducts();

    }


    public void ViewProducts(){
        ListOfProducts = new ArrayList<ProductsListtype>();
        GetProductsIntoList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPopup(view,position);
            }
        });
    }

    public void GetProductsIntoList(){
        ListOfProducts = new ArrayList<ProductsListtype>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("userId").getValue(String.class);
                    if(UserId.equals(id)){
                        String name = snapshot.child("name").getValue(String.class);
                        String price = snapshot.child("price").getValue(String.class);
                        String quantity = snapshot.child("quantity").getValue(String.class);
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
            Toast.makeText(MainActivity.this, "No products to display!", Toast.LENGTH_SHORT).show();

        }

    }

    public void showPopup(View v,final int i){

        final String SelectedProId = ListOfProducts.get(i).getProductId();
        PopupMenu popup= new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add:
                        String name = ListOfProducts.get(i).getName();
                        String price = ListOfProducts.get(i).getPrice();
                        String quan = ListOfProducts.get(i).getQuantity();
                        String img = ListOfProducts.get(i).getImage();
                        BillListtype p = new BillListtype(price,name,SelectedProId,img,quan);
                        CartProducts.add(p);
                        Toast.makeText(MainActivity.this, "Product added to cart!", Toast.LENGTH_SHORT).show();

                        return true;
                    case R.id.edit:
                        Intent intent2 = new Intent(getBaseContext(), UpdateProduct.class);
                        intent2.putExtra("PASSEDID", SelectedProId);
                        startActivity(intent2);
                        return true;
                    case R.id.delete:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("Confirmation");
                        builder.setMessage("Do you sure you want to delete this product?");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        databaseReference.child(SelectedProId).removeValue();

                                        Toast.makeText(MainActivity.this, "Product deleted!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                        startActivity(intent);
                                    }

                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                        return true;
                    default:
                        return false;

                }
            }
        });
        popup.inflate(R.menu.popup_menu);
        popup.show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
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
            case R.id.viewcart:{
                Intent intent2 = new Intent(getBaseContext(), ViewCart.class);
                startActivity(intent2);
                return true;
            }
            case R.id.previousBills:{
                Intent intent2 = new Intent(getBaseContext(), PreviousBills.class);
                startActivity(intent2);
                return true;
            }
            case R.id.AddProduct:{
                Intent intent2 = new Intent(getBaseContext(), AddProduct.class);
                startActivity(intent2);
                return true;
            }
            case R.id.lesser:{
                Intent intent2 = new Intent(getBaseContext(), LesserProducts.class);
                startActivity(intent2);
                return true;
            }
            case R.id.Logout:{
                FirebaseAuth.getInstance().signOut(); //End user session
                startActivity(new Intent(MainActivity.this, LoginUser.class)); //Go back to home page
                finish();
            }
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }



}
