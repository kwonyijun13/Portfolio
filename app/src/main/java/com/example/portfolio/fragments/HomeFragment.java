package com.example.portfolio.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.portfolio.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton qrButton = view.findViewById(R.id.qrMaterialButton);
        MaterialButton gpsButton = view.findViewById(R.id.gpsMaterialButton);
        MaterialButton youtubeButton = view.findViewById(R.id.youtubeConverterMaterialButton);
        MaterialButton musicButton = view.findViewById(R.id.musicMaterialButton);
        MaterialButton gameButton = view.findViewById(R.id.gameMaterialButton);
        Carousel carousel = view.findViewById(R.id.carousel);
        ImageView imageView1 = view.findViewById(R.id.firstImageView);
        ImageView imageView2 = view.findViewById(R.id.secondImageView);
        ImageView imageView3 = view.findViewById(R.id.thirdImageView);

        // CUSTOM FONT
        Typeface customFont = Typeface.createFromAsset(view.getResources().getAssets(), "fonts/CoffeeHealing-1GrKe.ttf");
        qrButton.setTypeface(customFont);
        gpsButton.setTypeface(customFont);
        youtubeButton.setTypeface(customFont);
        musicButton.setTypeface(customFont);
        gameButton.setTypeface(customFont);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.cat);
        imageList.add(R.drawable.blackcat);
        imageList.add(R.drawable.cat2);

        carousel.setAdapter(new Carousel.Adapter() {
            @Override
            public int count() {
                // need to return the number of items we have in the carousel
                return imageList.size();
            }

            @Override
            public void populate(View view, int index) {
                // need to implement this to populate the view at the given index
                int imageResId = imageList.get(index);

                imageView1.setImageResource(imageResId);
                imageView2.setImageResource(imageResId);
                imageView3.setImageResource(imageResId);
            }

            @Override
            public void onNewItem(int index) {
                // called when an item is set
            }
        });
    }
}