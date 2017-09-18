package com.robby.baking_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robby.baking_app.BuildConfig;
import com.robby.baking_app.R;
import com.robby.baking_app.RecipeListActivity;
import com.robby.baking_app.entity.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

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
        if (!TextUtils.isEmpty(recipes.get(holder.getAdapterPosition()).getImage())) {
            Picasso.with(context)
                    .load(recipes.get(holder.getAdapterPosition()).getImage())
                    .into(holder.imRecipeImage);
        } else if (!TextUtils.isEmpty(recipes.get(holder.getAdapterPosition()).getSteps().get(2).getVideoURL())) {
            Bitmap videoBitmap;
            MediaMetadataRetriever mediaMetadataRetriever = null;
            try {
                mediaMetadataRetriever = new MediaMetadataRetriever();
                if (Build.VERSION.SDK_INT >= 14) {
                    mediaMetadataRetriever.setDataSource(recipes.get(holder.getAdapterPosition()).getSteps().get(2).getVideoURL(), new HashMap<String, String>());
                } else {
                    mediaMetadataRetriever.setDataSource(recipes.get(holder.getAdapterPosition()).getSteps().get(2).getVideoURL());
                }
                videoBitmap = mediaMetadataRetriever.getFrameAtTime();
                holder.imRecipeImage.setImageBitmap(videoBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mediaMetadataRetriever != null) {
                    mediaMetadataRetriever.release();
                }
            }
        } else {
            Picasso.with(context)
                    .load(BuildConfig.RECIPE_LIST_DEF_IMG)
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
