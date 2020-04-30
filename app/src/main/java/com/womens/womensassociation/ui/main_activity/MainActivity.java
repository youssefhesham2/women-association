package com.womens.womensassociation.ui.main_activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.womens.womensassociation.R;
import com.womens.womensassociation.ui.ui.main2acticity.Main2Activity;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int languagee;
    TextView Beneficiary,humanitarianaids,WindowsandOrphans,community,our_project,aboutus,languagevw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        languagee = preferences.getInt("language", 1);
        intialviews();
        LinearLayout ll=findViewById(R.id.back);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.myanimition);
        ll.startAnimation(animation);

    }

    private void intialviews() {
        Beneficiary=findViewById(R.id.beneficiary_txt);
        humanitarianaids=findViewById(R.id.humanitarianaids_txt);
        WindowsandOrphans=findViewById(R.id.WindowsandOrphans_txt);
        community=findViewById(R.id.community_txt);
        our_project=findViewById(R.id.our_project_txt);
        aboutus=findViewById(R.id.aboutus_txt);
        languagevw=findViewById(R.id.language);
        if(languagee==2){
            Beneficiary.setText("خدمات المستفيدين");
            humanitarianaids.setText("المساعدات الإنسانية");
            WindowsandOrphans.setText("الأرامل والأيتام");
            community.setText("المشاركة المجتمعية");
            our_project.setText("مشاريعنا");
            aboutus.setText("من نحن");
            languagevw.setText("en");
        }
    }

    public void paypal(View view) {
        Intent intent=new Intent(this, Main2Activity.class);
        intent.putExtra("check","paypal");
        startActivity(intent);
    }

    public void aboutus(View view) {
        Intent intent=new Intent(this, Main2Activity.class);
        intent.putExtra("check","aboutus");
        startActivity(intent);
    }

    public void ourproject(View view) {
        Intent intent=new Intent(this, Main2Activity.class);
        intent.putExtra("check","ourproject");
        startActivity(intent);
    }

    public void language(View view) {
        if(languagee==1){
            editor.putInt("language",2);
            editor.commit();
            finish();
            startActivity(getIntent());
        }
       else{
            editor.putInt("language",1);
            editor.commit();
            finish();
            startActivity(getIntent());
        }
    }

    public void beneficiary(View view) {
        Intent intent=new Intent(this, Main2Activity.class);
        intent.putExtra("check","beneficiary");
        startActivity(intent);
    }

    public void human(View view) {
        Intent intent=new Intent(this, Main2Activity.class);
        intent.putExtra("check","human");
        startActivity(intent);
    }

    public void widows(View view) {
        Intent intent=new Intent(this, Main2Activity.class);
        intent.putExtra("check","widow");
        startActivity(intent);
    }

    public void community(View view) {
        Intent intent=new Intent(this, Main2Activity.class);
        intent.putExtra("check","community");
        startActivity(intent);
    }

    public void contect(View view) {
        Intent intent=new Intent(this, Main2Activity.class);
        intent.putExtra("check","contect");
        startActivity(intent);
    }


   /* void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view, fragment);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();


    }*/

}
