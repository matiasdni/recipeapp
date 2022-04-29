package com.example.androidproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


/* TODO: Create new activity for displaying the clicked recipe
*   and start the activity on onBindViewHolder() method when an item is clicked */

public class RecipesViewAdapter extends RecyclerView.Adapter<RecipesViewAdapter.ViewHolder> {
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private final Context context;

    public RecipesViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // get recipe name and thumbnail to cards
        holder.txtRecipeName.setText(recipes.get(position).getName());
        Glide.with(context)
                .asBitmap()
                .load(recipes.get(position).getImagePath())
                .into(holder.imageRecipe);
        // listen for card click and open recipe details activity
        holder.parent.setOnClickListener(view -> {
            /* TODO: Create new activity for displaying the clicked recipe */
            Toast.makeText(context, recipes.get(position).getName() + " Clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtRecipeName;
        private final CardView parent;
        private final ImageView imageRecipe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRecipeName = itemView.findViewById(R.id.txt_name);
            parent = itemView.findViewById(R.id.parent);
            imageRecipe = itemView.findViewById(R.id.image_recipe);
        }
    }
}
