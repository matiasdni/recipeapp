package com.example.androidproject;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
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

/**
 * Fragment of RecipeDetailsActivity that is used for adding name, category and image to recipes.
 *
 * @author Matias Niemelä
 */
public class RecipeDetailsFragment extends Fragment {

    /**
     * The Recipe name text field.
     */
    EditText recipeName;
    /**
     * The Recipe category text field.
     */
    EditText recipeCategory;
    /**
     * Take image button.
     */
    Button buttonTakeImage;
    /**
     * Load image button.
     */
    Button buttonLoadImage;
    /**
     * The Favorite switch.
     */
    SwitchMaterial favorite;
    /**
     * The Recipe image.
     */
    ImageView recipeImage;
    /**
     * Floating action button which takes the user to the next fragment.
     */
    FloatingActionButton fButton;
    /**
     * The navigation controller.
     */
    NavController navController;
    /**
     * Take image for result launcher.
     */
    ActivityResultLauncher<Intent> takeImageResultLauncher;
    /**
     * Load image for result launcher.
     */
    ActivityResultLauncher<Intent> loadImageResultLauncher;

    /**
     * The Currentimage path.
     */
    String currentImagePath;
    /**
     * The Recipe.
     */
    Recipe recipe = new Recipe();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        buttonLoadImage = view.findViewById(R.id.button_load);
        buttonTakeImage = view.findViewById(R.id.button_take);

        loadImageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selection = result.getData().getData();
                        Glide.with(requireActivity()).load(selection).centerCrop().into(recipeImage);
                        recipe.setImagePath(selection.getPath());
                    }
                }
        );

        Bundle bundle = new Bundle();
        if (bundle.get("ACTION") == "modifying") {
            recipe = bundle.getParcelable("recipe");
            recipeName.setText(recipe.getName());
            recipeCategory.setText(recipe.getCategory());
            Glide.with(view).load(recipe.getImagePath())
                    .centerCrop()
                    .into(recipeImage);
        }

        fButton.setOnClickListener(
                view1 -> {
                    if (recipeName.getText().toString().isEmpty() && recipeCategory.getText().toString().isEmpty()) {
                        Toast.makeText(getContext(), "Please enter name and category", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        recipe.setName(recipeName.getText().toString());
                        recipe.setCategory(recipeCategory.getText().toString());
                        recipe.setFavorite(favorite.isChecked());
                        NavDirections action =
                                RecipeDetailsFragmentDirections
                                        .actionRecipeDetailsFragmentToRecipeIngredientsFragment(recipe);
                        navController.navigate(action);
                    }
                });

        takeImageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Glide.with(requireActivity()).load(currentImagePath).centerCrop().into(recipeImage);
                        Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show();
                        recipe.setImagePath(currentImagePath);
                    }
                }
        );

        // Onclick listeners
        buttonTakeImage.setOnClickListener(view12 -> takeImageForResult());

        buttonLoadImage.setOnClickListener(view13 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            loadImageResultLauncher.launch(intent);
        });

    }

    /**
     * Take image for result activity.
     * Gets image file from createImageFile() method and passes it's URI to image capture activity.
     */
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
                Uri imageUri = FileProvider.getUriForFile(requireActivity(),
                        "com.example.androidproject.fileprovider", imageFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                takeImageResultLauncher.launch(intent);
            }
        }
    }

    /**
     * Create image file for storing recipe image.
     *
     * @return image file
     * @throws IOException the io exception
     */
    public File createImageFile() throws IOException {
        // Uses date to get unique image file name
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "RECIPE_" + timeStamp + "_";
        File imageDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                imageDirectory
        );
        // store current image path to class variable
        currentImagePath = image.getAbsolutePath();
        return image;
    }
}
