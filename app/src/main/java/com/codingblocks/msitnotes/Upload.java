package com.codingblocks.msitnotes;

public class Upload {
    public String name;
    public String url;
    public String size;
    public String firstLetter;
    public String timeInMillisecond;
    public int id;

    public Upload() {
    }

    public String getTimeInMillisecond() {
        return timeInMillisecond;
    }

    public Upload(String name, String url, String size, String firstLetter, String timeInMillisecond, int id) {
        this.name = name;
        this.url = url;
        this.size=size;
        this.firstLetter=firstLetter;
        this.timeInMillisecond=timeInMillisecond;
        this.id=id;

    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getSize(){ return size; }

    public String getFirstLetter(){ return firstLetter; }

    public int getId() { return id; }
}
