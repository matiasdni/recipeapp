package com.example.androidproject;

import android.content.Context;
import android.database.Cursor;
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

    public Boolean deleteIngredient(Ingredients ingredients) {
    // deletes ingredients by id
        SQLiteDatabase database = this.getWritableDatabase();
        String queryString = "DELETE FROM " +
                INGREDIENTS_TABLE + " WHERE " +
                COLUMN_INGREDIENT_ID + " = " +
                ingredients.getId();
        Cursor cursor = database.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
    // deletes all ingredients with this recipeID
/*
    public Boolean deleteIngredients(int id){
        if(true) {
            return true;
        }
        return false;
    }
*/
    public ArrayList<Ingredients> getIngredients(Recipe recipe) {
        ArrayList<Ingredients> ingredients = new ArrayList<>();
        //ingredients.add(new recipe("name", "category", 1, Ingredients, List<Ingredients>));
        // code that gets ingredients
        String queryString = "SELECT * FROM " + INGREDIENTS_TABLE;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryString, null);


                if(cursor.moveToFirst()) {

                    do {
                        String ingredientName = cursor.getString(1);
                        int recipeID = cursor.getInt(0);
                        int Id = cursor.getInt(0);

                        Ingredients newIngredients = new Ingredients(ingredientName, recipeID, Id);
                        //Ingredients.add(newIngredients);
                    } while (cursor.moveToNext());

                }

        // close cursor and database, return recipes
        cursor.close();
        database.close();
        return ingredients;
    }

}
