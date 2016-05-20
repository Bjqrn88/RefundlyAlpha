package com.macbear.refundlyalpha;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.macbear.refundlyalpha.Realm.PostInfomation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    ListView currentPosts;
    ArrayAdapter<String> currentArrayAdapter;
    MapFragment mapFragment = new MapFragment();
    RealmResults<PostInfomation> results;
    Realm realm;
    private SyncRealm sync;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        realm = Realm.getDefaultInstance();

        currentArrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_layout);

        currentPosts = (ListView) root.findViewById(R.id.currentpostslist);

        currentPosts.setAdapter(currentArrayAdapter);

        currentArrayAdapter.clear();
        currentArrayAdapter.addAll(getCurrentPosts());

        currentPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mapFragment.setCoords(new LatLng(results.get(i).getLat(), results.get(i).getLnt()));

                getFragmentManager().beginTransaction()
                        .replace(R.id.frameholder, new MapFragment())
                        .commit();
            }
        });

        sync = new SyncRealm();
        sync.sync();
        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    public List<String> getCurrentPosts(){
        SimpleDateFormat simple = new SimpleDateFormat("E MMM d HH:mm:ss");
        results = realm.where(PostInfomation.class).equalTo("collectorID","").findAll();

        List<String> list = new ArrayList<String>();

        for (PostInfomation post:results) {
            String date = simple.format(post.getTimestamp());
            list.add("Size: "+post.getSize()+", "+date);
        }

        return list;
    }
}
