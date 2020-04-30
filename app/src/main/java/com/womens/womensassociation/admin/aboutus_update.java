package com.womens.womensassociation.admin;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.womens.womensassociation.R;
import com.womens.womensassociation.models.AboutUsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class aboutus_update extends Fragment {
    EditText describe, longitude_field, latitude_Field;
    Button uplode;
    View view;
    ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.update_about_us, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intialdialod();
        intialviews();
        getdata();
    }

    void intialviews() {
        describe = view.findViewById(R.id.describe_about_us);
        uplode = view.findViewById(R.id.uplodaboutus);
        longitude_field = view.findViewById(R.id.longitude_field);
        latitude_Field = view.findViewById(R.id.latitude_Field);

        uplode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uplodebutton();
            }
        });
    }

    void uplodebutton() {
        String describee = describe.getText().toString();
        String longitude = longitude_field.getText().toString();
        String latitude = latitude_Field.getText().toString();
        uplode(describee, longitude, latitude);
    }

    void uplode(String describee, String longitude, String latitude) {

        uploadtodatabase(describee, longitude, latitude);
    }


    void uploadtodatabase(final String describee, final String longitude, final String latitude) {

        final long[] sizeofchilderen = new long[1];
        AboutUsModel aboutUsModel = new AboutUsModel("", describee, longitude, latitude);
        FirebaseDatabase.getInstance().getReference().child("pageone")
                .child("about us").setValue(aboutUsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "successful update", Toast.LENGTH_SHORT).show();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack("aboutus", 1);
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void getdata() {
        FirebaseDatabase.getInstance().getReference().child("pageone").child("about us").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dialog.setTitle("loading...");
                dialog.show();

                String des = dataSnapshot.child("describe").getValue(String.class);
                String image_urlll = dataSnapshot.child("image_url").getValue(String.class);
                String lat = dataSnapshot.child("latitude").getValue(String.class);
                String longt = dataSnapshot.child("longitude").getValue(String.class);

                AboutUsModel aboutUsModel = new AboutUsModel(image_urlll, des, longt, lat);
                if (aboutUsModel != null) {
                    describe.setText(aboutUsModel.getDescribe());
                    latitude_Field.setText(aboutUsModel.getLatitude());
                    longitude_field.setText(aboutUsModel.getLongitude());
                    dialog.setTitle("uploading...");
                    dialog.dismiss();
                }
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
