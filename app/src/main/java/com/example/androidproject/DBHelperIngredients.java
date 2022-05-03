package com.example.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite Helper Class, contains necessary methods for database management
 *
 * @author Matias Niemelä
 * @version 1.0 4/2022
 */


public class DBHelperIngredients extends SQLiteOpenHelper {

    // ingredients.db config
    public static final String INGREDIENTS_TABLE = "INGREDIENTS_TABLE";
    public static final String COLUMN_INGREDIENT_NAME = "INGREDIENT_NAME";
    public static final String COLUMN_RECIPE_ID = "RECIPE_ID";
    public static final String COLUMN_INGREDIENT_ID = "ID";

    public DBHelperIngredients(@Nullable Context context) {
        super(context, "ingredients.db", null, 1);
    }

    // create database, called when accessing database for the first time
    @Override
    public void onCreate(SQLiteDatabase database) {
        String createTableStatement =
                "CREATE TABLE " + INGREDIENTS_TABLE + " (" +
                        COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_INGREDIENT_NAME + " TEXT, " +
                        COLUMN_RECIPE_ID + " INTEGER)";
        database.execSQL(createTableStatement);
    }

    // called when database version changes, prevents app breaking due to database design change
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
    // Get
    public List<Ingredients> getIngredients(int recipeID) {
        ArrayList<Ingredients> ingredientsList = new ArrayList<>();
        return ingredientsList;
    }

    public Boolean deleteIngredient(String ingredient, int id){
        if(true) {
            return true;
        }
        return false;
    }
    // deletes all ingredients with this recipeID

    public Boolean deleteIngredients(int id){
        if(true) {
            return true;
        }
        return false;
    }

    public ArrayList<Ingredients> getIngredients(Recipe recipe) {
        ArrayList<Ingredients> ingredients = new ArrayList<>();

        // code that gets ingredients

        return ingredients;
    }

}
