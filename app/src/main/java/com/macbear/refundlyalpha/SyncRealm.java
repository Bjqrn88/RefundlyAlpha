package com.macbear.refundlyalpha;


import android.util.Log;

import com.baasbox.android.BaasException;
import com.baasbox.android.BaasHandler;
import com.baasbox.android.BaasQuery;
import com.baasbox.android.BaasResult;
import com.baasbox.android.json.JsonObject;
import com.macbear.refundlyalpha.Realm.PostInfomation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by MacBear on 18/05/16.
 */
public class SyncRealm {

    private static final String TAG = "SyncRealm";
    private List<JsonObject> result;
    private Realm realm;
    RealmResults<PostInfomation> results;
    BaasQuery query;

    public SyncRealm(){
        query = BaasQuery.builder().collection("PostInformation").build();
        realm = Realm.getDefaultInstance();
    }

    public void sync(){
        query.query(new BaasHandler<List<JsonObject>>(){
            @Override
            public void handle(BaasResult<List<JsonObject>> res){
                if (res.isSuccess()){
                    try {
                        result = res.get();
                        syncWithRealm();
                    } catch (BaasException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.e(TAG, "handle: "+res.error());
                }
            }
        });
    }

    private void syncWithRealm(){
        Date convertDate = new Date();
        SimpleDateFormat simple = new SimpleDateFormat("E MMM d HH:mm:ss zzzz yyyy");
        realm.beginTransaction();
        realm.delete(PostInfomation.class);
        realm.commitTransaction();

        for (JsonObject json: result) {
            realm.beginTransaction();
            PostInfomation post = new PostInfomation();
            post.setPostId(json.getInt("postId"));
            post.setPostProfileID(json.getString("posterProfileID"));
            post.setCollectorID(json.getString("collectorID"));
            post.setAddress(json.getString("address"));
            post.setPostNumber(json.getString("postalCode"));
            post.setLat(json.getDouble("lat"));
            post.setLnt(json.getDouble("lnt"));
            post.setSize(json.getInt("size"));
            post.setComment(json.getString("comment"));
            try {
                convertDate = simple.parse(json.getString("timeStamp"));
            } catch (ParseException e) {
                Log.e(TAG, "syncWithRealm: " + e.getLocalizedMessage());
            }
            post.setTimestamp(convertDate);
            realm.copyToRealmOrUpdate(post);
            realm.commitTransaction();

        }

        results = realm.where(PostInfomation.class).findAll();

        realm.close();
    }
}
