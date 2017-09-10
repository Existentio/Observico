package com.observico.observico.parser;

import com.observico.observico.model.RssItem;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Георгий on 08.09.2017.
 */

public class RssFeed {

    private List<RssItem> items;

    public void setItems(List<RssItem> items) {
        this.items = items;
    }

    public List<RssItem> getItems() {
        return items;
    }

}
