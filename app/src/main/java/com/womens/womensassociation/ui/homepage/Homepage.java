package com.womens.womensassociation.ui.homepage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ethanhua.skeleton.SkeletonScreen;
import com.womens.womensassociation.CardAdapter;
import com.womens.womensassociation.CardFragment;
import com.womens.womensassociation.CardFragmentPagerAdapter;
import com.womens.womensassociation.R;
import com.womens.womensassociation.models.HomePageTowModel;
import com.womens.womensassociation.models.model;
import com.womens.womensassociation.ui.pagetowfragment.PagTowFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Homepage extends Fragment {
    View view;
    Context context;
    public static List<model> models;
    public static List<model> models2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    int size;
    View view2;
    public static int chechk, pos;
    GalleryOneAdapter2 adapter2;
    ProgressDialog dialog;
    public static List<HomePageTowModel> homePageTowModels, homePageTowModels2;
    public static List<String> imagesurls;
    SkeletonScreen skeletonScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        intialdialod();
       // dialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_homepage, container, false);
        view2 = inflater.inflate(R.layout.item_page, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
     /*   skeletonScreen = Skeleton.bind(view)
                .load(R.layout.fragment_homepage)
                .show();*/
        chechk = 0;
        inialdatabase();
        getdataforviewpager();
        intialrecyclerview();
        intialgallerytow();
        //itialscrollview();
        // intialviewpagerr();
        //intialrecyclerview2();

    }

    private void intialdialod() {
        dialog = new ProgressDialog(context);
        dialog.setTitle("Loading..");
        dialog.setMessage("plese wait...");
        dialog.setCancelable(false);
    }

    private void intialviewpagerr() {
        CardFragment cardFragment = new CardFragment();
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        size = models.size();

        CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getChildFragmentManager(), dpToPixels(2, context), size);
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
        fragmentCardShadowTransformer.enableScaling(true);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
    }

    private void intialrecyclerview2() {
        models = new ArrayList<>();
        // final RecyclerView recyclerView2=view.findViewById(R.id.recycler_view2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        //  recyclerView2.setLayoutManager(layoutManager);
        databaseReference.child("pageone").child("galleryone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ii = null, b = null, c = null;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    model modell = snapshot.getValue(model.class);
                    models.add(modell);
                    models.add(new model("jjjj", "ooo", "uuu"));
                }

                GalleryOneAdapter2 galleryOneAdapter2 = new GalleryOneAdapter2(models);
                //      recyclerView2.setAdapter(galleryOneAdapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(context, databaseError.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //@RequiresApi(api = Build.VERSION_CODES.M)
    /*private void itialscrollview() {
        final BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.BottonNavigation);
        final ScrollView scrollView=view.findViewById(R.id.scrolll);

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                int x = i1 - i3;
                if (x > 0) {
                    //scroll up
                    bottomNavigationView.setVisibility(View.GONE);
                } else if (x < 0) {
                    //scroll down
                    bottomNavigationView.setVisibility(View.VISIBLE);


                } else {

                }
            }
        });
        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView.getChildAt(0).getBottom()
                                <= (scrollView.getHeight() + scrollView.getScrollY())) {
                            //scroll view is at bottom
                            //    bottomNavigationView.setVisibility(View.VISIBLE);
                        } else {
                            //scroll view is not at bottom

                        }
                    }
                });

    }*/

    private void intialgallerytow() {
        models2 = new ArrayList<>();
        homePageTowModels2 = new ArrayList<>();
        databaseReference.child("pageone").child("gallerytow").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models2.clear();
                homePageTowModels2.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.hasChildren() == true) {
                        model modell = snapshot.getValue(model.class);
                        if (modell != null) {
                            models2.add(modell);
                            // models2.add(modell);
                            //models2.add(modell);

                        }
                    }


                    String detials = snapshot.child("pagetow").child("detials").getValue(String.class);
                    String youtube2 = snapshot.child("pagetow").child("youtube2").getValue(String.class);

                    HomePageTowModel homePageTowModel = new HomePageTowModel(youtube2, detials);

                    for (DataSnapshot snapshot12 : snapshot.child("pagetow").child("alboum").getChildren()) {
                        String imageurl = snapshot12.getValue(String.class);
                        if (imageurl != null) {
                            homePageTowModel.setImagesurls(imageurl);

                        }
                    }


                    assert homePageTowModel != null;
                    homePageTowModels2.add(homePageTowModel);


                }

                 adapter2 = new GalleryOneAdapter2(models2);
                recyclerView.setAdapter(adapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void intialrecyclerview() {
        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void inialdatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);

    }

    private void getdataforviewpager() {
        models = new ArrayList<>();
        homePageTowModels = new ArrayList<>();
        imagesurls = new ArrayList<>();

        databaseReference.child("pageone").child("galleryone").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                models.clear();
                homePageTowModels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    model modell = snapshot.getValue(model.class);
                    if (modell != null) {
                        models.add(modell);
                    }
                    String detials = snapshot.child("pagetow").child("detials").getValue(String.class);
                    String youtube2 = snapshot.child("pagetow").child("youtube2").getValue(String.class);

                    HomePageTowModel homePageTowModel = new HomePageTowModel(youtube2, detials);

                    for (DataSnapshot snapshot12 : snapshot.child("pagetow").child("alboum").getChildren()) {
                        String imageurl = snapshot12.getValue(String.class);
                        homePageTowModel.setImagesurls(imageurl);
                    }

                    assert homePageTowModel != null;
                    homePageTowModels.add(homePageTowModel);

                }
                intialviewpagerr();
               // skeletonScreen.hide();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(context, databaseError.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });

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
            } else if (!scalingEnabled && enable) {
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

    class GalleryOneAdapter extends RecyclerView.Adapter<GalleryOneAdapter.viewholder> {
        List<model> models;

        public GalleryOneAdapter(List<model> models) {
            this.models = models;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.page_one_recycler_item, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, final int position) {
            model modell = models.get(position);
            holder.title.setText(modell.getTitle());
            Picasso.get().load(modell.getImage_url()).into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return models.size();
        }

        class viewholder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView title;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image2);
                title = itemView.findViewById(R.id.title2);
            }
        }
    }

    class GalleryOneAdapter2 extends RecyclerView.Adapter<GalleryOneAdapter2.viewholder> {
        List<model> models;

        public GalleryOneAdapter2(List<model> models) {
            this.models = models;
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_page, parent, false);
            return new viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final viewholder holder, final int position) {
            model modell = models.get(position);
            String titlee=modell.getTitle();
            holder.title.setText(modell.getTitle());
            if(titlee.isEmpty()){
                holder.title.setVisibility(View.INVISIBLE);
            }
            Picasso.get().load(modell.getImage_url()).into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chechk = 2;
                    pos = position;
                    holder.replacefragment();
                }
            });
        }

        @Override
        public int getItemCount() {
            return models.size();
        }

        class viewholder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView title;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image);
                title = itemView.findViewById(R.id.title);
            }

            void replacefragment() {
                PagTowFragment fragment2 = new PagTowFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.view, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        }
    }


    /*  private void initializeYoutubeFragment() {

        YouTubePlayerView youTubePlayerView=view.findViewById(R.id.youtube2);
        youTubePlayerView.initialize("AIzaSyBpIrVYaxSkU4cq9h5oGyrBvo6-a1nR8nk", Homepage.this);

    }*/

/*
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo("50ttf6SfkTE");

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }*/
}
