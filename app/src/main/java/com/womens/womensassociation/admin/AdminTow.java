package com.womens.womensassociation.admin;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.womens.womensassociation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class AdminTow extends Fragment {
    View view;
    Context context;
    Button galleryone,gllerytow,logout,abouus_button,Beneficiary_Button,Humanitarian_Button,widows_Button,comm_Button,Donate_update,see_all_don;
    EditText youtubelink;
    ProgressDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       context=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_admin_tow, container, false);;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        galleryone=view.findViewById(R.id.galleryone);
        gllerytow=view.findViewById(R.id.gallerytow);
        youtubelink=view.findViewById(R.id.youtubelinke3);
        logout=view.findViewById(R.id.logout);
        abouus_button=view.findViewById(R.id.aboutusupdate);
        Beneficiary_Button=view.findViewById(R.id.Beneficiaryservicesupdate);
        Humanitarian_Button=view.findViewById(R.id.humanitarianaidsupdate);
        widows_Button=view.findViewById(R.id.widowsupdate);
        comm_Button=view.findViewById(R.id.comm_update);
        Donate_update=view.findViewById(R.id.donate_update);
        see_all_don=view.findViewById(R.id.seealldonarss);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        abouus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateaboutus();
            }
        });

        Beneficiary_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replacrfrg(new beneficiary_update());
            }
        });

        Humanitarian_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replacrfrg(new humanitarian_update());
            }
        });

        widows_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replacrfrg(new widows_update());
            }
        });

        comm_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replacrfrg(new CommunityEngagement_update());
            }
        });

        Donate_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replacrfrg(new Donate_update());
            }
        });

        see_all_don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replacrfrg(new SeeAllDonars());
            }
        });
        intialdialod();
        youtubenlinke();
    }

    void youtubenlinke(){
        String youtubelinke=youtubelink.getText().toString();
        if(youtubelinke.isEmpty()){
            onpageslect();
            return;
        }
        if(!youtubelinke.isEmpty()) {
            dialog.show();
            FirebaseDatabase.getInstance().getReference().child("pageone").child("youtube")
                    .setValue(youtubelinke).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                    Toast.makeText(getContext(), "updated youtube", Toast.LENGTH_SHORT).show();
                   dialog.dismiss();
                    onpageslect();}
                    else{
                        dialog.dismiss();
                        Toast.makeText(getContext(),  task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
    void onpageslect(){
        galleryone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TitleDisPlayRecycler fragment2=new TitleDisPlayRecycler();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view,fragment2);
                Bundle args = new Bundle();
                args.putInt("aboutus_update", 1);

                fragment2.setArguments(args);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

        gllerytow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TitleDisPlayRecycler fragment2=new TitleDisPlayRecycler();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view,fragment2);
                Bundle args = new Bundle();
                args.putInt("aboutus_update", 2);

                fragment2.setArguments(args);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
    private void intialdialod() {
        dialog=new ProgressDialog(getContext());
        dialog.setTitle("uploading..");
        dialog.setMessage("plese wait...");
        dialog.setCancelable(false);
    }
    void logout(){
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.signOut();
        admin fragment2=new admin();
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view,fragment2);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }
    void updateaboutus(){
        aboutus_update fragment2=new aboutus_update();
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view,fragment2);
        fragmentTransaction.addToBackStack("aboutus");
        fragmentTransaction.commit();
    }
    void replacrfrg(Fragment fragment){
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
