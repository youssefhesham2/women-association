package com.womens.womensassociation.ui.Donate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.womens.womensassociation.R;
import com.womens.womensassociation.models.AboutUsModel;
import com.womens.womensassociation.models.DonateModel;
import com.womens.womensassociation.ui.main_activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class Donate extends Fragment {
    View view;
    Context context;
    ImageView imageView;
    TextView textView;
    ProgressDialog dialog,dialog2;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
            .clientId(com.womens.womensassociation.config.config.PAYPAL_CLIENT_ID);

    Button ButtonPayNow;
    EditText AmountField,namedonate,phonedonate,purpose;
    String amount = "";

    @Override
    public void onDestroy() {
        getActivity().startService(new Intent(getContext(), PayPalService.class));
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        //start paypal server
        Intent intent = new Intent(context, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_donate, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intialviews();
        intialdialod();
        intialdialod2();
        getdata();
    }

    private void intialviews() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        ButtonPayNow = view.findViewById(R.id.pay_now);
        AmountField = view.findViewById(R.id.amount);
        imageView = view.findViewById(R.id.image__donate);
        textView = view.findViewById(R.id.text_donate);
        namedonate = view.findViewById(R.id.namedonate);
        phonedonate = view.findViewById(R.id.phonedonate);
        purpose = view.findViewById(R.id.purpose);
        ButtonPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invilation();
            }
        });
    }

    private void paymentMethod() {
        amount = AmountField.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD"
                , "Donate for women's association", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentdetials = confirmation.toJSONObject().toString(4);
                        ButtonPayNow.setVisibility(View.GONE);
                        AmountField.setVisibility(View.GONE);
                        JSONObject jsonObject = new JSONObject(paymentdetials);
                        JSONObject response = jsonObject.getJSONObject("response");
                        String id = response.getString("id");
                        uploadtodatabase(id);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
            }

        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(context, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    void succsefuldialog(final String s) {
        final android.app.AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(R.layout.dialog);
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                android.app.AlertDialog.Builder alert2 = new AlertDialog.Builder(context);
                alert2.setCancelable(false);
                alert2.setTitle("Thank you  \uD83D\uDC9A");
                alert2.setMessage("id:" + s);
                alert2.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                alert2.show();

            }
        });

        alert.show();
    }

    void getdata() {
        FirebaseDatabase.getInstance().getReference().child("pageone").child("donate").addListenerForSingleValueEvent(new ValueEventListener() {
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
    void invilation(){
        String name=namedonate.getText().toString();
        String phone=phonedonate.getText().toString();
        String prups=purpose.getText().toString();
        String amount=AmountField.getText().toString();
        if(name.isEmpty()){
            Toast.makeText(context, "please enter name", Toast.LENGTH_SHORT).show();
            namedonate.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            Toast.makeText(context, "please enter phone number", Toast.LENGTH_SHORT).show();
            phonedonate.requestFocus();
            return;
        }

        if(prups.isEmpty()){
            Toast.makeText(context,"Please tell us what you want to donate to", Toast.LENGTH_SHORT).show();
            purpose.requestFocus();
            return;
        }

        if(amount.isEmpty()){
            Toast.makeText(context,"Please enter amount", Toast.LENGTH_SHORT).show();
            AmountField.requestFocus();
            return;
        }

        editor.putString("nameDonate",name);
        editor.putString("phone",phone);
        editor.putString("reasonofDonate",prups);
        editor.putString("amountt",amount);
        editor.commit();
        paymentMethod();
    }

    private void intialdialod2() {
        dialog2 = new ProgressDialog(getContext());
        dialog2.setTitle("uploading..");
        dialog2.setMessage("please wait...");
        dialog2.setCancelable(false);
    }

    void uploadtodatabase(final String id) {
        dialog2.show();
        String namee= preferences.getString("nameDonate",null);
        String phonee= preferences.getString("phone",null);
        String reson= preferences.getString("reasonofDonate",null);
        String amountt= preferences.getString("amountt",null);
        if(namee.isEmpty()||phonee.isEmpty()||reson.isEmpty()){
            dialog2.dismiss();
            return;
        }
        DonateModel donateModel=new DonateModel(namee,phonee,reson,amountt);
        FirebaseDatabase.getInstance().getReference().child("pageone")
                .child("seeDonate").push().setValue(donateModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog2.dismiss();
                    succsefuldialog(id);

                } else {
                    dialog2.dismiss();
                    succsefuldialog(id);
                }
            }

        });

    }

}
