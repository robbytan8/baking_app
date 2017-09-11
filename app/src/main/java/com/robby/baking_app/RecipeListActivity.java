package com.robby.baking_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.robby.baking_app.adapter.RecipeIngredientsStepsAdapter;
import com.robby.baking_app.entity.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeIngredientActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 *
 * @author Robby Tan
 */
public class RecipeListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    @BindView(R.id.recipe_list)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        RecipeIngredientsStepsAdapter recipeIngredientsStepsAdapter;
        if (intent.hasExtra(Intent.EXTRA_STREAM)) {
            Recipe recipe = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            ArrayList<Object> objects = new ArrayList<>();
            objects.add(recipe.getIngredientsInString());
            objects.addAll(recipe.getSteps());
            recipeIngredientsStepsAdapter = new RecipeIngredientsStepsAdapter(this, objects);
        } else {
            recipeIngredientsStepsAdapter = new RecipeIngredientsStepsAdapter(this);
        }

        assert recyclerView != null;

        if (findViewById(R.id.recipe_detail_container) != null) {
            mTwoPane = true;
        }
        recyclerView.setAdapter(recipeIngredientsStepsAdapter);
    }

    public boolean ismTwoPane() {
        return mTwoPane;
    }
}
