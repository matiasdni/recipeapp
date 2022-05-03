package com.example.androidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class RecipeDetailsFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        FloatingActionButton fButton = requireActivity().findViewById(R.id.fab);
        EditText recipeName = requireActivity().findViewById(R.id.recipeName);
        EditText recipeCategory = requireActivity().findViewById(R.id.recipeCategory);
        ImageView recipeImage = requireActivity().findViewById(R.id.image_recipe);
        SwitchMaterial favorite = requireActivity().findViewById(R.id.isFavorite);

        fButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Recipe recipe = new Recipe();
                        recipe.setName(recipeName.getText().toString());
                        recipe.setCategory(recipeCategory.getText().toString());
                        // todo: implement glide etc
                        // recipe.setImagePath();
                        recipe.setFavorite(favorite.isChecked());
                        NavDirections action =
                                RecipeDetailsFragmentDirections
                                        .actionRecipeDetailsFragmentToRecipeIngredientsFragment(recipe);
                        navController.navigate(action);
                    }
                });
    }
}
