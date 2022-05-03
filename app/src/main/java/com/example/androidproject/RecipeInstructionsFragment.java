package com.example.androidproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecipeInstructionsFragment extends Fragment {

    private Recipe recipe;
    DBHelperSingleton dbHelperSingleton;
    private static final String TAG_ING = "Ingredient";
    private static final String TAG_INS = "Instruction";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_instructions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton button = requireActivity().findViewById(R.id.fab);
        recipe = RecipeInstructionsFragmentArgs.fromBundle(getArguments()).getRecipe();

        button.setOnClickListener(v -> {
            if(recipe.getInstructions() != null){
                dbHelperSingleton.addRecipe(recipe);
                requireActivity().finish();
            }
            Toast.makeText(getContext(), "Please enter at least one instruction", Toast.LENGTH_SHORT).show();
        });

    }
}