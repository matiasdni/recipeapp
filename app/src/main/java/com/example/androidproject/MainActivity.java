package com.example.androidproject;

import static com.example.androidproject.R.menu.example_menu;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // references to components
    private static RecyclerView recipesView;
    DBHelperSingleton dbHelperSingleton;
    FloatingActionButton button_recipeAdd;

    static final int ADD_NEW_RECIPE = 1;
    ActivityResultLauncher<Intent> reciepeAddResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesView = findViewById(R.id.view_recipes);
        button_recipeAdd = findViewById(R.id.button_recipeAdd);
        dbHelperSingleton = DBHelperSingleton.getInstance(this);

        ArrayList<Recipe> recipes = new ArrayList<>(dbHelperSingleton.getRecipes());
        RecipesViewAdapter recipeAdapter = new RecipesViewAdapter(this, recipes);
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
                                Recipe newRecipe = data.getParcelableExtra("new_recipe");
                                recipeAdapter.addRecipe(newRecipe);
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
        Intent intent = new Intent(this, Reciepeadd.class);
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
}