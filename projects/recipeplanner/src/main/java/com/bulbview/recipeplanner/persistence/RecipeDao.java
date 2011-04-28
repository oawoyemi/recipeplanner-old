package com.bulbview.recipeplanner.persistence;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Recipe;

public interface RecipeDao {

    Collection<Recipe> getAll();

    void saveRecipe(Recipe recipe);

}
