package com.example.androidproject;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

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
}
