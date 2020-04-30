package com.womens.womensassociation.ui.beneficiary_services;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.womens.womensassociation.R;
import com.womens.womensassociation.models.AboutUsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
public class Beneficiaryservices extends Fragment {
    View view;
    ImageView imageView;
    TextView textView;
    ProgressDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_beneficiaryservices, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intialview();
        intialdialod();
        getdata();
    }

    private void intialview() {
        imageView = view.findViewById(R.id.image__beneficiary);
        textView = view.findViewById(R.id.text_beneficiary);

    }

    void getdata() {
        FirebaseDatabase.getInstance().getReference().child("pageone").child("beneficiary").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dialog.show();
                String des = dataSnapshot.child("describe").getValue(String.class);
                String image_urlll = dataSnapshot.child("image_url").getValue(String.class);

                AboutUsModel aboutUsModel = new AboutUsModel(image_urlll, des, "", "");
                if (aboutUsModel != null) {
                    textView.setText(aboutUsModel.getDescribe());
                    String image_urL = aboutUsModel.getImage_url();
                    if(image_urL!=null){
                        Picasso.get().load(image_urL).into(imageView);
                    }
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
            }
        });
    }

    private void intialdialod() {
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("uploading..");
        dialog.setMessage("plese wait...");
        dialog.setCancelable(false);
    }

}
