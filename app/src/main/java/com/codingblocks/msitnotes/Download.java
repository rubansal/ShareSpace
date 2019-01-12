package com.codingblocks.msitnotes;

import java.util.ArrayList;

public class Download {
    private String name;
    private String path;
    private int id;
    private String length;
    private long date;
    private ArrayList<Download> downloads=new ArrayList<>();

    public Download() {
    }

    public Download(String name, String path, int id, String length, long date) {

        this.name = name;
        this.path = path;
        this.id = id;
        this.length = length;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setArrayListItems(String name,String path,int id,String length,long date){
        downloads.add(new Download(name,path,id,length,date));
    }

    public ArrayList<Download> getArrayListItems(){
        return downloads;
    }
}
