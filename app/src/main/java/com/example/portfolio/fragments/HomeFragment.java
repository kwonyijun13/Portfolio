package com.example.portfolio.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.animation.LayoutTransition;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.portfolio.R;
import com.google.android.material.button.MaterialButton;

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

        // CUSTOM FONT
        Typeface customFont = Typeface.createFromAsset(view.getResources().getAssets(), "fonts/CoffeeHealing-1GrKe.ttf");
        qrButton.setTypeface(customFont);
        gpsButton.setTypeface(customFont);
        youtubeButton.setTypeface(customFont);
        musicButton.setTypeface(customFont);
    }
}