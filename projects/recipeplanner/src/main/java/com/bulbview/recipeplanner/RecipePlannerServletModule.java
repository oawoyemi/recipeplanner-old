package com.bulbview.recipeplanner;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBusManager;

import com.bulbview.recipeplanner.dao.InMemoryIngredientDao;
import com.bulbview.recipeplanner.dao.InMemoryRecipeDao;
import com.bulbview.recipeplanner.dao.IngredientDao;
import com.bulbview.recipeplanner.dao.RecipeDao;
import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.DailyRecipeList;
import com.bulbview.recipeplanner.ui.DailyRecipeListContainer;
import com.bulbview.recipeplanner.ui.DailyRecipeListsContainerView;
import com.bulbview.recipeplanner.ui.MasterRecipeList;
import com.bulbview.recipeplanner.ui.MasterRecipeListView;
import com.bulbview.recipeplanner.ui.RecipeEditorForm;
import com.bulbview.recipeplanner.ui.RecipeEditorFormView;
import com.bulbview.recipeplanner.ui.RecipeEditorModalWindow;
import com.bulbview.recipeplanner.ui.RecipePlannerApplication;
import com.bulbview.recipeplanner.ui.RecipePlannerPresenter.NumberOfDailyRecipeLists;
import com.bulbview.recipeplanner.ui.RecipePlannerWindow;
import com.bulbview.recipeplanner.ui.WindowView;
import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;
import com.bulbview.recipeplanner.ui.menu.MasterRecipeListContextMenu;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.servlet.ServletModule;
import com.google.inject.servlet.ServletScopes;
import com.google.inject.servlet.SessionScoped;
import com.vaadin.Application;
import com.vaadin.ui.Window;

final class RecipePlannerServletModule extends ServletModule {

    private final Logger logger;

    /**
     * @param recipePlannerServletConfig
     */
    RecipePlannerServletModule() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void bindDaos() {
        bind(RecipeDao.class).to(InMemoryRecipeDao.class);
        bind(IngredientDao.class).to(InMemoryIngredientDao.class);
    }

    public void bindScopes() {
        bind(EventBusManager.class).in(ServletScopes.SESSION);
        bind(RecipePlannerWindow.class).in(ServletScopes.SESSION);
        bind(Application.class).to(RecipePlannerApplication.class).in(ServletScopes.SESSION);
        bind(DailyRecipeListContainer.class).in(ServletScopes.SESSION);
        bind(RecipeEditorForm.class).in(ServletScopes.SESSION);
        bind(MasterRecipeList.class).in(ServletScopes.SESSION);
        bind(MasterRecipeListContextMenu.class).in(ServletScopes.SESSION);
        bind(RecipeEditorModalWindow.class).in(ServletScopes.SESSION);
    }

    public void bindViews() {
        bind(Window.class).to(RecipePlannerWindow.class);
        bind(RecipeEditorFormView.class).to(RecipeEditorForm.class);
        bind(MasterRecipeListView.class).to(MasterRecipeList.class);
        bind(WindowView.class).to(RecipePlannerWindow.class);
        bind(DailyRecipeListsContainerView.class).to(DailyRecipeListContainer.class);
    }

    @Override
    protected void configureServlets() {
        serve("/*").with(GuiceApplicationServlet.class);
        bind(String.class).toInstance("Recipe Planner");
        bindScopes();
        bindViews();
        bind(new TypeLiteral<Collection<DailyRecipeList>>() {}).toInstance(new HashSet<DailyRecipeList>());
        bind(Integer.class).annotatedWith(NumberOfDailyRecipeLists.class).toInstance(7);
        bind(new TypeLiteral<Collection<Recipe>>() {}).toInstance(new HashSet<Recipe>());
        bindDaos();
    }

    @Provides
    Collection<Ingredient> providesIngredientsCollection() {
        return new HashSet<Ingredient>();
    }

    @Provides
    @SessionScoped
    RecipePlannerEventBus providesRecipePlannerEventBus(final EventBusManager eventBusManager) {
        logger.debug("Retrieving RecipePlannerEventBus...");
        return eventBusManager.getEventBus(RecipePlannerEventBus.class);
    }

    @Provides
    Collection<String> providesStringCollection() {
        return new HashSet<String>();
    }
}