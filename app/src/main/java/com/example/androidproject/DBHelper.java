package com.example.androidproject;

import android.content.ContentValues;
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
public class DBHelper extends SQLiteOpenHelper {


    /**
     * The constant RECIPE_TABLE.
     */
    public static final String RECIPE_TABLE = "RECIPE_TABLE";
    /**
     * The constant COLUMN_RECIPE_NAME.
     */
    public static final String COLUMN_RECIPE_NAME = "RECIPE_NAME";
    /**
     * The constant COLUMN_RECIPE_CATEGORY.
     */
    public static final String COLUMN_RECIPE_CATEGORY = "RECIPE_CATEGORY";
    /**
     * The constant COLUMN_FAVORITE_RECIPE.
     */
    public static final String COLUMN_FAVORITE_RECIPE = "FAVORITE_RECIPE";
    /**
     * The constant COLUMN_RECIPE_ID.
     */
    public static final String COLUMN_RECIPE_ID = "ID";
    /**
     * The constant COLUMN_IMAGE_PATH.
     */
    public static final String COLUMN_IMAGE_PATH = "IMAGE_PATH";

    /**
     * Instantiates a DBHelper.
     *
     * @param context the context
     */
    public DBHelper(@Nullable Context context) {
        super(context, "recipes.db", null, 1);
    }

    /**
     * create database, called when accessing database for the first time
     * @param database the database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        String createTableStatement =
                "CREATE TABLE " + RECIPE_TABLE + " (" +
                        COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_RECIPE_NAME + " TEXT, " +
                        COLUMN_RECIPE_CATEGORY + " TEXT, " +
                        COLUMN_FAVORITE_RECIPE + " BOOL, " +
                        COLUMN_IMAGE_PATH + " TEXT)";
        database.execSQL(createTableStatement);
    }

    // called when database version changes, prevents app breaking due to database design change
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // no need at this time
    }

    /**
     * Add recipe to database.
     *
     * @param recipe the recipe to be added
     * @return the boolean
     */
    public boolean addRecipe(Recipe recipe) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_RECIPE_NAME, recipe.getName());
        contentValues.put(COLUMN_RECIPE_CATEGORY, recipe.getCategory());
        contentValues.put(COLUMN_FAVORITE_RECIPE, recipe.isFavorite());
        contentValues.put(COLUMN_IMAGE_PATH, recipe.getImagePath());

        long insert = database.insert(RECIPE_TABLE, null, contentValues);
        return insert != -1;
    }

    /**
     * Delete recipe from database.
     *
     * @param recipe the recipe to be deleted
     */
    public void deleteRecipe(Recipe recipe) {
        // deletes recipe by id
        SQLiteDatabase database = this.getWritableDatabase();
        String queryString = "DELETE FROM " +
                RECIPE_TABLE + " WHERE " +
                COLUMN_RECIPE_ID + " = " +
                recipe.getId();
        Cursor cursor = database.rawQuery(queryString, null);
        cursor.moveToFirst();
    }

    /**
     * Gets recipe id.
     *
     * @param recipe the recipe which id needs to be retrieved from database
     * @return the recipe id
     */
    public int getRecipeID(Recipe recipe) {
        SQLiteDatabase database = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + RECIPE_TABLE;
        Cursor cursor = database.rawQuery(queryString, null);
        int recipeID = 0;
        /*
        Since this method is mainly used when adding a new recipe, it is more efficient to
        start going through the database from last to start. Currently it looks for the same
        name and image path and it is enough to find the correct ID for now.
        */
        if (cursor.moveToLast()) {
            do {
                if (recipe.getName().equals(cursor.getString(1)) &&
                        recipe.getImagePath().equals(cursor.getString(4))) {
                    recipeID = cursor.getInt(0);
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        database.close();
        // returns 0 in case the method fails, shouldn't ever happen.
        return recipeID;
    }

    /**
     * Gets recipes from database.
     *
     * @return the recipes
     */
    public List<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        // retrieve data from database
        String queryString = "SELECT * FROM " + RECIPE_TABLE;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the database and create recipes
            do {
                int recipeID = cursor.getInt(0);
                String recipeName = cursor.getString(1);
                String category = cursor.getString(2);
                boolean favoriteRecipe = cursor.getInt(3) == 1;
                String imagePath = cursor.getString(4);
                Recipe newRecipe = new Recipe(recipeID, recipeName, category, favoriteRecipe, imagePath);
                recipes.add(newRecipe);
            } while (cursor.moveToNext());
        }

        // close cursor and database, return recipes
        cursor.close();
        database.close();
        return recipes;
    }
}