package com.example.androidproject;

import static com.example.androidproject.R.menu.example_menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
    RecyclerView recipesView;
    DBHelper dbHelper;
    FloatingActionButton button_recipeAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesView = findViewById(R.id.view_recipes);
        button_recipeAdd = findViewById(R.id.button_recipeAdd);

        dbHelper = new DBHelper(MainActivity.this);
        DBHelperSingleton dbHelperSingleton = DBHelperSingleton.getInstance(MainActivity.this);

        ArrayList<Recipe> recipes = new ArrayList<>(dbHelperSingleton.getRecipes());
        RecipesViewAdapter adapter = new RecipesViewAdapter(this);
        adapter.setRecipes(recipes);
        recipesView.setAdapter(adapter);
        recipesView.setLayoutManager(new LinearLayoutManager(this));

        // add test cases

        // Onclick listener for add recipe button
        button_recipeAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Reciepeadd.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(example_menu, menu);
        return true;
    }
}