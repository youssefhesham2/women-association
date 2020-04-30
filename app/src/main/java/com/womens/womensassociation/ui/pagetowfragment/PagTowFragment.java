package com.womens.womensassociation.ui.pagetowfragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ethanhua.skeleton.SkeletonScreen;
import com.womens.womensassociation.CardAdapter;
import com.womens.womensassociation.CardFragment;
import com.womens.womensassociation.CardFragment2;
import com.womens.womensassociation.CardFrgmentPagerAdapter2;
import com.womens.womensassociation.R;
import com.womens.womensassociation.ui.homepage.Homepage;
import com.jaedongchicken.ytplayer.YoutubePlayerView;
import com.jaedongchicken.ytplayer.model.YTParams;


public class PagTowFragment extends Fragment {
    View view;
    Context context;
    int childorno;
    SkeletonScreen skeletonScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      context=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_pag_tow, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int size=0;
        int size2=0;
        if(Homepage.chechk!=2) {
             size = ((Homepage.homePageTowModels.get(CardFragment.possitionn).getImagesurls().size()));
        }
         if(Homepage.chechk==2){
              size2=((Homepage.homePageTowModels2.get(Homepage.pos).getImagesurls().size()));
         }
        if((size2!=0&&Homepage.chechk==2)||(size!=0&&Homepage.chechk!=2)) {
            CardFragment2 cardFragment = new CardFragment2();
            ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager2);
            CardFrgmentPagerAdapter2 pagerAdapter = new CardFrgmentPagerAdapter2(getChildFragmentManager(), dpToPixels(2, context));
            PagTowFragment.ShadowTransformer fragmentCardShadowTransformer = new PagTowFragment.ShadowTransformer(viewPager, pagerAdapter);
            fragmentCardShadowTransformer.enableScaling(true);

            viewPager.setAdapter(pagerAdapter);
            viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
            viewPager.setOffscreenPageLimit(3);
            childorno=2;
        }
        if (Homepage.chechk==2){
            TextView detils = view.findViewById(R.id.detials);
            detils.setText(Homepage.homePageTowModels2.get(Homepage.pos).getDetials());
            String youtube_id=Homepage.homePageTowModels2.get(Homepage.pos).getYoutube2();
            intialyoutube(youtube_id);
        }
        else {
            TextView detils = view.findViewById(R.id.detials);
            detils.setText(Homepage.homePageTowModels.get(CardFragment.possitionn).getDetials());
            String youtube_id=Homepage.homePageTowModels.get(CardFragment.possitionn).getYoutube2();
            intialyoutube(youtube_id);
        }


    }

    public class ShadowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

        private ViewPager viewPager;
        private CardAdapter cardAdapter;
        private float lastOffset;
        private boolean scalingEnabled;

        public ShadowTransformer(ViewPager viewPager, CardAdapter adapter) {
            this.viewPager = viewPager;
            viewPager.addOnPageChangeListener(this);
            cardAdapter = adapter;
        }

        public void enableScaling(boolean enable) {
            if (scalingEnabled && !enable) {
                // shrink main card
                CardView currentCard = cardAdapter.getCardViewAt(viewPager.getCurrentItem());
                if (currentCard != null) {
                    currentCard.animate().scaleY(1);
                    currentCard.animate().scaleX(1);
                }
            }else if(!scalingEnabled && enable){
                // grow main card
                CardView currentCard = cardAdapter.getCardViewAt(viewPager.getCurrentItem());
                if (currentCard != null) {
                    //enlarge the current item
                    currentCard.animate().scaleY(1.1f);
                    currentCard.animate().scaleX(1.1f);
                }
            }
            scalingEnabled = enable;
        }

        @Override
        public void transformPage(View page, float position) {

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int realCurrentPosition;
            int nextPosition;
            float baseElevation = cardAdapter.getBaseElevation();
            float realOffset;
            boolean goingLeft = lastOffset > positionOffset;

            // If we're going backwards, onPageScrolled receives the last position
            // instead of the current one
            if (goingLeft) {
                realCurrentPosition = position + 1;
                nextPosition = position;
                realOffset = 1 - positionOffset;
            } else {
                nextPosition = position + 1;
                realCurrentPosition = position;
                realOffset = positionOffset;
            }

            // Avoid crash on overscroll
            if (nextPosition > cardAdapter.getCount() - 1
                    || realCurrentPosition > cardAdapter.getCount() - 1) {
                return;
            }

            CardView currentCard = cardAdapter.getCardViewAt(realCurrentPosition);

            // This might be null if a fragment is being used
            // and the views weren't created yet
            if (currentCard != null) {
                if (scalingEnabled) {
                    currentCard.setScaleX((float) (1 + 0.1 * (1 - realOffset)));
                    currentCard.setScaleY((float) (1 + 0.1 * (1 - realOffset)));
                }
                currentCard.setCardElevation((baseElevation + baseElevation
                        * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
            }

            CardView nextCard = cardAdapter.getCardViewAt(nextPosition);

            // We might be scrolling fast enough so that the next (or previous) card
            // was already destroyed or a fragment might not have been created yet
            if (nextCard != null) {
                if (scalingEnabled) {
                    nextCard.setScaleX((float) (1 + 0.1 * (realOffset)));
                    nextCard.setScaleY((float) (1 + 0.1 * (realOffset)));
                }
                nextCard.setCardElevation((baseElevation + baseElevation
                        * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (realOffset)));
            }

            lastOffset = positionOffset;
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    private void intialyoutube(String youtube_id) {
        YoutubePlayerView youtubePlayerView =view.findViewById(R.id.youtubePlayerView2);
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
        youtubePlayerView.setAutoPlayerHeight(getContext());
        // initialize YoutubePlayerCallBackListener and VideoID
        youtubePlayerView.initialize(youtube_id, new YoutubePlayerView.YouTubeListener() {

            @Override
            public void onReady() {
                // when player is ready.
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

}
