package com.example.dbvideomarker.database.entitiy;

public class Media {

    private int resId;
    private String Name;
    private String Dur;
    private String Size;
    private String Mime;
    private String Added;
    private String ContentUri;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDur() {
        return Dur;
    }

    public void setDur(String dur) {
        Dur = dur;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getMime() {
        return Mime;
    }

    public void setMime(String mime) {
        Mime = mime;
    }

    public String getAdded() {
        return Added;
    }

    public void setAdded(String added) {
        Added = added;
    }

    public String getContentUri() {
        return ContentUri;
    }

    public void setContentUri(String contentUri) {
        ContentUri = contentUri;
    }
}
