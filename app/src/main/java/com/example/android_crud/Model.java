package com.example.android_crud;

public class Model {

    String id, tile;

    public Model(){

    }

    public Model(String id, String tile){
        this.id = id;
        this.tile = tile;

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getTile(){
        return tile;
    }


}
