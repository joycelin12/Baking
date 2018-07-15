package com.example.android.baking.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by joycelin12 on 7/14/18.
 */

public class Recipe implements Parcelable {

    String id;
    String name;
    ArrayList<Ingredients> ingredients;
    ArrayList<Steps> steps;
    String servings;
    String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Steps> steps) {
        this.steps = steps;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Recipe(String id, String name, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps, String servings, String image) {

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
        dest.writeString(this.servings);
        dest.writeString(this.image);
    }

    protected Recipe(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.ingredients = in.createTypedArrayList(Ingredients.CREATOR);
        this.steps = in.createTypedArrayList(Steps.CREATOR);
        this.servings = in.readString();
        this.image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
