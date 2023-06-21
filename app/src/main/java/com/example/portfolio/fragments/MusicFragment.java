package com.example.portfolio.fragments;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.portfolio.R;

public class MusicFragment extends Fragment {

    public MusicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Called when the fragment is being created, but BEFORE its view hierarchy is created
        PRIMARILY USED FOR:
         1. Performing NON-UI related initialization
         - such as initializing variables
         - setting up dependencies
         - or performing any other setup tasks that don't require access to the fragment's view hierarchy
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        // SET TOOLBAR AS ACTIONBAR
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        // REMOVE THE NAME OF APP 'PORTFOLIO' FROM THE ACTIONBAR
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        return view;
    }
}

/*
onCreateView(): This method is called when the fragment needs to create its layout and view hierarchy.
In this method, you typically inflate a layout XML file to define the UI of the fragment and initialize
any UI elements or references you need to interact with. It is important to return the root view of the
fragment's layout from this method.

onViewCreated(): This method is called immediately after onCreateView() and indicates that the view hierarchy
of the fragment has been created. It is useful for performing additional setup or initialization that requires
access to the fragment's view hierarchy. Here, you can find and reference UI elements using findViewById()
and set up event listeners or perform any other view-related operations.
 */