package com.macbear.refundlyalpha.Realm;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by MacBear on 28/04/16.
 */
public class PostInfomation extends RealmObject {
    @PrimaryKey
    private int posterID;

    private String postProfileID;
    private String collectorID;
    private double lat;
    private double lnt;
    private int size;
    private String comment;
    private String adress;
    private String postNumber;
    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getPosterID() {
        return posterID;
    }

    public void setPosterID(int posterID) {
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

    public void setAddress(String adress) {
        this.adress = adress;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }
}
