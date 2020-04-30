package com.womens.womensassociation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.womens.womensassociation.ui.homepage.Homepage;
import com.womens.womensassociation.ui.pagetowfragment.PagTowFragment;
import com.squareup.picasso.Picasso;
import com.womens.womensassociation.models.model;

import java.util.List;

public  class  CardFragment extends Fragment {
    private CardView cardView;
    //static List<model> models;
    public static int possitionn;
    List<model> models;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        models=Homepage.models;
    }

    public static Fragment getInstance(int position) {
        CardFragment f = new CardFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);

        f.setArguments(args);
        return f;
    }



    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_page, container, false);
        final View view2 = inflater.inflate(R.layout.fragment_homepage, container, false);

       // cardView = (CardView) view.findViewById(R.id.cardView);
        //cardView.setMaxCardElevation(cardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);

        TextView title = (TextView) view.findViewById(R.id.title);
         title = (TextView) view.findViewById(R.id.title);
        ImageView imageView=view.findViewById(R.id.image);
       Picasso.get().load(models.get(getArguments().getInt("position")).getImage_url()).into(imageView);

        title.setText(models.get(getArguments().getInt("position")).getTitle());
        String titlee=models.get(getArguments().getInt("position")).getTitle();
        if(titlee.isEmpty()){
            title.setVisibility(View.INVISIBLE);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                possitionn=(getArguments().getInt("position"));
                replacefragment();
            }
        });
        return view;
    }

    public CardView getCardView() {
        return cardView;
    }
    void replacefragment(){
        PagTowFragment fragment2=new PagTowFragment();
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.view,fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}