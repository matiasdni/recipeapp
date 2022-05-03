package com.example.androidproject;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DBHelperSingleton {

    private Context context;
    private DBHelper dbHelper;
    private DBHelperIngredients ingredientsHelper;
    private DBHelperInstructions dbHelperInstructions;
    private static DBHelperSingleton ourInstance;

    public static DBHelperSingleton getInstance(Context context) {
        if (ourInstance == null) {
            // ensures only one instance is created
            synchronized (DBHelperSingleton.class) {
                ourInstance = new DBHelperSingleton(context);
            }
        }
        return ourInstance;
    }

    private DBHelperSingleton(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        ingredientsHelper = new DBHelperIngredients(context);
        dbHelperInstructions = new DBHelperInstructions(context);
    }

    public void addRecipe(Recipe recipe) {
        // success
        if (dbHelper.addRecipe(recipe)) {
            Toast.makeText(context, "Recipe added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Adding recipe failed", Toast.LENGTH_SHORT).show();
        }
    }

    public int getRecipeID(Recipe recipe) {
        return dbHelper.getRecipeID(recipe);
    }

    public void deleteRecipe(Recipe recipe) {
        // success
        if (dbHelper.deleteRecipe(recipe)) {
            Toast.makeText(context, "Recipe deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Recipe deletion failed", Toast.LENGTH_SHORT).show();
        }
    }

    // retrieves recipes from database
    public List<Recipe> getRecipes() {
        return dbHelper.getRecipes();
    }

    // retrieves ingredients and instructions of a specific recipe from database
    public ArrayList<Ingredients> getIngredients(Recipe recipe) {
        return ingredientsHelper.getIngredients(recipe);
    }

    public ArrayList<Instructions> getInstructions(Recipe recipe) {
        return dbHelperInstructions.getInstructions(recipe);
    }



    // setIngredients

    }

    ///setInstructions





