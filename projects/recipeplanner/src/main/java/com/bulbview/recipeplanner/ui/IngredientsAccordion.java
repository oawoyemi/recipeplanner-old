package com.bulbview.recipeplanner.ui;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bulbview.recipeplanner.datamodel.Category;
import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.persistence.IngredientDao;
import com.google.common.collect.Maps;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.Table;

public class IngredientsAccordion extends HorizontalLayout implements Accordion.SelectedTabChangeListener {

    private final Map<Category, Table> categoryTables;
    private final Logger               logger;

    public IngredientsAccordion(final Accordion categoriesAccordion, final IngredientDao ingredientDao) {
        setSpacing(true);
        categoryTables = Maps.newHashMap();
        this.logger = LoggerFactory.getLogger(getClass());
        categoriesAccordion.addListener(this);
        addComponent(categoriesAccordion);
        categoriesAccordion.setSizeFull();
        createCategoryTabs(categoriesAccordion);
        populateCategoryTables(ingredientDao);
    }

    public Collection<Table> getIngredientCategoryTables() {
        return categoryTables.values();
    }

    public void selectedTabChange(final SelectedTabChangeEvent event) {
        // final TabSheet tabsheet = event.getTabSheet();
        // final Tab tab = tabsheet.getTab(tabsheet.getSelectedTab());
        // if( tab != null ) {
        // getWindow().showNotification("Selected tab: " + tab.getCaption());
        // }
    }

    private void createCategoryTabs(final Accordion categoriesAccordion) {
        // for ( final Category category : Category.values() ) {
        // final Table ingredientsTable = ingredientsTableProvider.get();
        // ingredientsTable.setWidth("400px");
        // ingredientsTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
        // ingredientsTable.setContainerDataSource(new
        // BeanItemContainer<Ingredient>(Ingredient.class));
        // ingredientsTable.setVisibleColumns(new Object[] { "name" });
        // ingredientsTable.setCaption(category.string);
        // ingredientsTable.setDragMode(TableDragMode.ROW);
        // categoriesAccordion.addTab(ingredientsTable);
        // categoryTables.put(category, ingredientsTable);
        // }
    }

    private void populateCategoryTables(final IngredientDao ingredientDao) {
        final Collection<Ingredient> ingredients = ingredientDao.getAll();
        for ( final Ingredient ingredient : ingredients ) {
            final Table categoryTable = categoryTables.get(ingredient.getCategory());
            categoryTable.getContainerDataSource().addItem(ingredient);
        }
    }
}
