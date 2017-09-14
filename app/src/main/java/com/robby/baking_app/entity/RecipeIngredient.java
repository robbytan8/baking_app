package com.robby.baking_app.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Robby on 9/2/2017.
 *
 * @author Robby Tan
 */

public class RecipeIngredient implements Parcelable {

    public static final String TAG_QUANTITY = "quantity";
    public static final String TAG_MEASURE = "measure";
    public static final String TAG_INGREDIENT = "ingredient";

    private String quantity;
    private String measure;
    private String ingredient;

    public RecipeIngredient() {
    }

    private RecipeIngredient(Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<RecipeIngredient> CREATOR = new Creator<RecipeIngredient>() {
        @Override
        public RecipeIngredient createFromParcel(Parcel in) {
            return new RecipeIngredient(in);
        }

        @Override
        public RecipeIngredient[] newArray(int size) {
            return new RecipeIngredient[size];
        }
    };

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }
}
