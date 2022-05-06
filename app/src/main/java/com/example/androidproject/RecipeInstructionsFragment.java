package com.example.androidproject;

import android.content.Context;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * Fragment of RecipeDetailsActivity that is used for adding instructions to recipes.
 *
 * @author Matias Niemelä
 */
public class RecipeInstructionsFragment extends Fragment {

    /**
     * The Recipe the user is creating.
     */
    Recipe recipe;
    /**
     * The add button that is used for adding new instructions to the recipe.
     */
    Button btnAdd;
    /**
     * The floating action button used for going to next fragment or finishing activity.
     */
    FloatingActionButton fButton;
    /**
     * The Edit Text  field for instructions.
     */
    EditText etInstruction;
    /**
     * The instructions list that contains all the added instructions.
     */
    ListView listInstructions;
    /**
     * The List adapter.
     */
    ArrayAdapter<String> listAdapter;
    /**
     * The Instructions for storing all the added instructions.
     */
    List<String> instructions;
    /**
     * The Recipe passer for passing the recipe to the main activity.
     */
    OnRecipePass recipePasser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_instructions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fButton = requireActivity().findViewById(R.id.fab);
        btnAdd = view.findViewById(R.id.btn_add);
        etInstruction = view.findViewById(R.id.et_ingedient);
        listInstructions = view.findViewById(R.id.lv_ingredients);
        recipe = RecipeIngredientsFragmentArgs.fromBundle(getArguments()).getRecipe();
        instructions = recipe.getInstructions();

        listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, instructions);
        listInstructions.setAdapter(listAdapter);

        fButton.setOnClickListener(v -> {
            if (!instructions.isEmpty()) {
                recipe.setInstructions(instructions);
                // pass the recipe to the activity
                recipePasser.onRecipePass(recipe);
                DBHelperSingleton.getInstance(getContext()).addRecipe(recipe);
                requireActivity().finish();
            } else {
                Toast.makeText(getContext(), "Please enter at least one instruction", Toast.LENGTH_SHORT).show();
            }
        });
        // does error handling, user input validation and adds new ingredient to list view
        btnAdd.setOnClickListener(v -> {
            String newIngredient;
            if (etInstruction.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Can't read your mind buddy", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    newIngredient = etInstruction.getText().toString();
                    instructions.add(newIngredient);
                    Toast.makeText(getContext(), newIngredient + " added.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error creating a customer", Toast.LENGTH_SHORT).show();
                }
                etInstruction.getText().clear();
                updateInstructionList(instructions);
            }
        });

        listInstructions.setOnItemClickListener((adapterView, view1, i, l) -> {
            String clickedItem = (String) adapterView.getItemAtPosition(i);
            instructions.remove(clickedItem);
            updateInstructionList(instructions);
            Toast.makeText(getContext(), clickedItem + " deleted", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * updates list view
     *
     * @param ingredients the data set to the list
     */
    private void updateInstructionList(List<String> ingredients) {
        listAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, ingredients);
        listInstructions.setAdapter(listAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        recipePasser = (OnRecipePass) context;
    }

    /**
     * interface for passing recipe to activity
     */
    public interface OnRecipePass {
        /**
         * On recipe pass.
         *
         * @param recipe the recipe passed to the activity
         */
        void onRecipePass(Recipe recipe);
    }
}