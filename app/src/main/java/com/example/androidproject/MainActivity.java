package com.example.androidproject;

import static com.example.androidproject.R.menu.example_menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Main activity.
 *
 * @author Matias Niemelä, Ella Sigvart, Kim Rautiainen, Leo Koskimäki
 * @version 4 /2022
 */
public class MainActivity extends AppCompatActivity {

    private static final String ACTION_NEW = "add_new";
    private static final String ACTION_MODIFY = "modify";

    /**
     * The Recipes recycler view.
     */
    RecyclerView recipesView;
    /**
     * The database helper singleton.
     */
    DBHelperSingleton dbHelperSingleton;
    /**
     * The plus button for adding recipe.
     */
    FloatingActionButton buttonRecipeAdd;
    /**
     * The Recipe recycler view adapter.
     */
    RecipesViewAdapter recipeAdapter;
    /**
     * The Recipe add result launcher.
     */
    static ActivityResultLauncher<Intent> recipeAddResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipesView = findViewById(R.id.view_recipes);
        buttonRecipeAdd = findViewById(R.id.button_recipeAdd);
        dbHelperSingleton = DBHelperSingleton.getInstance(this);

        // initialize recycler view
        ArrayList<Recipe> recipes = new ArrayList<>(dbHelperSingleton.getRecipes());
        recipeAdapter = new RecipesViewAdapter(this, recipes);
        recipesView.setAdapter(recipeAdapter);
        recipesView.setLayoutManager(new LinearLayoutManager(this));

        recipeAddResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Recipe newRecipe = data.getParcelableExtra("new_recipe");
                            recipeAdapter.addRecipe(dbHelperSingleton);
                            recipesView.smoothScrollToPosition(recipeAdapter.getItemCount());
                        }
                    }
                });

        // Onclick listener for add recipe button
        buttonRecipeAdd.setOnClickListener(view -> reciepeAddForResult());
    }

    /**
     * Reciepe add for result.
     */
    public void reciepeAddForResult() {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(ACTION_NEW, 0);
        recipeAddResultLauncher.launch(intent);
    }

    // creating menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(example_menu, menu);
        return true;
    }

    // using menu options and opening them
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item3) {
            Intent intent = new Intent(this, MyFavorites.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.item2) {
            Intent intent = new Intent(this, MyRecipes.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}