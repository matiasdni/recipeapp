package com.example.androidproject;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * The singleton for interacting with database.
 * @author Matias Niemelä
 */
public class DBHelperSingleton {

    private final Context context;
    private final DBHelper dbHelper;
    private final DBHelperIngredients ingredientsHelper;
    private final DBHelperInstructions dbHelperInstructions;
    private static DBHelperSingleton ourInstance;

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
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

    /**
     * Add recipe to database.
     *
     * @param recipe the recipe to be added
     */
    public void addRecipe(Recipe recipe) {
        dbHelper.addRecipe(recipe);
        recipe.setId(dbHelper.getRecipeID(recipe));
        ingredientsHelper.addIngredients(recipe);
        dbHelperInstructions.addInstructions(recipe);
    }

    /**
     * Gets recipe id from database.
     *
     * @param recipe the recipe which id needs to be retrieved
     * @return the recipe id
     */
    public int getRecipeID(Recipe recipe) {
        return dbHelper.getRecipeID(recipe);
    }

    /**
     * Deletes recipe from database.
     *
     * @param recipe the recipe to be deleted
     */
    public void deleteRecipe(Recipe recipe) {
        dbHelper.deleteRecipe(recipe);
        dbHelperInstructions.deleteInstructions(recipe);
        ingredientsHelper.deleteIngredients(recipe);
        Toast.makeText(context, "Recipe deleted", Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets recipes from database.
     *
     * @return all recipes in database
     */
    public List<Recipe> getRecipes() {
        try {
            ArrayList<Recipe> recipes = (ArrayList<Recipe>) dbHelper.getRecipes();
            int i = 0;
            do {
                recipes.get(i).setIngredients(ingredientsHelper.getIngredients(recipes.get(i)));
                recipes.get(i).setInstructions(dbHelperInstructions.getInstructions(recipes.get(i)));
                ++i;
            } while (i < recipes.size());
            return recipes;
        } catch (IndexOutOfBoundsException i) {
            return dbHelper.getRecipes();
        }
    }
}