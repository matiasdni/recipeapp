package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // references to components
    RecyclerView recipesView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesView = findViewById(R.id.view_recipes);

        dbHelper = new DBHelper(MainActivity.this);

        ArrayList<Recipe> recipes = new ArrayList<>(dbHelper.getRecipes());
        RecipesViewAdapter adapter = new RecipesViewAdapter(this);
        adapter.setRecipes(recipes);
        /* TODO:
            retrieve data from database to recipes */


        recipesView.setAdapter(adapter);
        recipesView.setLayoutManager(new LinearLayoutManager(this));

        // add test cases



    }
}