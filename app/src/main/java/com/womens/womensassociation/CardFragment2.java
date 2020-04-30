package com.womens.womensassociation;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.womens.womensassociation.ui.homepage.Homepage;

import com.squareup.picasso.Picasso;

public  class  CardFragment2 extends Fragment {
    private CardView cardView;
    //static List<model> models;
    int size;
    ImagePopup imagePopup;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imagePopup = new ImagePopup(getContext());
        imagePopup.setWindowHeight(800); // Optional
        imagePopup.setWindowWidth(800); // Optional
        imagePopup.setBackgroundColor(Color.BLACK);  // Optional
        imagePopup.setFullScreen(true); // Optional
        imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional
    }

    public static Fragment getInstance(int position) {
        CardFragment2 f = new CardFragment2();
        Bundle args = new Bundle();
        args.putInt("position2", position);

        f.setArguments(args);
        return f;
    }

    int a=0;

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_pager_item_tow, container, false);
        if (Homepage.chechk == 2) {
            ImageView imageView = view.findViewById(R.id.image3);
            int size=(Homepage.homePageTowModels2.get(Homepage.pos).getImagesurls().size());
            if(size!=0){
                Picasso.get().load(Homepage.homePageTowModels2.get(Homepage.pos).getImagesurls().get((getArguments().getInt("position2")))).into(imageView);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  Toast.makeText(getActivity(), "Button in Card "+getArguments().getInt("position"),Toast.LENGTH_SHORT).show();
                    imagePopup.initiatePopupWithPicasso(Homepage.homePageTowModels2.get(Homepage.pos).getImagesurls().get((getArguments().getInt("position2"))));
                    imagePopup.viewPopup();

                }

            });
        }
        else {

            ImageView imageView = view.findViewById(R.id.image3);
            Picasso.get().load(Homepage.homePageTowModels.get(CardFragment.possitionn).getImagesurls().get((getArguments().getInt("position2")))).into(imageView);
            /* detils.setText*/
            Toast.makeText(getContext(), Homepage.homePageTowModels.get(CardFragment.possitionn).getDetials(), Toast.LENGTH_SHORT);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  Toast.makeText(getActivity(), "Button in Card "+getArguments().getInt("position"),Toast.LENGTH_SHORT).show();
                    imagePopup.initiatePopupWithPicasso(Homepage.homePageTowModels.get(CardFragment.possitionn).getImagesurls().get((getArguments().getInt("position2"))));
                    imagePopup.viewPopup();

                }

            });
        }
            return view;
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public CardView getCardView() {
        return cardView;
    }


}