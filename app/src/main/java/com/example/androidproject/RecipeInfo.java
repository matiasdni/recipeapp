package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecipeInfo extends AppCompatActivity {
    DBHelper dbHelper;
    DBHelperSingleton dbHelperSingleton;
    DBHelperIngredients dbHelperIngredients;
    TextView recipeName, recipeCategory;
    TextView ingredients;
    TextView instructions;
    ImageView image_recipe;
    private static RecyclerView rving;
    private static RecyclerView rvinst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        image_recipe = (ImageView) findViewById(R.id.image_recipe);
        recipeName = (TextView) findViewById(R.id.txt_name2);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int resName = bundle.getInt("resName");
            recipeName.setText(resName);
            int resId = bundle.getInt("resId");
            image_recipe.setImageResource(resId);
        }

/*TODO:
        Get the right picture, recipe name.
        Instructions and ingredients for recipe.
        Display them in activity_recipe_info.xml*/

        rvinst = findViewById(R.id.rvinstructions);
        rving = findViewById(R.id.rvingredients);
        dbHelperSingleton = DBHelperSingleton.getInstance(this);

        //ArrayList<Instructions> instructions = new ArrayList<>(dbHelperSingleton.getInstructions());


        //ArrayList<Ingredients> ingredients = new ArrayList<>(dbHelperSingleton.getIngredients());
        //RecipesViewAdapter recipeAdapter = new RecipesViewAdapter(this, ingredients);
        //rving.setAdapter(recipeAdapter);
        //rving.setLayoutManager(new LinearLayoutManager(this));





    }
}