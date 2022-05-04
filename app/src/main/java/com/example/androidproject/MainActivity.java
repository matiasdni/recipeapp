package com.example.androidproject;

import static com.example.androidproject.R.menu.example_menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String ACTION_NEW = "add_new";
    private static final String ACTION_MODIFY = "modify";

    // references to components
    private static RecyclerView recipesView;
    DBHelperSingleton dbHelperSingleton;
    FloatingActionButton button_recipeAdd;

    static final int ADD_NEW_RECIPE = 1;
    static ActivityResultLauncher<Intent> reciepeAddResultLauncher;
    RecipesViewAdapter recipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesView = findViewById(R.id.view_recipes);
        button_recipeAdd = findViewById(R.id.button_recipeAdd);
        dbHelperSingleton = DBHelperSingleton.getInstance(this);

        ArrayList<Recipe> recipes = new ArrayList<>(dbHelperSingleton.getRecipes());
        recipeAdapter = new RecipesViewAdapter(this, recipes);
        recipesView.setAdapter(recipeAdapter);
        recipesView.setLayoutManager(new LinearLayoutManager(this));

        reciepeAddResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                RecipesViewAdapter adapter = new RecipesViewAdapter(getApplicationContext(),recipes);

                                Recipe newRecipe = data.getParcelableExtra("new_recipe");
                                recipeAdapter.addRecipe(newRecipe);
                                recipesView.setAdapter(recipeAdapter);
                            }
                        }
                    }
                });

        // Onclick listener for add recipe button
        button_recipeAdd.setOnClickListener(view -> {
            reciepeAddForResult();
        });
    }

    public void reciepeAddForResult() {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(ACTION_NEW, 0);
        reciepeAddResultLauncher.launch(intent);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ADD_NEW_RECIPE && resultCode == RESULT_OK) {
//            //Recipe newRecipe = data.getExtras().get("new_recipe");
//            Recipe newRecipe = data.getParcelableExtra("new_recipe");
//            // deal with the item yourself
//            updateRecipesView(this);
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(example_menu, menu);
        return true;
    }

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