package com.bulbview.recipeplanner.datamodel.schedule;

import java.util.Collection;

import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.ScheduledItem;
import com.google.appengine.repackaged.com.google.common.collect.Sets;
import com.googlecode.objectify.Key;

public abstract class Section {
    
    private final Collection<Key<Item>> items;
    
    public Section() {
        this.items = Sets.newHashSet();
    }
    
    public void addItem(final ScheduledItem item) {
        items.add(new Key<Item>(Item.class, item.getId()));
    }
    
    public void clear() {
        items.clear();
        
    }
    
    public Collection<Key<Item>> getItems() {
        return items;
    }
    
    @Override
    public abstract String toString();
}
