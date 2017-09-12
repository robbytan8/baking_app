package com.robby.baking_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robby.baking_app.R;
import com.robby.baking_app.RecipeListActivity;
import com.robby.baking_app.entity.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Robby on 8/30/2017.
 *
 * @author Robby Tan
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final ArrayList<Recipe> recipes = new ArrayList<>();
    private final Context context;

    public RecipeAdapter(Context context) {
        this.context = context;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes.clear();
        this.recipes.addAll(recipes);
        this.notifyDataSetChanged();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.row_recipe_list, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        holder.tvRecipeItemName.setText(recipes.get(position).getName());
        String servings = context.getResources().getString(R.string.label_servings)
                + recipes.get(holder.getAdapterPosition()).getServings();
        holder.tvRecipeItemServing.setText(servings);
        holder.cvRecipeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeListActivity.class);
                intent.putExtra(Intent.EXTRA_STREAM, recipes.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
        if (recipes.get(holder.getAdapterPosition()).getImagePath().isEmpty()) {
            holder.imRecipeImage.setImageResource(R.mipmap.no_recipe_image);
            holder.imRecipeImage.setAlpha(0.2f);
        } else {
            Picasso.with(context)
                    .load(recipes.get(holder.getAdapterPosition()).getImagePath())
                    .into(holder.imRecipeImage);
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_recipe_item)
        CardView cvRecipeItem;
        @BindView(R.id.tv_recipe_name)
        TextView tvRecipeItemName;
        @BindView(R.id.tv_recipe_servings)
        TextView tvRecipeItemServing;
        @BindView(R.id.im_recipe_image)
        ImageView imRecipeImage;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
