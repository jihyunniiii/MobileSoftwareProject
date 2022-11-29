package com.jihyun.mobilesoftwareproject;

public class Menudata {
    String time;
    String mnn;
    String kcal;

    public Menudata(String time, String mnn, String kcal) {
        this.time = time;
        this.mnn= mnn;
        this.kcal = kcal;
    }

    public String gettime(){
        return time;
    }

    public String getmnn(){
        return mnn;
    }

    public String getkcal(){
        return kcal;
    }

    public void settime(String time){
        this.time = time;
    }

    public void setmnn(String mnn){
        this.mnn = mnn;
    }

    public void setkcal(String kcal){
        this.kcal = kcal;
    }
}
