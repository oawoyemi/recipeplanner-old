package com.bulbview.recipeplanner.ui;

import static com.bulbview.recipeplanner.ui.RecipeFieldFactory.CategoryPropertyId;
import static com.bulbview.recipeplanner.ui.RecipeFieldFactory.IngredientNamePropertyId;

import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.google.inject.Inject;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.ComboBox;

@SuppressWarnings("serial")
public class IngredientValueChangeListener implements ValueChangeListener {

    private final RecipePlannerEventBus recipePlannerEventBus;
    private Container                   ingredientsTableContainer;
    private final UiHelper              uiHelper;

    @Inject
    public IngredientValueChangeListener(final RecipePlannerEventBus recipePlannerEventBus, final UiHelper uiHelper) {
        this.recipePlannerEventBus = recipePlannerEventBus;
        this.uiHelper = uiHelper;
    }

    public void setIngredientsTableContainer(final Container ingredientsTableContainer) {
        this.ingredientsTableContainer = ingredientsTableContainer;
    }

    @Override
    public void valueChange(final Property.ValueChangeEvent event) {
        final ComboBox ingredientComboBox = (ComboBox) event.getProperty();
        final ComboBox categoryComboBox = getCategoryComboBoxFor(ingredientComboBox);
        final ViewField categoryViewField = createViewField(categoryComboBox);
        final ViewField ingredientViewField = createViewField(ingredientComboBox);
        recipePlannerEventBus.newOrExistingIngredientSelected(categoryViewField, ingredientViewField);
    }

    private ViewField createViewField(final ComboBox categoryComboBox) {
        return new VaadinPropertyAdapter(categoryComboBox);
    }

    private ComboBox getCategoryComboBoxFor(final ComboBox ingredientField) {
        ComboBox comboBox = null;
        for ( final Object id : ingredientsTableContainer.getItemIds() ) {
            final Item item = ingredientsTableContainer.getItem(id);
            final ComboBox ingredientComboBox = uiHelper.getComboBox(item, IngredientNamePropertyId);
            if( ingredientComboBox.equals(ingredientField) ) {
                comboBox = uiHelper.getComboBox(item, CategoryPropertyId);
            }
        }
        return comboBox;
    }

}