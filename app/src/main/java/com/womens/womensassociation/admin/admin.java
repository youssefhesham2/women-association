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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class admin extends Fragment {
    Context context;
    View view;
    EditText email,password;
    Button login;
    ProgressDialog dialog;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    context=getContext();
    FirebaseAuth Auth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=Auth.getCurrentUser();
      if (firebaseUser!=null){
          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
          fragmentTransaction.replace(R.id.view, new AdminTow());
          fragmentTransaction.disallowAddToBackStack();
          fragmentTransaction.commit();
      }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_admin, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intialdialod();
        intialviews();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String emaill=email.getText().toString();
        String passwordd=password.getText().toString();
        if(emaill.isEmpty()){
            Toast.makeText(context, "please enter mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if(passwordd.isEmpty()){
            Toast.makeText(context, "please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
       /* if(!emaill.equals("womensassociation")){
            Toast.makeText(context, "wrong email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!passwordd.equals("womensassociation123")){
            Toast.makeText(context, "wrong password!", Toast.LENGTH_SHORT).show();
            return;
        }*/
       dialog.show();
       mAuth.signInWithEmailAndPassword(emaill,passwordd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()) {
                  dialog.dismiss();
                  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                  fragmentTransaction.replace(R.id.view, new AdminTow());
                  fragmentTransaction.disallowAddToBackStack();
                  fragmentTransaction.commit();
              }
              else{
                  dialog.dismiss();
                  Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
              }
           }
       });

    }
    private void intialdialod() {
        dialog=new ProgressDialog(getContext());
        dialog.setTitle("Login..");
        dialog.setMessage("plese wait...");
        dialog.setCancelable(false);
    }

    private void intialviews() {
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        login=view.findViewById(R.id.login);
    }
}
