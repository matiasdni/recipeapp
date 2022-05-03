package com.example.androidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecipeIngredientsFragment extends Fragment {

    private Recipe recipe;

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
        recipe = RecipeIngredientsFragmentArgs.fromBundle(getArguments()).getRecipe();

        Bundle bundle = new Bundle();
        if(bundle.get("ACTION") == "modifying") {
            Recipe recipe = bundle.getParcelable("recipe");

            // set list contents
            recipe.getIngredients();
        }

        button.setOnClickListener(view1 -> {
            if(recipe.getIngredients() == null) {
                // error handling
            } else {
                NavDirections action = RecipeIngredientsFragmentDirections
                        .actionRecipeIngredientsFragmentToRecipeInstructionsFragment(recipe);
                navController.navigate(action);
            }
        });
    }
}