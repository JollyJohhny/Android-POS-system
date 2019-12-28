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

public class BillShowAdapter extends ArrayAdapter {

    int Layout_Id;
    Context mContext;
    ArrayList<Bill> ListOfProducts;

    public BillShowAdapter(Context context, ArrayList<Bill> list) {
        super(context, 0);
        mContext = context;
        Layout_Id = R.layout.billshow_layout;
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


        TextView tp = (TextView) view.findViewById(R.id.txtProducts);
        TextView ta = (TextView) view.findViewById(R.id.txtAmount);
        TextView ts = (TextView) view.findViewById(R.id.txtTime);


        tp.setText("Total Products: "+ListOfProducts.get(position).getTotal_Products());
        ta.setText("Total Amount: "+ListOfProducts.get(position).getTotal_Amount());
        ts.setText("Timestamp: "+ListOfProducts.get(position).getTimeStamp());




        return view;

    }
}
