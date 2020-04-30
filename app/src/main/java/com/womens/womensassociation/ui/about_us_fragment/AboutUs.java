package com.womens.womensassociation.ui.about_us_fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.womens.womensassociation.R;
import com.womens.womensassociation.models.AboutUsModel;
import com.womens.womensassociation.ui.map.MapsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.YTParams;

public class AboutUs extends Fragment {
    View view;
    String youtube;
    TextView textView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
   public static Float longtiude, latitude;
    FloatingActionButton floatingActionButton;
    ProgressDialog dialog;
    Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return view = inflater.inflate(R.layout.fragment_about_us, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intialview();
        intialdialod();
        inialdatabase();
        getdata();
    }

    private void intialview() {
        textView = view.findViewById(R.id.text_about_us);
        floatingActionButton = view.findViewById(R.id.location);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    void getdata() {
        FirebaseDatabase.getInstance().getReference().child("pageone").child("about us").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dialog.show();
                String des = dataSnapshot.child("describe").getValue(String.class);
                String image_urlll = dataSnapshot.child("image_url").getValue(String.class);
                String lat = dataSnapshot.child("latitude").getValue(String.class);
                String longt = dataSnapshot.child("longitude").getValue(String.class);

                AboutUsModel aboutUsModel = new AboutUsModel(image_urlll, des, longt, lat);
                if (aboutUsModel != null) {
                    textView.setText(aboutUsModel.getDescribe());
                    latitude = (Float.parseFloat(aboutUsModel.getLatitude()));
                    longtiude = Float.parseFloat(aboutUsModel.getLongitude());
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
            }
        });
    }
    private void inialdatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("pageone").child("youtube").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                youtube = dataSnapshot.getValue(String.class);
                intialyoutube();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void intialyoutube() {
        YoutubePlayerView youtubePlayerView = view.findViewById(R.id.youtubePlayerView);
        youtubePlayerView.playFullscreen();

        // Control values
        // see more # https://developers.google.com/youtube/player_parameters?hl=en
        YTParams params = new YTParams();

        // params.setControls(0); // hide control
        // params.setVolume(100); // volume control
        // params.setPlaybackQuality(PlaybackQuality.small); // video quality control

        // initialize YoutubePlayerCallBackListener with Params and VideoID
        // youtubePlayerView.initialize("WCchr07kLPE", params, new YoutubePlayerView.YouTubeListener())


        // initialize YoutubePlayerCallBackListener with Params and Full Video URL
        // To Use - avoid UMG block!!!! but you'd better make own your server for your real service.
        // youtubePlayerView.initializeWithCustomURL("p1Zt47V3pPw" or "http://jaedong.net/youtube/p1Zt47V3pPw", params, new YoutubePlayerView.YouTubeListener())

        // make auto height of youtube. if you want to use 'wrap_content'
        youtubePlayerView.setAutoPlayerHeight(context);
        // initialize YoutubePlayerCallBackListener and VideoID

        youtubePlayerView.initialize(youtube, new YoutubePlayerView.YouTubeListener() {

            @Override
            public void onReady() {
                // when player is ready.
                //dialog.dismiss();

            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {
                /**
                 * YoutubePlayerView.STATE
                 *
                 * UNSTARTED, ENDED, PLAYING, PAUSED, BUFFERING, CUED, NONE
                 *
                 */

            }

            @Override
            public void onPlaybackQualityChange(String arg) {
            }

            @Override
            public void onPlaybackRateChange(String arg) {

            }

            @Override
            public void onError(String error) {
                // dialog.dismiss();
            }

            @Override
            public void onApiChange(String arg) {

            }

            @Override
            public void onCurrentSecond(double second) {
                // currentTime callback


            }

            @Override
            public void onDuration(double duration) {
                // total duration

            }


            @Override
            public void logs(String log) {
                // javascript debug log. you don't need to use it.
            }
        });

    }

    private void intialdialod() {
        dialog = new ProgressDialog(getContext());
        dialog.setTitle("uploading..");
        dialog.setMessage("plese wait...");
        dialog.setCancelable(false);
    }

}
