package com.example.androidproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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
                "CREATE TABLE " +   INSTRUCTIONS_TABLE + " (" +
                        COLUMN_INSTRUCTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_INSTRUCTION_NAME + " TEXT, " +
                        COLUMN_RECIPE_ID + " INTEGER)";
        database.execSQL(createTableStatement);
    }

    // called when database version changes, prevents app breaking due to database design change
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    // This method is called when a new recipe has been ceated and we need to link instruction
    // to correct recipe id
    public boolean addInstruction(Instructions instructions) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_RECIPE_ID, instructions.getRecipeId());
        contentValues.put(COLUMN_INSTRUCTION_NAME, instructions.getBody());

        long insert = database.insert(INSTRUCTIONS_TABLE, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Instructions> getInstructions(Recipe recipe) {
        ArrayList<Instructions> instructions = new ArrayList<>();

        // code that gets all instructions

        return instructions;
    }

}
