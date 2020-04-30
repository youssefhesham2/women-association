package com.womens.womensassociation.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.womens.womensassociation.R;
import com.womens.womensassociation.models.model;
import com.womens.womensassociation.ui.homepage.Homepage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class PageTowAdmin extends Fragment {

    View view;
    ImageView imageView;
    EditText title, details, youtube;
    Button uplode;
    Uri image_uri;
    StorageReference storageReference;
    Task<Void> databaseReference;
    int check, requirorno;
    String galleryoneortow = "";
    DatabaseReference databaseReference2;
    ProgressDialog dialog;
    String titleee;
    String image_urll=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        check = getArguments().getInt("gellery2", 0);
        requirorno = getArguments().getInt("requirorno", 0);
        titleee = getArguments().getString("title2", "");

        return view = inflater.inflate(R.layout.fragment_page_tow_admin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intialviews();
        intialdialod();
    }

    private void intialdialod() {
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("uploading..");
        dialog.setMessage("plese wait...");
        dialog.setCancelable(false);
    }

    private void intialviews() {
        imageView = view.findViewById(R.id.uplode_image2);
        title = view.findViewById(R.id.titleinadmain2);
        uplode = view.findViewById(R.id.uplodd2);
        details = view.findViewById(R.id.detials);
        youtube = view.findViewById(R.id.youtubelink);
        if (requirorno == 2) {
            title.setVisibility(View.GONE);
            youtube.setVisibility(View.GONE);
            details.setVisibility(View.GONE);
        }
        if (requirorno == 3) {
            uplode.setText("update");
        }

        String youtubelinkee = null, detialss = null, titllee = null;
        if (requirorno == 3) {

            if (check == 1) {
                detialss = Homepage.homePageTowModels.get(TitleDisPlayRecycler.possintionn).getDetials();
                youtubelinkee = Homepage.homePageTowModels.get(TitleDisPlayRecycler.possintionn).getYoutube2();
                 image_urll= Homepage.models.get(TitleDisPlayRecycler.possintionn).getImage_url();


            } else if (check == 2) {
                detialss = Homepage.homePageTowModels2.get(TitleDisPlayRecycler.possintionn).getDetials();
                youtubelinkee = Homepage.homePageTowModels2.get(TitleDisPlayRecycler.possintionn).getYoutube2();
                image_urll=Homepage.models2.get(TitleDisPlayRecycler.possintionn).getImage_url();
            }
            if(image_urll!=null){
                Picasso.get().load(image_urll).into(imageView);
            }
            titllee = TitleDisPlayRecycler.models.get(TitleDisPlayRecycler.possintionn).getTitle();

            title.setText(titllee);
            details.setText(detialss);
            youtube.setText(youtubelinkee);
        }
        uplode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uplodebutton();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onslectImage();
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
        String titllee = title.getText().toString();
        String youtubelinkee = youtube.getText().toString();
        String detials = details.getText().toString();

        if (image_uri == null && (requirorno == 4 || requirorno == 2)) {
            Toast.makeText(getContext(), "please select image", Toast.LENGTH_SHORT).show();
            return;
        }



       /* if (titllee.isEmpty()&&requirorno==4) {
            Toast.makeText(getContext(), "please enter describe", Toast.LENGTH_SHORT).show();
            return;
        }
        if (detials.isEmpty()&&requirorno==4) {
            Toast.makeText(getContext(), "please enter detials", Toast.LENGTH_SHORT).show();
            return;
        }
        if (youtubelinkee.isEmpty()&&requirorno==4) {
            Toast.makeText(getContext(), "please enter youtube id", Toast.LENGTH_SHORT).show();
            return;
        }*/
        uplode(titllee);
    }

    void uplode(String title) {
        uploadphototostorage(image_uri, title);
    }

    private void uploadphototostorage(Uri image_uri, final String title) {
        dialog.show();

        if (image_uri != null) {
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
                    String image_urL = task.getResult().toString();
                    uploadtodatabase(image_urL, title);
                }
            });

        } else {
            uploadtodatabase(image_urll, title);
        }
    }

    void uploadtodatabase(final String image_url, final String title) {
        if (check == 1) {
            galleryoneortow = "galleryone";
        } else if (check == 2) {
            galleryoneortow = "gallerytow";
        }
//        Toast.makeText(getContext(), galleryoneortow, Toast.LENGTH_SHORT).show();
        //model modell = new model(describe, image_url, "null");
        if (requirorno != 4) {
            FirebaseDatabase.getInstance().getReference().child("pageone")
                    .child(galleryoneortow).addListenerForSingleValueEvent(new ValueEventListener() {
                int a = 0;

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String titlees = snapshot.child("title").getValue(String.class);
                        if (titleee.equals(titlees)) {
                            a = a + 1;
                            databaseReference2 = snapshot.getRef();
                            if (image_url != null && requirorno == 2) {
                                databaseReference2.child("pagetow").child("alboum").push().setValue(image_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                        //    Toast.makeText(getContext(), "successful upload", Toast.LENGTH_SHORT).show();
//                                            FragmentManager fm = getActivity().getSupportFragmentManager();
  //                                          fm.popBackStack();
                                            return;
                                        } else {
                                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                            if (image_url != null && (requirorno == 3)) {
                                databaseReference2.child("image_url").setValue(image_url);
                            }
                            if (!title.isEmpty() || (title.isEmpty() && requirorno == 3)) {
                                Toast.makeText(getContext(), "zaaaa", Toast.LENGTH_SHORT).show();
                                databaseReference2.child("title").setValue(title);
                            }
                            if (!details.getText().toString().isEmpty() || (details.getText().toString().isEmpty() && requirorno == 3)) {
                                databaseReference2.child("pagetow").child("detials").setValue(details.getText().toString());
                            }
                            if (!youtube.getText().toString().isEmpty() || (youtube.getText().toString().isEmpty() && requirorno == 3)) {
                                databaseReference2.child("pagetow").child("youtube2").setValue(youtube.getText().toString());
                            }
                            Toast.makeText(getContext(), "successful upload", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();


                        }

                    }
                    if (a == 0) {
                        Toast.makeText(getContext(), "title not found!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (requirorno != 4) {
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.popBackStack();
                        }
                    }
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (requirorno == 4) {
            Toast.makeText(getContext(), "wwwww", Toast.LENGTH_SHORT).show();
            model modell = new model(title, image_url, "");
            FirebaseDatabase.getInstance().getReference().child("pageone").child(galleryoneortow)
                    .push().setValue(modell).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        NavigationView navView = getActivity().findViewById(R.id.nav_view);
                        navView.getMenu().getItem(0).setChecked(true);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.view, new Homepage());
                        fragmentTransaction.disallowAddToBackStack();
                        fragmentTransaction.commit();
                    } else {
                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

        }

    }

}
