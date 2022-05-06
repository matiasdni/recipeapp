package com.example.androidproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom adapter for recycler view that shows recipes in main activity.
 *
 * @author Matias Niemelä
 */
public class RecipesViewAdapter extends RecyclerView.Adapter<RecipesViewAdapter.ViewHolder> {
    private ArrayList<Recipe> recipes;
    private final Context context;
    /**
     * The Reciepe modify result launcher.
     */
    ActivityResultLauncher<Intent> reciepeModifyResultLauncher;

    /**
     * Instantiates a new Recipes view adapter.
     *
     * @param context the context
     * @param recipes the recipes to be added to the recycler view dataset
     */
    public RecipesViewAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = (ArrayList<Recipe>) recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // get recipe names and images to cards
        holder.txtRecipeName.setText(recipes.get(position).getName());
        Glide.with(context)
                .asBitmap()
                .load(recipes.get(position).getImagePath())
                .centerCrop()
                .into(holder.imageRecipe);

        // listen for card clicks and open recipe info activity
        holder.parent.setOnClickListener(view -> {
            Intent intent = new Intent(this.context, RecipeInfo.class);
            intent.putExtra("recipe", recipes.get(position));
            context.startActivity(intent);
            Toast.makeText(context, recipes.get(position).getName() + " Clicked", Toast.LENGTH_SHORT).show();
        });
        // listen for popup menu clicks
        holder.ibPopupMenu.setOnClickListener(view -> {
            // inflate popup menu and show it to the user
            PopupMenu popupMenu = new PopupMenu(context, holder.ibPopupMenu);
            popupMenu.inflate(R.menu.cardpopup_menu);
            popupMenu.show();
            // listen for popup menu clicks and response to it
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.menu_modify_recipe:
                        Intent intent = new Intent(context, RecipeDetailsActivity.class);
                        intent.putExtra("ACTION", "modify");
                        MainActivity.recipeAddResultLauncher.launch(intent);
                        break;
                    case R.id.menu_delete_recipe:
                        // delete recipe from database and recycler view
                        DBHelperSingleton.getInstance(view.getContext()).deleteRecipe(recipes.get(position));
                        recipes.remove(recipes.get(position));
                        notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                return false;
            });
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    /**
     * updates the recycler view dataset.
     *
     * @param dbHelperSingleton to update the list from database
     */
    public void addRecipe(DBHelperSingleton dbHelperSingleton) {
        recipes = (ArrayList<Recipe>) dbHelperSingleton.getRecipes();
        notifyDataSetChanged();
    }

    /**
     * The View holder.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtRecipeName;
        private final CardView parent;
        private final ImageView imageRecipe;
        private final ImageButton ibPopupMenu;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the card view
         */
        public ViewHolder(View itemView) {
            super(itemView);
            txtRecipeName = itemView.findViewById(R.id.txt_name);
            parent = itemView.findViewById(R.id.parent);
            imageRecipe = itemView.findViewById(R.id.image_recipe);
            ibPopupMenu = itemView.findViewById(R.id.ib_popup_menu);
        }
    }
}
