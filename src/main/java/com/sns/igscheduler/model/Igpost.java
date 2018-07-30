package com.sns.igscheduler.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Igpost {
    @Id
    String id;
    String imageurl;
    String postdate;
    String posttime;
    String captions;

    public Igpost(String imageurl, String postdate, String posttime, String captions) {
        this.imageurl = imageurl;
        this.postdate = postdate;
        this.posttime = posttime;
        this.captions = captions;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getCaptions() {
        return captions;
    }

    public void setCaptions(String captions) {
        this.captions = captions;
    }

    public String toString(){
        return this.imageurl+" "+this.postdate+" "+this.posttime+" "+this.captions;
    }

}
