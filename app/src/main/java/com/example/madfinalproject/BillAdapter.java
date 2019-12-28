package com.example.madfinalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BillAdapter extends ArrayAdapter {

    int Layout_Id;
    Context mContext;
    ArrayList<BillListtype> ListOfProducts;

    public BillAdapter(Context context, ArrayList<BillListtype> list) {
        super(context, 0);
        mContext = context;
        Layout_Id = R.layout.bill_layout;
        ListOfProducts = list;
    }


    @Override
    public int getCount() {
        return ListOfProducts.size();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) this.getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(Layout_Id, parent, false);


        TextView price = (TextView) view.findViewById(R.id.txtprice);
        TextView quantity = (TextView) view.findViewById(R.id.txtquantity);
        TextView name = (TextView) view.findViewById(R.id.txtname);
        ImageView image= (ImageView) view.findViewById(R.id.imageView);

        name.setText("Name: "+ListOfProducts.get(position).getName());
        price.setText("Price: " +ListOfProducts.get(position).getPrice());
//        quantity.setText("Quantity: "+ListOfProducts.get(position).getQuantity());

        Picasso.get().load(ListOfProducts.get(position).getImage()).into(image);




        return view;

    }
}
