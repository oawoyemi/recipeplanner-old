package com.bulbview.recipeplanner.ui.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Entity;
import com.bulbview.recipeplanner.ui.presenter.CategoryListPresenter;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

@Component
public class CategorisedItemList extends GenericListUiManager<Entity, CategoryListPresenter> {

    private String categoryName;

    public CategorisedItemList() {
        super(Entity.class);
    }

    @Override
    public void init() {
        super.init();
        topLevelPanel.addComponent(newItemTextFieldPanel());
    }

    public void setCategoryName(final String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    @Autowired
    public void setGenericListTable(final Table genericListTable) {
        super.setGenericListTable(genericListTable);
    }

    @Override
    @Autowired
    public void setPresenter(final CategoryListPresenter presenter) {
        super.setPresenter(presenter);
        presenter.setView(this);
    }

    @SuppressWarnings("serial")
    private com.vaadin.ui.Component newItemTextFieldPanel() {
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        final TextField itemName = new TextField();
        itemName.setInputPrompt("<Enter new item name>");
        horizontalLayout.addComponent(itemName);
        final Button button = new Button("+");
        button.addListener(new ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                presenter.addItem(itemName.getValue().toString(), categoryName);
            }
        });
        button.setClickShortcut(KeyCode.ENTER);
        horizontalLayout.addComponent(button);
        return horizontalLayout;
    }
}