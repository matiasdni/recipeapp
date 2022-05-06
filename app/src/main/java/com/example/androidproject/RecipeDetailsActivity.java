package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class RecipeDetailsActivity extends AppCompatActivity implements RecipeInstructionsFragment.OnRecipePass {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;

    String currentimagePath;
    Recipe newRecipe = new Recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        fab = findViewById(R.id.fab);
        tabLayout = findViewById(R.id.tabs);

    }

    @Override
    public void finish() {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("new_recipe", newRecipe);
            setResult(RESULT_OK, returnIntent);
            super.finish();
    }

    @Override
    public void onRecipePass(Recipe recipe) {
        newRecipe = recipe;
    }

}