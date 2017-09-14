package com.robby.baking_app;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robby.baking_app.adapter.RecipeAdapter;
import com.robby.baking_app.entity.Recipe;
import com.robby.baking_app.utility.NetworkUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Robby Tan
 */

public class RecipeMainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>> {

    private static final int RECIPE_APP_ID = 123;
    private RecipeAdapter recipeAdapter;
    @BindView(R.id.rv_main)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        recyclerView.setAdapter(this.getRecipeAdapter());
        this.getRecipeFromInternet();
    }

    private RecipeAdapter getRecipeAdapter() {
        if (recipeAdapter == null) {
            recipeAdapter = new RecipeAdapter(this);
        }
        return recipeAdapter;
    }

    private void getRecipeFromInternet() {
        if (NetworkUtils.hasInternetConnection(this)) {
            if (getSupportLoaderManager().getLoader(RECIPE_APP_ID) == null) {
                getSupportLoaderManager().initLoader(RECIPE_APP_ID, null, this);
            } else {
                getSupportLoaderManager().restartLoader(RECIPE_APP_ID, null, this);
            }
        } else {
            Toast.makeText(this, "Please check your internet connection!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<Recipe>>(this) {

            ArrayList<Recipe> recipes;

            @Override
            protected void onStartLoading() {
                if (null != recipes) {
                    deliverResult(recipes);
                } else {
                    forceLoad();
                }
            }

            @Override
            public ArrayList<Recipe> loadInBackground() {
                InputStream inputStream;
                HttpURLConnection connection = null;
                try {
                    URL url = NetworkUtils.buildUrl();
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(6000);
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        recipes = new ArrayList<>();
                        ObjectMapper objectMapper = new ObjectMapper();
                        recipes.addAll(Arrays.asList(objectMapper.readValue(url, Recipe[].class)));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return recipes;
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        if (null != data) {
            this.getRecipeAdapter().setRecipes(data);
        } else {
            Toast.makeText(this, "Error Loading Data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {

    }
}
