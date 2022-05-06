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
public class DBHelperInstructions extends SQLiteOpenHelper {

    /**
     * The constant INSTRUCTIONS_TABLE.
     */
    public static final String INSTRUCTIONS_TABLE = "INSTRUCTION_TABLE";
    /**
     * The constant COLUMN_INSTRUCTION_NAME.
     */
    public static final String COLUMN_INSTRUCTION_NAME = "INSTRUCTION_NAME";
    /**
     * The constant COLUMN_RECIPE_ID.
     */
    public static final String COLUMN_RECIPE_ID = "RECIPE_ID";
    /**
     * The constant COLUMN_INSTRUCTION_ID.
     */
    public static final String COLUMN_INSTRUCTION_ID = "ID";

    /**
     * Instantiates a new Db helper instructions.
     *
     * @param context the context
     */
    public DBHelperInstructions(@Nullable Context context) {
        super(context, "instructions.db", null, 1);
    }

    /**
     * create database, called when accessing database for the first time
     * @param database the database
     */
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
        // no need to implement at this time
    }

    /**
     * Delete instructions of a recipe.
     *
     * @param recipe the recipe to be deleted
     */
    public void deleteInstructions(Recipe recipe) {
        // deletes instructions by id
        SQLiteDatabase database = this.getWritableDatabase();
        String queryString = "DELETE FROM " +
                INSTRUCTIONS_TABLE + " WHERE " +
                COLUMN_RECIPE_ID + " = " +
                recipe.getId();
        Cursor cursor = database.rawQuery(queryString, null);
        cursor.moveToFirst();
    }

    /**
     * Gets instructions.
     *
     * @param recipeId the recipe id
     * @return the instructions list
     */
    public List<String> getInstructions(int recipeId) {
        ArrayList<String> ingredientsList = new ArrayList<>();

        String queryString = "SELECT * FROM " +
                INSTRUCTIONS_TABLE + " WHERE " +
                COLUMN_INSTRUCTION_ID + " = " + recipeId;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(2) == recipeId) {
                    ingredientsList.add(cursor.getString(1));
                }
            } while (cursor.moveToNext());
        }

        // close cursor and database, return recipes
        cursor.close();
        database.close();
        return ingredientsList;
    }

    /**
     * Add instructions.
     *
     * @param recipe the recipe to be added to database
     */
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