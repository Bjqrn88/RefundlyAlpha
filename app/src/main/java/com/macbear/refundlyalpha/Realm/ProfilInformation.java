package com.macbear.refundlyalpha.Realm;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Eier on 21.04.2016.
 */
public class ProfilInformation extends RealmObject {
    @PrimaryKey
    @Required
    private String profileID;
    private String username;
    private String postNumber;
    private String adress;
    private String phone;


    private Boolean collector;

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getCollector() {return collector;}
    
    public void setCollector(Boolean collector) {this.collector = collector;}
}
