package com.observico.observico.ui.rss;

import com.observico.observico.model.RssItem;

import java.util.List;

/**
 * Created by Георгий on 08.09.2017.
 */

public interface RssView {

    void showProgress();

    void hideProgress();

    void setItems(List<RssItem> items);

}
