package com.example.androidproject;



import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 *
 * @author Ella Sigvart
 * @version 4/2022
 */

public class Ingredients implements Parcelable {
    private String name;
    private int id;
    private int recipeId;

    public Ingredients(String name) { this.name = name; }

    public Ingredients(String name, int id, int recipeId) {

        this.id = id;
        this.name = name;
        this.recipeId = recipeId;

    }

    private Ingredients(Parcel in)   {
        name = in.readString();
        id = in.readInt();
        recipeId = in.readInt();
    }

    /*getters and setters */

    public int getId () { return id; }

    public String getName() { return name; }

    public int getRecipeId() { return recipeId; }

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setRecipeId(int recipeId)  { this.recipeId = recipeId; }

    
    @Override
    public int describeContents () { return 0; }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(id);
        parcel.writeInt(recipeId);
    }

    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {

        @Override
        public Ingredients createFromParcel(Parcel source) {
            return new Ingredients(source);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }

    };

    @Override
    public String toString() {
            return "Ingredients{" + "name=" + name + ",id= " +  id + '\'' + ",recipeId=" + recipeId + '}';

    }

    };


