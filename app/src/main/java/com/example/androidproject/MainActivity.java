package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // references to components
    RecyclerView recipesView;
    DBHelper dbHelper;
    Button button_recipeAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipesView = findViewById(R.id.view_recipes);
        button_recipeAdd = findViewById(R.id.button_recipeAdd);

        dbHelper = new DBHelper(MainActivity.this);

        ArrayList<Recipe> recipes = new ArrayList<>(dbHelper.getRecipes());
        RecipesViewAdapter adapter = new RecipesViewAdapter(this);
        adapter.setRecipes(recipes);
        /* TODO:
            retrieve data from database to recipes */


        recipesView.setAdapter(adapter);
        recipesView.setLayoutManager(new LinearLayoutManager(this));

        // add test cases

        // Onclick listener for add recipe button

        button_recipeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(this, Reciepeadd.class);
                startActivity(intent);
            }
        });


    }
}