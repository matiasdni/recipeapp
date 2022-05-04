package com.example.androidproject;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecipeDetailsFragment extends Fragment {

    EditText recipeName, recipeCategory;
    Button button_takeImage, button_loadImage;
    SwitchMaterial favorite;
    ImageView recipeImage;
    FloatingActionButton fButton;
    NavController navController;
    ActivityResultLauncher<Intent> takeImageResultLauncher, loadImageResultLauncher;

    String currentimagePath;
    Recipe recipe = new Recipe();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        fButton = requireActivity().findViewById(R.id.fab);
        recipeName = view.findViewById(R.id.recipeName);
        recipeCategory = view.findViewById(R.id.recipeCategory);
        recipeImage = view.findViewById(R.id.image_recipe);
        favorite = view.findViewById(R.id.isFavorite);
        button_loadImage = view.findViewById(R.id.button_load);
        button_takeImage = view.findViewById(R.id.button_take);
        loadImageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            Uri selection = result.getData().getData();
                            Glide.with(requireActivity()).load(selection).centerCrop().into(recipeImage);
                            recipe.setImagePath(selection.getPath());
                        }
                    }
                }
        );

        Bundle bundle = new Bundle();
        if(bundle.get("ACTION") == "modifying") {
            recipe = bundle.getParcelable("recipe");
            recipeName.setText(recipe.getName());
            recipeCategory.setText(recipe.getCategory());
            Glide.with(view).load(recipe.getImagePath())
                    .centerCrop()
                    .into(recipeImage);
        }

        fButton.setOnClickListener(
                view1 -> {
                    recipe.setName(recipeName.getText().toString());
                    recipe.setCategory(recipeCategory.getText().toString());
                    // todo: implement glide etc
                    // recipe.setImagePath();
                    recipe.setFavorite(favorite.isChecked());
                    NavDirections action =
                            RecipeDetailsFragmentDirections
                                    .actionRecipeDetailsFragmentToRecipeIngredientsFragment(recipe);
                    navController.navigate(action);
                });

        takeImageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Glide.with(requireActivity()).load(currentimagePath).centerCrop().into(recipeImage);
                        Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show();
                        recipe.setImagePath(currentimagePath);
                    }
                }
        );


//
//        // onclick listeners
        button_takeImage.setOnClickListener(view12 -> takeImageForResult());

        button_loadImage.setOnClickListener(view13 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            loadImageResultLauncher.launch(intent);
        });

    }

    public void takeImageForResult() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // create image file
            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (IOException exception) {
                Toast.makeText(getActivity(), "ERROR ON IMAGE CREATION", Toast.LENGTH_SHORT)
                        .show();
            }
            // Image creation success
            if (imageFile != null) {
                Uri imageURI = FileProvider.getUriForFile(requireActivity(),
                        "com.example.androidproject.fileprovider", imageFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
                takeImageResultLauncher.launch(intent);
            }
        }
    }

    private File createImageFile() throws IOException {
        // unique image filename
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        // assign image path to variable and return image file
        currentimagePath = image.getAbsolutePath();
        return image;
    }
}
