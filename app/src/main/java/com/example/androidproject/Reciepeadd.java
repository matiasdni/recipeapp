package com.example.androidproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;
import androidx.annotation.NonNull;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reciepeadd extends AppCompatActivity {

    EditText recipeName, recipeCategory;
    Button button_saveRecipe, button_takeImage, button_loadImage;
    SwitchMaterial isFavorite;
    ImageView image_recipe;
    DBHelperSingleton dbHelperSingleton;
    ActivityResultLauncher<Intent> takeImageResultLauncher, loadImageResultLauncher;


    // for identifying if user takes or loads image
    static final boolean IMAGE_CAPTURE = true;
    boolean imageAction = false;
    String currentimagePath;
    Recipe newRecipe = new Recipe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciepeadd);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // references to components
        recipeName = findViewById(R.id.recipeName);
        recipeCategory = findViewById(R.id.recipeCategory);
        button_saveRecipe = findViewById(R.id.button_saveRecipe);
        button_loadImage = findViewById(R.id.button_load);
        button_takeImage = findViewById(R.id.button_take);
        image_recipe = findViewById(R.id.image_recipe);
        isFavorite = findViewById(R.id.isFavorite);
        dbHelperSingleton = DBHelperSingleton.getInstance(this);

        takeImageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {




                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Glide.with(Reciepeadd.this).load(currentimagePath).centerCrop().into(image_recipe);
                            Toast.makeText(Reciepeadd.this, "Success", Toast.LENGTH_SHORT).show();
                            newRecipe.setImagePath(currentimagePath);
                            addImageToGallery();
                        }
                    }
                }
        );

        loadImageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK) {
                            if (result.getData() != null) {
                                Uri selection = result.getData().getData();
                                Glide.with(Reciepeadd.this).load(selection).centerCrop().into(image_recipe);
                                newRecipe.setImagePath(selection.getPath());
                            }
                        }
                    }
                }
        );

        // onclick listeners
        button_takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeImageForResult();
            }
        });

        button_loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                loadImageResultLauncher.launch(intent);
            }
        });

        button_saveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recipeName.getText().toString().length() > 0 && recipeCategory.getText().toString().length() > 0) {
                    // get inputs from text fields
                    finish();
                } else {
                    Toast.makeText(Reciepeadd.this, "Enter all required data", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    public void takeImageForResult(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null) {
            // create image file
            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (IOException exception) {
                Toast.makeText(this, "ERROR ON IMAGE CREATION", Toast.LENGTH_SHORT)
                        .show();
            }
            // Image creation success
            if(imageFile != null) {
                Uri imageURI = FileProvider.getUriForFile(this,
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
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        // assign image path to variable and return image file
        currentimagePath = image.getAbsolutePath();
        return image;
    }

    private void addImageToGallery() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File image = new File(currentimagePath);
        Uri contentUri = Uri.fromFile(image);
        intent.setData(contentUri);
        this.sendBroadcast(intent);
    }




    @Override
    public void finish() {
        if (recipeName.getText().toString().length() > 0 && recipeCategory.getText().toString().length() > 0){
            newRecipe.setName(recipeName.getText().toString());
            newRecipe.setCategory(recipeCategory.getText().toString());
            newRecipe.setFavorite(isFavorite.isChecked());
            // save new recipe to database and update recyclerview
            dbHelperSingleton.addRecipe(newRecipe);

            // get ID from database to recipe
            newRecipe.setId(dbHelperSingleton.getRecipeID(newRecipe));

            Intent returnIntent = new Intent();
            returnIntent.putExtra("new_recipe", newRecipe);
            setResult(RESULT_OK, returnIntent);
            super.finish();
        }
    }

}


