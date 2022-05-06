package com.example.androidproject;


import android.os.Parcel;
import android.os.Parcelable;
/**
 *
 *
 * @author Kim Rautiainen
 * @version 4/2022
 */
public class Instructions implements Parcelable {

    private String body;
    private int id;
    private int recipeId;

    public Instructions (String body) {
        this.body=body;
    }

    public Instructions(int id,String body,int recipeId){
        this.id = id;
        this.body = body;
        this.recipeId = recipeId;

    }

    private Instructions(Parcel in){
        id = in.readInt();
        body = in.readString();
        recipeId = in.readInt();
    }
    public int getId(){
        return id;
    }
    public String getBody(){
        return body;
    }
    public int getRecipeId(){
        return recipeId;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setBody(String body){
        this.body = body;
    }
    public void setRecipeId(int recipeId){
        this.recipeId = recipeId;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeInt(id);
        parcel.writeString(body);
        parcel.writeInt(recipeId);
    }

    public static final Creator<Instructions> CREATOR = new Creator<Instructions>() {
        @Override
        public Instructions createFromParcel(Parcel parcel) {
            return new Instructions(parcel);
        }

        @Override
        public Instructions[] newArray(int size) {
            return new Instructions[size];
        }
    };

    @Override
    public String toString(){
        return "Instructions{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", recipeId=" + recipeId +
                '}';
    }
}
