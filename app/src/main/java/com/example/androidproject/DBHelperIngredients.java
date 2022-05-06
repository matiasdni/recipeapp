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
 * @author Matias Niemelä and Ella Sigvart
 * @version 1.0 4/2022
 */
public class DBHelperIngredients extends SQLiteOpenHelper {

    /**
     * The constant INGREDIENTS_TABLE.
     */
    public static final String INGREDIENTS_TABLE = "INGREDIENTS_TABLE";
    /**
     * The constant COLUMN_INGREDIENT_NAME.
     */
    public static final String COLUMN_INGREDIENT_NAME = "INGREDIENT_NAME";
    /**
     * The constant COLUMN_RECIPE_ID.
     */
    public static final String COLUMN_RECIPE_ID = "RECIPE_ID";
    /**
     * The constant COLUMN_INGREDIENT_ID.
     */
    public static final String COLUMN_INGREDIENT_ID = "ID";

    /**
     * Instantiates a new Db helper ingredients.
     *
     * @param context the context
     */
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
        // no need at this time
    }

    /**
     * Gets ingredients of a recipe.
     *
     * @param recipe the recipe which instructions need to be retrieved from the database
     * @return the ingredients
     */
    public List<String> getIngredients(Recipe recipe) {
        ArrayList<String> ingredients = new ArrayList<>();
        String queryString = "SELECT * FROM " + INGREDIENTS_TABLE;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryString, null);
        cursor.moveToFirst();
        do {
            if (cursor.getInt(2) == recipe.getId()) {
                ingredients.add(cursor.getString(1));
            }
        } while (cursor.moveToNext());


        // close cursor and database, return recipes
        cursor.close();
        database.close();
        return ingredients;
    }

    /**
     * Add recipe ingredients to database.
     *
     * @param recipe the recipe to be added to database
     */
    public void addIngredients(Recipe recipe) {
        SQLiteDatabase database = this.getWritableDatabase();
        int i = 0;
        do {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_INGREDIENT_NAME, recipe.getIngredients().get(i));
            contentValues.put(COLUMN_RECIPE_ID, recipe.getId());
            database.insert(INGREDIENTS_TABLE, null, contentValues);
            ++i;
        } while (i < recipe.getIngredients().size());
    }

    /**
     * Delete recipes ingredients from database.
     *
     * @param recipe the recipe to be deleted
     */
    public void deleteIngredients(Recipe recipe) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryString = "DELETE FROM " +
                INGREDIENTS_TABLE + " WHERE " +
                COLUMN_RECIPE_ID + " = " +
                recipe.getId();
        Cursor cursor = database.rawQuery(queryString, null);
        cursor.moveToFirst();
    }
}
