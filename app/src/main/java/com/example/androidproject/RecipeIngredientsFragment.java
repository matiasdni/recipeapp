package com.example.androidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientsFragment extends Fragment {

    private Recipe recipe;
    Button btn_add;
    EditText et_ingredient;
    ListView lv_ingredients;
    ArrayAdapter listAdapter;
    private List<String> ingredients;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        FloatingActionButton button = requireActivity().findViewById(R.id.fab);
        btn_add = view.findViewById(R.id.btn_add);
        et_ingredient = view.findViewById(R.id.et_ingedient);
        lv_ingredients = view.findViewById(R.id.lv_ingredients);
        recipe = RecipeIngredientsFragmentArgs.fromBundle(getArguments()).getRecipe();
        ingredients = new ArrayList<>();

        // if getingredients is null user is creating a new recipe
        listAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, ingredients);
        lv_ingredients.setAdapter(listAdapter);

        Bundle bundle = new Bundle();
        if (bundle.get("ACTION") == "modifying") {
            Recipe recipe = bundle.getParcelable("recipe");

            // set list contents
            recipe.getIngredients();
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient;
                try {
                    ingredient = et_ingredient.getText().toString();
                    ingredients.add(ingredient);
                    Toast.makeText(getContext(), ingredient + " added.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error creating a customer", Toast.LENGTH_SHORT).show();
                }
                et_ingredient.getText().clear();
                updateIngredients(ingredients);
            }
        });

        button.setOnClickListener(view1 -> {
            if (recipe.getIngredients() == null) {
                // error handling
            } else {
                NavDirections action = RecipeIngredientsFragmentDirections
                        .actionRecipeIngredientsFragmentToRecipeInstructionsFragment(recipe);
                navController.navigate(action);
            }
        });

        lv_ingredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickedItem = (String) adapterView.getItemAtPosition(i);
                ingredients.remove(clickedItem);
                updateIngredients(ingredients);
                Toast.makeText(getContext(), clickedItem + " deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateIngredients(List<String> ingredients) {
        listAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, ingredients);
        lv_ingredients.setAdapter(listAdapter);
    }
}