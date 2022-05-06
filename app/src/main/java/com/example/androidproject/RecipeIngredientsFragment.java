package com.example.androidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

/**
 * Fragment of RecipeDetailsActivity that is used for adding instructions to recipes.
 *
 * @author Matias Niemelä
 */
public class RecipeIngredientsFragment extends Fragment {

    Recipe recipe;
    Button btnAdd;
    EditText etIngredient;
    ListView listIngredients;
    ArrayAdapter<String> listAdapter;
    List<String> ingredients;

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
        btnAdd = view.findViewById(R.id.btn_add);
        etIngredient = view.findViewById(R.id.et_ingedient);
        listIngredients = view.findViewById(R.id.lv_ingredients);
        recipe = RecipeIngredientsFragmentArgs.fromBundle(getArguments()).getRecipe();
        ingredients = recipe.getIngredients();
        listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, ingredients);
        listIngredients.setAdapter(listAdapter);

        // add button listener, does input validation and updates view
        btnAdd.setOnClickListener(v -> {
            String ingredient;
            if (etIngredient.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Can't read your mind buddy", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    ingredient = etIngredient.getText().toString();
                    ingredients.add(ingredient);
                    Toast.makeText(getContext(), ingredient + " added.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "error on adding ingredient", Toast.LENGTH_SHORT)
                            .show();
                }
                etIngredient.getText().clear();
                updateIngredients(ingredients);
            }
        });

        // listener for plus button that takes the user to the next step, does input validation and changes fragment
        button.setOnClickListener(view1 -> {
            if (ingredients.isEmpty()) {
                Toast.makeText(getContext(), "Please add at least one ingredient", Toast.LENGTH_SHORT)
                        .show();
            } else {
                NavDirections action = RecipeIngredientsFragmentDirections
                        .actionRecipeIngredientsFragmentToRecipeInstructionsFragment(recipe);
                navController.navigate(action);
            }
        });

        // list listener for deleting ingredients by clicking them
        listIngredients.setOnItemClickListener((adapterView, view12, i, l) -> {
            String clickedItem = (String) adapterView.getItemAtPosition(i);
            ingredients.remove(clickedItem);
            updateIngredients(ingredients);
            Toast.makeText(getContext(), clickedItem + " deleted", Toast.LENGTH_SHORT).show();
        });
    }
    /**
     * updates list view
     */
    private void updateIngredients(List<String> ingredients) {
        listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, ingredients);
        listIngredients.setAdapter(listAdapter);
    }
}