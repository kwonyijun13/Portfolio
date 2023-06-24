package com.example.portfolio.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ViewFlipper;

import com.example.portfolio.MusicPlayerActivity;
import com.example.portfolio.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        MaterialButton qrButton = view.findViewById(R.id.qrMaterialButton);
        MaterialButton gpsButton = view.findViewById(R.id.gpsMaterialButton);
        MaterialButton youtubeButton = view.findViewById(R.id.youtubeConverterMaterialButton);
        MaterialButton musicButton = view.findViewById(R.id.musicMaterialButton);
        MaterialButton gameButton = view.findViewById(R.id.gameMaterialButton);
        Carousel carousel = view.findViewById(R.id.carousel);

        progressBar.setVisibility(View.INVISIBLE);

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

                ImageView imageView = view.findViewById(R.id.carouselImageView);
                imageView.setImageResource(imageResId);
            }

            @Override
            public void onNewItem(int index) {
                // called when an item is set
            }
        });

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(getActivity(), MusicPlayerActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        progressBar.setVisibility(View.GONE);
    }

}

/*
OnCreate():
Called when the fragment is being created, but BEFORE its view hierarchy is created
PRIMARILY USED FOR:
1. Performing NON-UI related initialization
- such as initializing variables
- setting up dependencies
- or performing any other setup tasks that don't require access to the fragment's view hierarchy

onCreateView(): This method is called when the fragment needs to create its layout and view hierarchy.
In this method, you typically inflate a layout XML file to define the UI of the fragment and initialize
any UI elements or references you need to interact with. It is important to return the root view of the
fragment's layout from this method.

onViewCreated(): This method is called immediately after onCreateView() and indicates that the view hierarchy
of the fragment has been created. It is useful for performing additional setup or initialization that requires
access to the fragment's view hierarchy. Here, you can find and reference UI elements using findViewById()
and set up event listeners or perform any other view-related operations.
 */