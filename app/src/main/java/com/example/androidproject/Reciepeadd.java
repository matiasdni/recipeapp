package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

// references


public class Reciepeadd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciepeadd);

        EditText recipeName = findViewById(R.id.recipeName);
        EditText recipeCategory = findViewById(R.id.recipeCategory);
        EditText recipeImagePath = findViewById(R.id.recipePhotoPath);
        Switch isFavorite = findViewById(R.id.isFavorite);
        Button button_saveRecipe = findViewById(R.id.button_saveRecipe);

        button_saveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recipeName.getText().toString().length() >0 && recipeCategory.getText().toString().length() > 0 && recipeImagePath.getText().toString().length() > 0) {
                    // get inputs from text fields
                    Recipe newRecipe = new Recipe();
                    newRecipe.setName(recipeName.getText().toString());
                    newRecipe.setCategory(recipeCategory.getText().toString());
                    newRecipe.setImagePath(recipeImagePath.getText().toString());
                    newRecipe.setFavorite(isFavorite.isChecked());


                    // save new recipe
                    DBHelperSingleton dbHelperSingleton = DBHelperSingleton.getInstance(Reciepeadd.this); // singleton
                    dbHelperSingleton.addRecipe(recipe);

                } else {
                    Toast.makeText(Reciepeadd.this, "Enter all required data", Toast.LENGTH_SHORT)
                            .show();
                }
            }


        }
    }