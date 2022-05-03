package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeInfo extends AppCompatActivity {
    DBHelperSingleton dbHelperSingleton;
    EditText recipeName, recipeCategory;
    TextView ingredients;
    TextView instructions;
    ImageView image_recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        dbHelperSingleton = DBHelperSingleton.getInstance(this);
        recipeName = findViewById(R.id.recipeName);
        recipeCategory = findViewById(R.id.recipeCategory);
        image_recipe = findViewById(R.id.image_recipe);
        ingredients = findViewById(R.id.ingredients_text);
        instructions = findViewById(R.id.instructions_text);



        //Get the right picture
        //Right recipe name
        //Instructions for recipe
        //and ingredients
        //and display them in activity_recipe_info.xml
    }

//    public void setRecipeName(EditText recipeName) {
//        this.recipeName = recipeName;
//    }
}