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

/* TODO: Create new activity for displaying the clicked recipe
 *   and start the activity on onBindViewHolder() method when an item is clicked */

public class RecipesViewAdapter extends RecyclerView.Adapter<RecipesViewAdapter.ViewHolder> {
    private ArrayList<Recipe> recipes;
    private Context context;
    private DBHelperSingleton dbHelperSingleton;
    ActivityResultLauncher<Intent> reciepeModifyResultLauncher;

    // initialize the dataset of the adapter
    public RecipesViewAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // get recipe name and thumbnail to cards
        holder.txtRecipeName.setText(recipes.get(position).getName());
        Glide.with(context)
                .asBitmap()
                .load(recipes.get(position).getImagePath())
                .centerCrop()
                .into(holder.imageRecipe);
        // listen for card click and open recipe details activity
        holder.parent.setOnClickListener(view -> {
            Intent intent = new Intent(this.context, RecipeInfo.class);
            /*TODO  getting the recipe name and image to RecipeInfo (Not working correctly) */
            intent.putExtra("resId", R.drawable.ic_image_placeholder);
            intent.putExtra("resName", R.id.txt_name);
            context.startActivity(intent);
            /* TODO: Create new activity for displaying the clicked recipe (Done) */
            Toast.makeText(context, recipes.get(position).getName() + " Clicked", Toast.LENGTH_SHORT).show();
        });
        holder.ibPopupMenu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.ibPopupMenu);
            popupMenu.inflate(R.menu.cardpopup_menu);
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.menu_modify_recipe:
                        Intent intent = new Intent(context, RecipeDetailsActivity.class);
                        intent.putExtra("ACTION", "modify");
                        MainActivity.reciepeAddResultLauncher.launch(intent);
                        break;
                    case R.id.menu_delete_recipe:
                        recipes.remove(recipes.get(position));
                        notifyItemRemoved(position);
                        DBHelperSingleton.getInstance(view.getContext()).deleteRecipe(recipes.get(position));
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

    public void addRecipe(Recipe recipe) {
        int poschanged = recipes.size();
        recipes.add(recipe);
        notifyItemInserted(poschanged);
        notifyDataSetChanged();
        notifyItemInserted(recipes.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtRecipeName;
        private final CardView parent;
        private final ImageView imageRecipe;
        private final ImageButton ibPopupMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            txtRecipeName = itemView.findViewById(R.id.txt_name);
            parent = itemView.findViewById(R.id.parent);
            imageRecipe = itemView.findViewById(R.id.image_recipe);
            ibPopupMenu = itemView.findViewById(R.id.ib_popup_menu);
        }
    }
}
