package com.robby.baking_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.robby.baking_app.BuildConfig;
import com.robby.baking_app.R;
import com.robby.baking_app.RecipeIngredientActivity;
import com.robby.baking_app.RecipeIngredientFragment;
import com.robby.baking_app.RecipeListActivity;
import com.robby.baking_app.RecipeStepActivity;
import com.robby.baking_app.RecipeStepFragment;
import com.robby.baking_app.entity.RecipeStep;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Robby on 8/30/2017.
 *
 * @author Robby Tan
 */

public class RecipeIngredientsStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Object> OBJECTS;
    private final int INGREDIENTS = 0;
    private final int STEP = 1;
    private final Context CONTEXT;

    public RecipeIngredientsStepsAdapter(Context CONTEXT) {
        this.CONTEXT = CONTEXT;
        this.OBJECTS = new ArrayList<>();
    }

    public RecipeIngredientsStepsAdapter(Context CONTEXT, ArrayList<Object> OBJECTS) {
        this.CONTEXT = CONTEXT;
        this.OBJECTS = OBJECTS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(CONTEXT);

        switch (viewType) {
            case STEP:
                View v1 = inflater.inflate(R.layout.row_recipe_step, parent, false);
                viewHolder = new StepsViewHolder(v1);
                break;
            case INGREDIENTS:
                View v2 = inflater.inflate(R.layout.row_recipe_ingredient, parent, false);
                viewHolder = new IngredientsViewHolder(v2);
                break;
            default:
                View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                return null;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case STEP:
                StepsViewHolder holder1 = (StepsViewHolder) holder;
                final RecipeStep recipeStep = (RecipeStep) OBJECTS.get(position);
                holder1.tvRecipeSteps.setText("Step " + (Integer.parseInt(recipeStep.getId()) + 1)
                        + ": " + recipeStep.getShortDescription());
                holder1.cvRecipeSteps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean twoPane = CONTEXT.getResources().getBoolean(R.bool.is_tablet);
                        if (twoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putParcelable(CONTEXT.getResources()
                                    .getString(R.string.send_parcelable_recipe_step), recipeStep);
                            RecipeStepFragment fragment = new RecipeStepFragment();
                            fragment.setArguments(arguments);
                            ((RecipeListActivity) CONTEXT).getSupportFragmentManager()
                                    .beginTransaction().replace(R.id.recipe_detail_container, fragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(CONTEXT, RecipeStepActivity.class);
                            intent.putExtra(CONTEXT.getResources().getString
                                    (R.string.send_parcelable_recipe_step), recipeStep);
                            CONTEXT.startActivity(intent);
                        }
                    }
                });
                if (recipeStep.getThumbnailURL().isEmpty()) {
                    Picasso.with(CONTEXT)
                            .load(BuildConfig.RECIPE_DEF_IMG)
                            .into(holder1.imRecipeStepBg);
                } else {
                    Picasso.with(CONTEXT)
                            .load(recipeStep.getThumbnailURL())
                            .into(holder1.imRecipeStepBg);
                }
                break;
            case INGREDIENTS:
                IngredientsViewHolder holder2 = (IngredientsViewHolder) holder;
                holder2.tvRecipeIngredients.setText(CONTEXT.getResources()
                        .getString(R.string.label_recipe_ingredient));
                holder2.cvRecipeIngredients.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean twoPane = CONTEXT.getResources().getBoolean(R.bool.is_tablet);
                        if (twoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putString(CONTEXT.getResources().getString(R.string.send_string_ingredients_key), OBJECTS.get(holder
                                    .getAdapterPosition()).toString());
                            RecipeIngredientFragment fragment = new RecipeIngredientFragment();
                            fragment.setArguments(arguments);
                            ((RecipeListActivity) CONTEXT).getSupportFragmentManager()
                                    .beginTransaction().replace(R.id.recipe_detail_container, fragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(CONTEXT, RecipeIngredientActivity.class);
                            intent.putExtra(Intent.EXTRA_STREAM, OBJECTS.get(
                                    holder.getAdapterPosition()).toString());
                            CONTEXT.startActivity(intent);
                        }
                    }
                });
                Picasso.with(CONTEXT)
                        .load(BuildConfig.RECIPE_DEF_IMG)
                        .into(holder2.imRecipeIngredientBg);
                break;
            default:
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (OBJECTS.get(position) instanceof RecipeStep) {
            return STEP;
        } else if (OBJECTS.get(position) instanceof String) {
            return INGREDIENTS;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return OBJECTS.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_recipe_steps)
        CardView cvRecipeSteps;
        @BindView(R.id.tv_recipe_steps)
        TextView tvRecipeSteps;
        @BindView(R.id.im_recipe_steps)
        ImageView imRecipeStepBg;

        private StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_recipe_ingredients)
        CardView cvRecipeIngredients;
        @BindView(R.id.tv_recipe_ingredients)
        TextView tvRecipeIngredients;
        @BindView(R.id.im_recipe_ingredients)
        ImageView imRecipeIngredientBg;

        private IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
