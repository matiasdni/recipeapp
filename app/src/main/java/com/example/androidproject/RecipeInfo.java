package com.example.androidproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * @author Leo and Matias
 */
public class RecipeInfo extends AppCompatActivity {
    TextView recipeName;
    TextView ingredients;
    TextView instructions;
    ImageView imageRecipe;
    ListView listIngredients;
    ListView listInstructions;
    ArrayAdapter<String> ingredientsAdapter;
    ArrayAdapter<String> instructionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        imageRecipe = findViewById(R.id.image_recipe);
        recipeName = findViewById(R.id.txt_name2);
        ingredients = findViewById(R.id.ingredients_text);
        instructions = findViewById(R.id.instructions_text);
        listIngredients = findViewById(R.id.rvingredients);
        listInstructions = findViewById(R.id.rvinstructions);
        initUI(getIntent().getParcelableExtra("recipe"));
    }

    public void initUI(Recipe recipe) {
        Glide.with(this).asBitmap().load(recipe.getImagePath()).fitCenter().into(imageRecipe);
        recipeName.setText(recipe.getName());
        ArrayList<String> ingredients = (ArrayList<String>) recipe.getIngredients();
        ArrayList<String> instructions = (ArrayList<String>) recipe.getInstructions();
        ingredientsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredients);
        instructionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, instructions);
        listIngredients.setAdapter(ingredientsAdapter);
        listInstructions.setAdapter(instructionsAdapter);
    }
}