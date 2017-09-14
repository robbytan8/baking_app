package com.robby.baking_app.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Robby on 8/30/2017.
 *
 * @author Robby Tan
 */

public class Recipe implements Parcelable {

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_INGREDIENTS = "ingredients";
    public static final String TAG_STEPS = "steps";
    public static final String TAG_SERVINGS = "servings";
    public static final String TAG_IMAGE = "image";

    private int id;
    private String name;
    private ArrayList<RecipeIngredient> ingredients;
    private ArrayList<RecipeStep> steps;
    private int servings;
    private String image;

    public Recipe() {
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
    }


    private Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArrayList(RecipeIngredient.CREATOR);
        steps = in.createTypedArrayList(RecipeStep.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public ArrayList<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<RecipeStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<RecipeStep> steps) {
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIngredientsInString() {
        StringBuilder builder = new StringBuilder("");
        for (RecipeIngredient ingredient : ingredients) {
            builder.append(ingredient.getIngredient()).append(" (")
                    .append(ingredient.getQuantity()).append(" ")
                    .append(ingredient.getMeasure()).append(")")
                    .append("\n\n");
        }
        return builder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedList(ingredients);
        parcel.writeTypedList(steps);
        parcel.writeInt(servings);
        parcel.writeString(image);
    }
}
