package com.womens.womensassociation.admin;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.womens.womensassociation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddUpdateDlete extends Fragment {
    View view;
    String title,galleryoneortow;
    int check;
    DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference();
    ProgressDialog dialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          title=getArguments().getString("title1","");
          check=getArguments().getInt("gellery2",0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view=inflater.inflate(R.layout.fragment_add_update_dlete, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intialdialod();
        Button add=view.findViewById(R.id.add);
        Button update=view.findViewById(R.id.update);
        Button delete=view.findViewById(R.id.delete);
        Button senndtopastproject=view.findViewById(R.id.send_to_past_project);
         if (check == 2) {

             senndtopastproject.setVisibility(View.GONE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PageTowAdmin fragment2=new PageTowAdmin();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view,fragment2);
                Bundle args = new Bundle();
                args.putInt("gellery2", check);
                args.putInt("requirorno", 2);
                args.putString("title2", title);

                fragment2.setArguments(args);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PageTowAdmin fragment2=new PageTowAdmin();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view,fragment2);
                Bundle args = new Bundle();
                args.putInt("gellery2", check);
                args.putInt("requirorno", 3);
                args.putString("title2", title);

                fragment2.setArguments(args);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteFragment fragment2=new DeleteFragment();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view,fragment2);
                Bundle args = new Bundle();
                args.putInt("gellery3", check);
                args.putInt("requirorno3", 5);
                args.putString("title3", title);

                fragment2.setArguments(args);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        senndtopastproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getdata2();
            }
        });
    }


    private void intialdialod() {
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("uploading..");
        dialog.setMessage("plese wait...");
        dialog.setCancelable(false);
    }

    void getdata2() {
        if (check == 1) {
            galleryoneortow = "galleryone";
        } else if (check == 2) {
            galleryoneortow = "gallerytow";
            return;
        }
        FirebaseDatabase.getInstance().getReference().child("pageone")
                .child("galleryone").addListenerForSingleValueEvent(new ValueEventListener() {
            int a = -1;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dialog.show();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String titlees = snapshot.child("title").getValue(String.class);
                    if (title == titlees) {
                        databaseReference2 = snapshot.getRef();
                        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                FirebaseDatabase.getInstance().getReference().child("pageone")
                                        .child("gallerytow").push().setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(getContext(), "succsful", Toast.LENGTH_SHORT).show();
                                            databaseReference2.removeValue();
                                        }
                                        else {
                                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        dialog.dismiss();

                    }
                }
                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }


        });
    }
}
