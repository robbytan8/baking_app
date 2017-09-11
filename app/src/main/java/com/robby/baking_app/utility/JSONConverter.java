package com.robby.baking_app.utility;

import com.robby.baking_app.entity.Recipe;
import com.robby.baking_app.entity.RecipeIngredient;
import com.robby.baking_app.entity.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robby on 7/25/2017.
 *
 * @author Robby Tan
 */

public class JSONConverter {

    public static String parseDataToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder("");
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        inputStream.close();
        return builder.toString();
    }

    public static List<Recipe> convertDataToRecipeEntity(String data) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Recipe recipe = new Recipe();
                recipe.setId(object.getInt(Recipe.TAG_ID));
                recipe.setName(object.getString(Recipe.TAG_NAME));
                JSONArray jsonIngredients = object.getJSONArray(Recipe.TAG_INGREDIENTS);
                for (int index = 0; index < jsonIngredients.length(); index++) {
                    JSONObject objectIngredient = jsonIngredients.getJSONObject(index);
                    RecipeIngredient ingredient = new RecipeIngredient();
                    ingredient.setQuantity(objectIngredient.getString(RecipeIngredient.TAG_QUANTITY));
                    ingredient.setMeasure(objectIngredient.getString(RecipeIngredient.TAG_MEASURE));
                    ingredient.setIngredientName(objectIngredient.getString(RecipeIngredient.TAG_INGREDIENT));
                    recipe.getIngredients().add(ingredient);
                }
                JSONArray jsonSteps = object.getJSONArray(Recipe.TAG_STEPS);
                for (int index = 0; index < jsonSteps.length(); index++) {
                    JSONObject objectIngredient = jsonSteps.getJSONObject(index);
                    RecipeStep step = new RecipeStep();
                    step.setId(objectIngredient.getString(RecipeStep.TAG_ID));
                    step.setShortDescription(objectIngredient.getString(RecipeStep.TAG_SHORT_DESC));
                    step.setDescription(objectIngredient.getString(RecipeStep.TAG_DESC));
                    step.setVideoUrl(objectIngredient.getString(RecipeStep.TAG_VIDEO_URL));
                    step.setThumbnailUrl(objectIngredient.getString(RecipeStep.TAG_THUMBNAIL_URL));
                    recipe.getSteps().add(step);
                }
                recipe.setServings(object.getInt(Recipe.TAG_SERVINGS));
                recipe.setImagePath(object.getString(Recipe.TAG_IMAGE));
                recipes.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
