package com.example.androidproject;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

    private String name;
    private String category;
    private int id;
    private String imagePath;
    private boolean isFavorite;
    private List<Ingredients> ingredients;
    private List<Instructions> instructions;

    // constructors
    public Recipe() {
        instructions = new ArrayList<>();
        ingredients = new ArrayList<>();
    }

    public Recipe(int id, String name, String category, boolean isFavorite) {
        this.name = name;
        this.category = category;
        this.id = id;
        this.isFavorite = isFavorite;
    }

    public Recipe(int id, String name, String category, boolean isFavorite, String imagePath) {
        this.name = name;
        this.category = category;
        this.id = id;
        this.imagePath = imagePath;
        this.isFavorite = isFavorite;
    }

    public Recipe(String name, String category, int id, String imagePath, List<Ingredients> ingredients, List<Instructions> instructions) {
        this.name = name;
        this.category = category;
        this.id = id;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        category = in.readString();
        id = in.readInt();
        imagePath = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isFavorite = in.readBoolean();
        } else {
            isFavorite = in.readByte() != 0;
        }
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Instructions> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instructions> instructions) {
        this.instructions = instructions;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", ingredients=" + ingredients +
                ", instructions=" + instructions +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeInt(id);
        parcel.writeString(imagePath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            parcel.writeBoolean(isFavorite);
        } else {
            parcel.writeByte((byte) (isFavorite ? 1 : 0));
        }
    }
}
