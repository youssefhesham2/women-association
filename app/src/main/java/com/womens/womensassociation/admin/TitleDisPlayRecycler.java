package com.womens.womensassociation.admin;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.womens.womensassociation.R;
import com.womens.womensassociation.models.model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TitleDisPlayRecycler extends Fragment {
    View view;
    RecyclerView recyclerView;
   public static List<model> models;
   public  static int possintionn;
    int check;
    ProgressDialog dialog;
    FloatingActionButton floatingActionButton;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        check=getArguments().getInt("aboutus_update",0);
        return view=inflater.inflate(R.layout.fragment_title_dis_play_recycler, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intialdialod();
        intialrecyclerview();
        intialflotting();
        getdata();

    }

    private void intialflotting() {
        floatingActionButton=view.findViewById(R.id.flotting);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PageTowAdmin fragment2=new PageTowAdmin();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view,fragment2);
                Bundle args = new Bundle();
                args.putInt("gellery2", check);
                args.putInt("requirorno", 4);

                fragment2.setArguments(args);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void intialdialod() {
        dialog=new ProgressDialog(getContext());
        dialog.setTitle("Loading..");
        dialog.setMessage("plese wait...");
        dialog.setCancelable(false);
    }
    private void getdata() {
        dialog.show();
        models=new ArrayList<>();
        String galleryoneortow="";
        if(check==1){
            galleryoneortow="galleryone";
        }
        else if(check==2){
            galleryoneortow="gallerytow";
        }
        databaseReference.child("pageone").child(galleryoneortow).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    if (snapshot.hasChildren() == true) {
                        model modell = snapshot.getValue(model.class);

                        assert modell != null;
                        models.add(modell);

                    }
                }
                recycler recycleradapter=new recycler(models);
                recyclerView.setAdapter(recycleradapter);
                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(getContext(), databaseError.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void intialrecyclerview() {
        recyclerView=view.findViewById(R.id.recycler_view2);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

    }
    class recycler extends RecyclerView.Adapter<recycler.viewholder>{
        List<model> models;

        public recycler(List<model> models) {
            this.models = models;
        }

        @NonNull
        @Override
        public recycler.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(getContext()).inflate(R.layout.adminrecycleritem,parent,false);
            return new recycler.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final recycler.viewholder holder, final int position) {
            model modell=models.get(position);
            holder.title.setText(modell.getTitle());
            Picasso.get().load(modell.getImage_url()).into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    possintionn=position;
                    AddUpdateDlete fragment2=new AddUpdateDlete();
                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.view,fragment2);
                    Bundle args = new Bundle();
                    args.putInt("gellery2", check);
                    args.putString("title1",holder.title.getText().toString());

                    fragment2.setArguments(args);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return models.size();
        }

        class viewholder extends RecyclerView.ViewHolder{
            ImageView imageView;
            TextView title;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.image4);
                title=itemView.findViewById(R.id.title4);
            }
        }
    }

}
