package com.example.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBHelperSingleton {

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    public static DBHelperSingleton ourInstance;

    public static DBHelperSingleton getInstance(Context context) {
        if (ourInstance == null){
            synchronized (DBHelperSingleton.class){
                if (ourInstance == null){
                    ourInstance = new DBHelperSingleton(context);
                }
            }
        }
        return ourInstance;
    }

    private DBHelperSingleton(Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public boolean addRecipe(Recipe recipe){
        if(dbHelper.addRecipe(recipe)){
            return true;
        }
        return false;
    }

    public void deleteRecipe(Recipe recipe){
        dbHelper.deleteRecipe(recipe);
    }

    public ArrayList<Recipe> getRecipes(){
        return dbHelper.getRecipes();
    }

}
