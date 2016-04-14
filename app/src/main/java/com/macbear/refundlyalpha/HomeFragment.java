package com.macbear.refundlyalpha;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {

    ListView currentPosts, oldPosts;
    ArrayAdapter<String> currentArrayAdapter;
    ArrayAdapter<String> oldArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        currentArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_layout);
        oldArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_layout);

        currentPosts = (ListView) root.findViewById(R.id.currentpostslist);
        oldPosts = (ListView) root.findViewById(R.id.oldpostslist);
        currentPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("CurrentList", "Jeg blev trykket");
            }
        });
        oldPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("OldList", "Jeg blev trykket");
            }
        });
        currentPosts.setAdapter(currentArrayAdapter);
        oldPosts.setAdapter(oldArrayAdapter);

        currentArrayAdapter.clear();
        oldArrayAdapter.clear();
        currentArrayAdapter.addAll(Arrays.asList("One", "Two", "Three", "Four", "Five", "Six", "Seven"));
        oldArrayAdapter.addAll(Arrays.asList("OneOLD", "TwoOLD", "ThreeOLD", "FourOld", "FiveOld", "SixOld", "SevenOld"));


        return root;
    }
}
