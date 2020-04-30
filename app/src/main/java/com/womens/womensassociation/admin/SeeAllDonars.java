package com.womens.womensassociation.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.womens.womensassociation.R;
import com.womens.womensassociation.models.DonateModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SeeAllDonars extends Fragment {
    Context context;
    View view;
    RecyclerView recyclerView;
    ProgressDialog dialog;
    List<DonateModel> donateModels;
    donateadapter donateadapterr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_see_all_donars, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        donateModels = new ArrayList<>();
        intialrecyclerview();
        intialdialod2();
        getdata();
    }


    private void intialrecyclerview() {
        recyclerView = view.findViewById(R.id.recycler_view_for_dont);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);

    }

    class donateadapter extends RecyclerView.Adapter<donateadapter.viewholder> {
        List<DonateModel> models;

        public donateadapter(List<DonateModel> models) {
            this.models = models;
        }

        @NonNull
        @Override

        public donateadapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.seealldonars_item, parent, false);
            return new donateadapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final donateadapter.viewholder holder, final int position) {
            DonateModel modell = models.get(position);
            String name = modell.getName();
            String phonee = modell.getPhone();
            String ress = modell.getReason();
            String amount=modell.getAmount();
            holder.name.setText(name);
            holder.phone.setText(phonee);
            holder.reason.setText(ress);
            holder.amount.setText(amount+"$");
        }

        @Override
        public int getItemCount() {
            return models.size();
        }

        class viewholder extends RecyclerView.ViewHolder {
            TextView name, phone, reason,amount;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.nameodon);
                phone = itemView.findViewById(R.id.numberphodon);
                reason = itemView.findViewById(R.id.resonof);
                amount = itemView.findViewById(R.id.amountofdonaeet);

            }

        }
    }

    void getdata() {
        dialog.show();
        FirebaseDatabase.getInstance().getReference().child("pageone").child("seeDonate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                donateModels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DonateModel modell = snapshot.getValue(DonateModel.class);
                    if (modell != null) {
                        donateModels.add(modell);
                    }
                }
                donateadapterr=new donateadapter(donateModels);
                recyclerView.setAdapter(donateadapterr);
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    private void intialdialod2() {
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("loading..");
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
    }

}