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
 /*TODO:
        new column for image path (done), ingredients and instructions
 *      Figure out how to store ingredients and instructions in the database
        figure out how to read the data to classes (done)
        Implement database to main program
        addRecipe, onUpgrade, deleteRecipe (done)*/

public class DBHelperInstructions extends SQLiteOpenHelper {

    // instructions.db config
    public static final String INSTRUCTIONS_TABLE = "INSTRUCTION_TABLE";
    public static final String COLUMN_INSTRUCTION_NAME = "INSTRUCTION_NAME";
    public static final String COLUMN_RECIPE_ID = "RECIPE_ID";
    public static final String COLUMN_INSTRUCTION_ID = "ID";

    public DBHelperInstructions(@Nullable Context context) {
        super(context, "instructions.db", null, 1);
    }

    // create database, called when accessing database for the first time
    @Override
    public void onCreate(SQLiteDatabase database) {
        String createTableStatement =
                "CREATE TABLE " + INSTRUCTIONS_TABLE + " (" +
                        COLUMN_INSTRUCTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_INSTRUCTION_NAME + " TEXT, " +
                        COLUMN_RECIPE_ID + " INTEGER)";
        database.execSQL(createTableStatement);
    }

    // called when database version changes, prevents app breaking due to database design change
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean deleteInstructions(Recipe recipe) {
        // deletes instructions by id
        SQLiteDatabase database = this.getWritableDatabase();
        String queryString = "DELETE FROM " +
                INSTRUCTIONS_TABLE + " WHERE " +
                COLUMN_RECIPE_ID + " = " +
                recipe.getId();
        Cursor cursor = database.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }

    public List<String> getInstructions(int recipe) {
        ArrayList<String> ingredientsList = new ArrayList<>();

        String queryString = "SELECT * FROM " +
                INSTRUCTIONS_TABLE + " WHERE " +
                COLUMN_INSTRUCTION_ID + " = " + recipe;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                if(cursor.getInt(2) == recipe)
                    ingredientsList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // close cursor and database, return recipes
        cursor.close();
        database.close();
        return ingredientsList;
    }

    public void addInstructions(Recipe recipe) {
        SQLiteDatabase database = this.getWritableDatabase();
        int i = 0;
        do {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_INSTRUCTION_NAME, recipe.getIngredients().get(i));
            contentValues.put(COLUMN_RECIPE_ID, recipe.getId());
            database.insert(INSTRUCTIONS_TABLE, null, contentValues);
            ++i;
        } while (i < recipe.getInstructions().size());
    }
}