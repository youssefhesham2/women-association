package com.womens.womensassociation.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.womens.womensassociation.R;
import com.womens.womensassociation.models.AboutUsModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Donate_update extends Fragment {
    ImageView imageView;
    EditText describe;
    Button uplode;
    View view;
    Uri image_uri;
    StorageReference storageReference;
    String image_urL;
    Task<Void> databaseReference;
    ProgressDialog dialog;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_donate_update, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intialdialod();
        intialviews();
        getdata();
        onslectImage();

    }

    void intialviews() {
        imageView = view.findViewById(R.id.uplode_image_don);
        describe = view.findViewById(R.id.describe_don);
        uplode = view.findViewById(R.id.uplod_don);

        uplode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uplodebutton();
            }
        });
    }

    private void onslectImage() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 505);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 505 && resultCode == getActivity().RESULT_OK && data != null) {

            image_uri = data.getData();
            Picasso.get().load(image_uri).into(imageView);
        }
    }

    void uplodebutton() {
        String describee = describe.getText().toString();

        uplode(describee);
    }

    void uplode(String describee) {

        uploadphototostorage(image_uri, describee);
    }

    private void uploadphototostorage(Uri image_uri, final String describee) {
        if (image_uri != null) {
            dialog.show();
            storageReference = FirebaseStorage.getInstance().getReference().child("galleryone/" + image_uri.getLastPathSegment());
            UploadTask uploadTask = storageReference.putFile(image_uri);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        image_urL = task.getResult().toString();
                        uploadtodatabase(image_urL, describee);

                    } else {
                        dialog.dismiss();
                    }
                }
            });
        } else {
            uploadtodatabase(image_urL, describee);
        }
    }

    void uploadtodatabase(String image_url, final String describee) {
        final long[] sizeofchilderen = new long[1];
        AboutUsModel aboutUsModel = new AboutUsModel(image_url, describee, "", "");
        FirebaseDatabase.getInstance().getReference().child("pageone")
                .child("donate").setValue(aboutUsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(context, "successful update", Toast.LENGTH_SHORT).show();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void getdata() {
        FirebaseDatabase.getInstance().getReference().child("pageone").child("donate").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dialog.setTitle("loading...");
                dialog.show();

                String des = dataSnapshot.child("describe").getValue(String.class);
                String image_urlll = dataSnapshot.child("image_url").getValue(String.class);

                AboutUsModel aboutUsModel = new AboutUsModel(image_urlll, des, "", "");
                if (aboutUsModel != null) {
                    describe.setText(aboutUsModel.getDescribe());
                    image_urL = aboutUsModel.getImage_url();
                    Picasso.get().load(image_urL).into(imageView);
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
