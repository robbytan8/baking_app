package com.robby.baking_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robby.baking_app.R;
import com.robby.baking_app.RecipeIngredientActivity;
import com.robby.baking_app.RecipeIngredientFragment;
import com.robby.baking_app.RecipeListActivity;
import com.robby.baking_app.RecipeStepFragment;
import com.robby.baking_app.entity.RecipeStep;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Robby on 8/30/2017.
 *
 * @author Robby Tan
 */

public class RecipeIngredientsStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Object> objects;

    private final int ingredient = 0;
    private final int step = 1;

    private final Context context;

    public RecipeIngredientsStepsAdapter(Context context) {
        this.context = context;
        this.objects = new ArrayList<>();
    }

    public RecipeIngredientsStepsAdapter(Context context, ArrayList<Object> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case step:
                View v1 = inflater.inflate(R.layout.row_recipe_step, parent, false);
                viewHolder = new StepsViewHolder(v1);
                break;
            case ingredient:
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
            case step:
                StepsViewHolder holder1 = (StepsViewHolder) holder;
                final RecipeStep recipeStep = (RecipeStep) objects.get(position);
                holder1.tvRecipeSteps.setText("Step " + (Integer.parseInt(recipeStep.getId()) + 1)
                        + ": " + recipeStep.getShortDescription());
                holder1.cvRecipeSteps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean twoPane = ((RecipeListActivity) context).ismTwoPane();
                        if (twoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putParcelable(context.getResources()
                                    .getString(R.string.send_parcelable_recipe_step), recipeStep);
                            RecipeStepFragment fragment = new RecipeStepFragment();
                            fragment.setArguments(arguments);
                            ((RecipeListActivity) context).getSupportFragmentManager()
                                    .beginTransaction().replace(R.id.recipe_detail_container, fragment)
                                    .commit();
                        }
                    }
                });
                break;
            case ingredient:
                IngredientsViewHolder holder2 = (IngredientsViewHolder) holder;
                holder2.tvRecipeIngredients.setText(context.getResources()
                        .getString(R.string.label_recipe_ingredient));
                holder2.cvRecipeIngredients.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean twoPane = ((RecipeListActivity) context).ismTwoPane();
                        if (twoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putString(context.getResources().getString(R.string.send_string_ingredients_key), objects.get(holder
                                    .getAdapterPosition()).toString());
                            RecipeIngredientFragment fragment = new RecipeIngredientFragment();
                            fragment.setArguments(arguments);
                            ((RecipeListActivity) context).getSupportFragmentManager()
                                    .beginTransaction().replace(R.id.recipe_detail_container, fragment)
                                    .commit();
                        } else {
                            Intent intent = new Intent(context, RecipeIngredientActivity.class);
                            intent.putExtra(Intent.EXTRA_STREAM, objects.get(
                                    holder.getAdapterPosition()).toString());
                            context.startActivity(intent);
                        }
                    }
                });
                break;
            default:
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (objects.get(position) instanceof RecipeStep) {
            return step;
        } else if (objects.get(position) instanceof String) {
            return ingredient;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cv_recipe_steps)
        CardView cvRecipeSteps;
        @BindView(R.id.tv_recipe_steps)
        TextView tvRecipeSteps;

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

        private IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
