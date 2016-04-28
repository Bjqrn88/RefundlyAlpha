package com.macbear.refundlyalpha.Realm;

import io.realm.RealmObject;

/**
 * Created by MacBear on 28/04/16.
 */
public class PostInfomation extends RealmObject {
    private String posterID;
    private String postProfileID;
    private String collectorID;
    private double lat;
    private double lnt;
    private int size;
    private String comment;
    private String adress;
    private String postNumber;

    public String getPosterID() {
        return posterID;
    }

    public void setPosterID(String posterID) {
        this.posterID = posterID;
    }

    public String getPostProfileID() {
        return postProfileID;
    }

    public void setPostProfileID(String postProfileID) {
        this.postProfileID = postProfileID;
    }

    public String getCollectorID() {
        return collectorID;
    }

    public void setCollectorID(String collectorID) {
        this.collectorID = collectorID;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLnt() {
        return lnt;
    }

    public void setLnt(double lnt) {
        this.lnt = lnt;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }
}
