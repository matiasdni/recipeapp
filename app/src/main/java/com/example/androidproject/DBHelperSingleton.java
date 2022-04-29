package com.example.androidproject;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelperSingleton {

    private Context context;
    private DBHelper dbHelper;
    private static DBHelperSingleton ourInstance;

    public static DBHelperSingleton getInstance(Context context) {
        if (ourInstance == null) {
            // ensures only one instance is created
            synchronized (DBHelperSingleton.class) {
                if (ourInstance == null) {
                    ourInstance = new DBHelperSingleton(context);
                }
            }
        }
        return ourInstance;
    }

    private DBHelperSingleton(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public void addRecipe(Recipe recipe) {
        // success
        if(dbHelper.addRecipe(recipe)){
            Toast.makeText(context, "Recipe added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Adding recipe failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteRecipe(Recipe recipe) {
        // success
        if (dbHelper.deleteRecipe(recipe)) {
            Toast.makeText(context, "Recipe deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Recipe deletion failed", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Recipe> getRecipes() {
        return dbHelper.getRecipes();
    }

}
