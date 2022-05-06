package com.example.androidproject;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Recipe type for storing recipes
 * @author Matias Niemelä
 */
public class Recipe implements Parcelable {

    private String name;
    private String category;
    private int id;
    private String imagePath;
    private boolean isFavorite;
    private List<String> ingredients;
    private List<String> instructions;

    /**
     * Instantiates a new Recipe.
     */
    public Recipe() {
        instructions = new ArrayList<>();
        ingredients = new ArrayList<>();
    }

    /**
     * Instantiates a new Recipe.
     *
     * @param id         the recipe id
     * @param name       the name
     * @param category   the category
     * @param isFavorite is favorite
     */
    public Recipe(int id, String name, String category, boolean isFavorite) {
        this.name = name;
        this.category = category;
        this.id = id;
        this.isFavorite = isFavorite;
    }

    /**
     * Instantiates a new Recipe.
     *
     * @param id         the id
     * @param name       the name
     * @param category   the category
     * @param isFavorite is favorite
     * @param imagePath  the image path
     */
    public Recipe(int id, String name, String category, boolean isFavorite, String imagePath) {
        instructions = new ArrayList<>();
        ingredients = new ArrayList<>();

        this.name = name;
        this.category = category;
        this.id = id;
        this.imagePath = imagePath;
        this.isFavorite = isFavorite;
    }

    /**
     * Instantiates a new Recipe.
     *
     * @param name         the name
     * @param category     the category
     * @param id           the id
     * @param imagePath    the image path
     * @param ingredients  the ingredients
     * @param instructions the instructions
     */
    public Recipe(String name, String category, int id, String imagePath, List<String> ingredients, List<String> instructions) {
        this.name = name;
        this.category = category;
        this.id = id;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    /**
     * Instantiates a new Recipe from parcel.
     *
     * @param in the parcel
     */
    protected Recipe(Parcel in) {
        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();
        in.readStringList(ingredients);
        in.readStringList(instructions);
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

    /**
     * The constant CREATOR which creates a parcel from recipe.
     */
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

    /**
     * Gets recipe name.
     *
     * @return the recipe name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets recipe name.
     *
     * @param name the recipe name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets recipe category.
     *
     * @return the recipe category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the recipe category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets id.
     *
     * @return the recipe id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the recipe id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets image path.
     *
     * @return the recipe image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets image path.
     *
     * @param imagePath the recipe image path
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Gets ingredients.
     *
     * @return the recipe ingredients list
     */
    public List<String> getIngredients() {
        return ingredients;
    }

    /**
     * Sets ingredients.
     *
     * @param ingredients the recipe ingredients list
     */
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Gets instructions.
     *
     * @return the recipe instructions list
     */
    public List<String> getInstructions() {
        return instructions;
    }

    /**
     * Sets instructions.
     *
     * @param instructions the recipe instructions list
     */
    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    /**
     * Is favorite boolean.
     *
     * @return the boolean
     */
    public boolean isFavorite() {
        return isFavorite;
    }

    /**
     * Sets favorite.
     *
     * @param favorite true or false
     */
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @NonNull
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
        parcel.writeStringList(ingredients);
        parcel.writeStringList(instructions);
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
