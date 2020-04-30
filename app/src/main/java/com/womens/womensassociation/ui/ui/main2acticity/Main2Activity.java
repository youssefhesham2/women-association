package com.womens.womensassociation.ui.ui.main2acticity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.womens.womensassociation.R;
import com.womens.womensassociation.admin.admin;
import com.womens.womensassociation.ui.Donate.Donate;
import com.womens.womensassociation.ui.about_us_fragment.AboutUs;
import com.womens.womensassociation.ui.beneficiary_services.Beneficiaryservices;
import com.womens.womensassociation.ui.communityengagement.CommunityEngagement;
import com.womens.womensassociation.ui.homepage.Homepage;
import com.womens.womensassociation.ui.humanitarianaids;
import com.womens.womensassociation.ui.main_activity.MainActivity;
import com.womens.womensassociation.ui.widowsandorphans.WidowsAndOrphans;
import com.google.android.material.navigation.NavigationView;

public class Main2Activity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    DrawerLayout drawerLayout;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    String check;
    int previousItem = 0;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int languagee = preferences.getInt("language", 1);
        editor = preferences.edit();
        drawerLayout = findViewById(R.id.ddddd);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        final NavigationView navView = findViewById(R.id.nav_view);
        if(languagee==2){
            navView.getMenu().findItem(R.id.Beneficiary).setTitle("خدمات المستفيدين");
            navView.getMenu().findItem(R.id.Humanitarian).setTitle("المساعدات الإنسانية");
            navView.getMenu().findItem(R.id.Widows).setTitle("الأرامل والأيتام");
            navView.getMenu().findItem(R.id.Community).setTitle("المشاركة المجتمعية");
            navView.getMenu().findItem(R.id.payment).setTitle("تبرع");
            navView.getMenu().findItem(R.id.home).setTitle("الصفحه الرئسيه");
            navView.getMenu().findItem(R.id.our_project).setTitle("مشاريعنا");
            navView.getMenu().findItem(R.id.aboutus).setTitle("من نحن");
            navView.getMenu().findItem(R.id.admin).setTitle("المشرف");
            navView.getMenu().findItem(R.id.Communicate).setTitle("التواصل");
            navView.getMenu().findItem(R.id.contectus).setTitle("تواصل معنا");
            navView.getMenu().findItem(R.id.facebook).setTitle("facebook");
        }


        check = getIntent().getStringExtra("check");
        switch (check) {
            case "ourproject":
                replacefragment(new Homepage());
                navView.getMenu().getItem(1).setChecked(true);
                previousItem = navView.getMenu().getItem(1).getItemId();
                break;

            case "beneficiary":
                replacefragment(new Beneficiaryservices());
                navView.getMenu().getItem(2).setChecked(true);
                previousItem = navView.getMenu().getItem(2).getItemId();
                break;

            case "human":
                replacefragment(new humanitarianaids());
                navView.getMenu().getItem(3).setChecked(true);
                previousItem = navView.getMenu().getItem(3).getItemId();
                break;

            case "widow":
                replacefragment(new WidowsAndOrphans());
                navView.getMenu().getItem(4).setChecked(true);
                previousItem = navView.getMenu().getItem(4).getItemId();
                break;

            case "community":
                replacefragment(new CommunityEngagement());
                navView.getMenu().getItem(5).setChecked(true);
                previousItem = navView.getMenu().getItem(5).getItemId();
                break;

            case "paypal":
                replacefragment(new Donate());
                navView.getMenu().getItem(6).setChecked(true);
                previousItem = navView.getMenu().getItem(6).getItemId();
                break;
            case "aboutus":
                replacefragment(new AboutUs());
                navView.getMenu().getItem(7).setChecked(true);
                previousItem = navView.getMenu().getItem(7).getItemId();
                break;
            case "contect":
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","women.leb@gmail.com", null));
              //  emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
               // emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                navView.getMenu().findItem(R.id.contectus).setChecked(true);
                previousItem = navView.getMenu().findItem(R.id.contectus).getItemId();
                break;
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemid = item.getItemId();
                if (previousItem != 0) {
                    navView.getMenu().findItem(previousItem).setChecked(false);
                }
                previousItem = itemid;
                item.setChecked(true);
                toolbar.setTitle(item.getTitle());
                switch (itemid) {
                    case R.id.home:
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.our_project:
                        replacefragment(new Homepage());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Beneficiary:
                        replacefragment(new Beneficiaryservices());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Humanitarian:
                        replacefragment(new humanitarianaids());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Widows:
                        replacefragment(new WidowsAndOrphans());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Community:
                        replacefragment(new CommunityEngagement());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.payment:
                        replacefragment(new Donate());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.aboutus:
                        replacefragment(new AboutUs());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.admin:
                        replacefragment(new admin());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.contectus:
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto","women.leb@gmail.com", null));
                       // emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                        //emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.facebook:
                        String YourPageURL = "https://www.facebook.com/Womens-Association-In-Al-Sfireh-2098847260125763/?__tn__=%2Cd%2CP-R&eid=ARBnaBVfLfuHANt5uBr97pEp-k5_wzZXshqNLQlQQtWpY9IlWh95eNyZCqaxmJMJoH5bg5R08eqyS-Vs";
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));
                        startActivity(browserIntent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return false;
            }
        });
      /*  i=i+1;
       String check=getIntent().getStringExtra("check");
       if(i==1) {
           Toast.makeText(getApplicationContext(), "lll", Toast.LENGTH_SHORT).show();

           switch (check) {
               case "paypal":
                   navController = Navigation.findNavController(this, R.id.nav_host_fragment);
              //     NavGraph navGraph = navController.getGraph();
                //   navGraph.setStartDestination(R.id.payment);
                 //  navController.setGraph(navGraph);
                   break;
           }
       }
        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);
         appBarConfiguration =
                new AppBarConfiguration.Builder(navView.getMenu())
                       .setDrawerLayout(drawerLayout)
                        .build();
        appBarConfiguration.getFallbackOnNavigateUpListener();
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);*/

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    void replacefragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view, fragment);
        fragmentTransaction.commit();


    }
}
