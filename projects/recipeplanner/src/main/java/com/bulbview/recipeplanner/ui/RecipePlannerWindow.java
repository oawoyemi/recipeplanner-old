package com.bulbview.recipeplanner.ui;

import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class RecipePlannerWindow extends Window implements WindowView {

    private final RecipeEditorModalWindow createRecipeModalWindow;

    
    public RecipePlannerWindow(final RecipeEditorModalWindow createRecipeModalWindow) {
        this.createRecipeModalWindow = createRecipeModalWindow;
    }

    public void showRecipeEditor() {
        addWindow(createRecipeModalWindow);
    }

    public void hideRecipeEditor() {
        removeWindow(createRecipeModalWindow);
    }

}
