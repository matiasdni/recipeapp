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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RecipeInstructionsFragment extends Fragment {

    private Recipe recipe;
    DBHelperSingleton dbHelperSingleton;
    Button btn_add;
    EditText et_instruction;
    ListView lv_instructions;
    ArrayAdapter listAdapter;
    private List<String> instructions;
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
        btn_add = view.findViewById(R.id.btn_add);
        et_instruction = view.findViewById(R.id.et_ingedient);
        lv_instructions = view.findViewById(R.id.lv_ingredients);
        recipe = RecipeIngredientsFragmentArgs.fromBundle(getArguments()).getRecipe();
        instructions = new ArrayList<>();

        listAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, instructions);
        lv_instructions.setAdapter(listAdapter);

        button.setOnClickListener(v -> {
            if(!instructions.isEmpty()){
                //recipe.setInstructions(instructions);
                DBHelperSingleton.getInstance(getContext()).addRecipe(recipe);
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), "Please enter at least one instruction", Toast.LENGTH_SHORT).show();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient;
                try {
                    ingredient = et_instruction.getText().toString();
                    instructions.add(ingredient);
                    Toast.makeText(getContext(), ingredient + " added.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error creating a customer", Toast.LENGTH_SHORT).show();
                }
                et_instruction.getText().clear();
                updateIngredients(instructions);
            }
        });

        lv_instructions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clickedItem = (String) adapterView.getItemAtPosition(i);
                instructions.remove(clickedItem);
                updateIngredients(instructions);
                Toast.makeText(getContext(), clickedItem + " deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateIngredients(List<String> ingredients) {
        listAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, ingredients);
        lv_instructions.setAdapter(listAdapter);
    }
}