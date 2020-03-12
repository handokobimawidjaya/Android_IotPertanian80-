package com.example.hans.agrigo.LihatZona.Support;

import com.google.gson.annotations.SerializedName;

public class Item_Zona {
    @SerializedName("zona_name")
    private String zona_name;

    @SerializedName("zona_number")
    private String zona_number;


    public void setZona_name(String zona_name){

        this.zona_name = zona_name;
    }

    public String getZona_name(){
        return zona_name;
    }

    public void setZona_number(String zona_number){

        this.zona_number = zona_number;
    }

    public String getZona_number(){
        return zona_number;
    }
    @Override
    public String toString(){
        return
                "Item_Zona{" +
                        "zona_name= '" + zona_name+ '\'' +
                        ",zona_number= '" + zona_number+ '\'' +
                        "}";
    }
}
