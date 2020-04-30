package com.womens.womensassociation.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.womens.womensassociation.R;
import com.womens.womensassociation.ui.homepage.Homepage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DeleteFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    int check, requirorno;
    ProgressDialog dialog;
    String title, galleryoneortow;
    recycler adaper;
    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference();
    List<String> strings = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    Context  context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        Toast.makeText(getContext(), "press long click to delete image", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        check = getArguments().getInt("gellery3", 0);
        requirorno = getArguments().getInt("requirorno3", 0);
        title = getArguments().getString("title3", "");
        return view = inflater.inflate(R.layout.fragment_delete, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intialrecyclerview();
        intialdialod();
        getdata();
    }

    private void intialrecyclerview() {
        recyclerView = view.findViewById(R.id.recycler_view3);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        floatingActionButton=view.findViewById(R.id.flotting2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code to differnt one item will delet or all
                alertdialog(1111);
            }
        });

    }

    class recycler extends RecyclerView.Adapter<recycler.viewholder> {
        List<String> models;

        public recycler(List<String> models) {
            this.models = models;
        }

        @NonNull
        @Override
        public recycler.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.delete_item, parent, false);
            return new recycler.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final recycler.viewholder holder, final int position) {
            String modell = models.get(position);
            Picasso.get().load(modell).into(holder.imageView);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    alertdialog(position);
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return models.size();
        }

        class viewholder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image5);
            }


        }
    }

    void getdata() {
        if (check == 1) {
            galleryoneortow = "galleryone";
        } else if (check == 2) {
            galleryoneortow = "gallerytow";
        }
        FirebaseDatabase.getInstance().getReference().child("pageone")
                .child(galleryoneortow).addListenerForSingleValueEvent(new ValueEventListener() {
            int a = -1;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dialog.show();


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String titlees = snapshot.child("title").getValue(String.class);
                    if (title == titlees) {
                        databaseReference2 = snapshot.getRef();
                        databaseReference2.child("pagetow").child("alboum").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                strings.clear();
                                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                    String url = snapshot1.getValue(String.class);
                                    if(url!=null)
                                    strings.add(url);
                                }
                                dialog.dismiss();
                                adaper=new recycler(strings);
                                recyclerView.setAdapter(adaper);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
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

    private void intialdialod() {
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("uploading..");
        dialog.setMessage("plese wait...");
        dialog.setCancelable(false);
    }


    void deleteoneitem(final int posstion) {
        dialog.show();
        if (check == 1) {
            galleryoneortow = "galleryone";
        } else if (check == 2) {
            galleryoneortow = "gallerytow";
        }
        FirebaseDatabase.getInstance().getReference().child("pageone")
                .child(galleryoneortow).addListenerForSingleValueEvent(new ValueEventListener() {
            int a = -1;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String titlees = snapshot.child("title").getValue(String.class);
                    if (title.equals(titlees)) {
                        databaseReference2 = snapshot.getRef();
                        databaseReference2.child("pagetow").child("alboum").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                strings.clear();
                                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                    a=a+1;
                                   if(a==posstion){
                                       snapshot1.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               if(task.isSuccessful()){
                                                   dialog.dismiss();
                                                   Toast.makeText(getContext(),"deleted",Toast.LENGTH_SHORT).show();
                                                   FragmentManager fm = getActivity().getSupportFragmentManager();
                                                   fm.popBackStack();
                                               }
                                               else{
                                                   Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                                               }
                                           }
                                       });

                                   }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                dialog.dismiss();

                                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    void deletfromdatabase() {
        if (check == 1) {
            galleryoneortow = "galleryone";
        } else if (check == 2) {
            galleryoneortow = "gallerytow";
        }
        assert galleryoneortow!=null;
        FirebaseDatabase.getInstance().getReference().child("pageone")
                .child(galleryoneortow).addListenerForSingleValueEvent(new ValueEventListener() {
            int a = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dialog.show();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String titlee = snapshot.child("title").getValue(String.class);
                    if (title.equals(titlee)) {
                        a = a + 1;
                        databaseReference2 = snapshot.getRef();
                        databaseReference2.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(), "deleted!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    NavigationView navView = getActivity().findViewById(R.id.nav_view);
                                    navView.getMenu().getItem(0).setChecked(true);
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.view, new Homepage());
                                    fragmentTransaction.disallowAddToBackStack();
                                    fragmentTransaction.commit();
                                }
                                else {
                                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        });
                    }

                }
                if (a==0) {
                    Toast.makeText(getContext(), "not found!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void alertdialog(final int possition){

        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        if(possition==1111){
            alert.setMessage("are you sure to delete all gallery! ❌");
        }
        else{
            alert.setMessage("are you sure to delete this image! ❌");
        }
        alert.setCancelable(false);
        alert.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        alert.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(possition==1111){
                    deletfromdatabase();
                }
                else
                {
                    deleteoneitem(possition);

                }
            }
        });
        alert.show();
    }
}


