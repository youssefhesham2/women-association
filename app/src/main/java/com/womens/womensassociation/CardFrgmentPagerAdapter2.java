package com.womens.womensassociation;


import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.womens.womensassociation.ui.homepage.Homepage;

import java.util.ArrayList;
import java.util.List;

public class CardFrgmentPagerAdapter2 extends FragmentStatePagerAdapter implements CardAdapter {

    private List<CardFragment2> fragments;
    private float baseElevation;
    int size;
    public CardFrgmentPagerAdapter2(FragmentManager fm, float baseElevation) {
        super(fm);
        fragments = new ArrayList<>();
        this.baseElevation = baseElevation;
        if(Homepage.chechk==2){
            size=Homepage.homePageTowModels2.get(Homepage.pos).getImagesurls().size();
        }
        else {
            size=Homepage.homePageTowModels.get(CardFragment.possitionn).getImagesurls().size();
        }
        for(int i = 0; i<size; i++){
            addCardFragment(new CardFragment2());
        }
    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return fragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return CardFragment2.getInstance(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        fragments.set(position, (CardFragment2) fragment);
        return fragment;
    }

    public void addCardFragment(CardFragment2 fragment) {
        fragments.add(fragment);
    }

}