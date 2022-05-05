package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
//        tabLayout = findViewById(R.id.tabs);
//        viewPager = findViewById(R.id.view_pager);
//
//        tabLayout.setupWithViewPager(viewPager);
//
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
//                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        viewPagerAdapter.addFragment(new RecipeDetailsFragment(), "RECIPE");
//        viewPagerAdapter.addFragment(new RecipeIngredientsFragment(), "INGREDIENTS");
//        viewPagerAdapter.addFragment(new RecipeInstructionsFragment(), "INSTRUCTIONS");
//        viewPager.setAdapter(viewPagerAdapter);


        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
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