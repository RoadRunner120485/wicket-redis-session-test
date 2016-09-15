package de.sturmm.wicket.demo.pages;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by sturmm on 13.09.16.
 */
public class NextPage extends WebPage {

    private static final MetaDataKey<String> TEST = new MetaDataKey<String>() { };

    private int clickCounter = 0;

    public NextPage(PageParameters parameters) {
        super(parameters);

        add(new Label("label", "Hello"));
        add(new Label("clickCounter", PropertyModel.of(this, "clickCounter")));

        add(new Link<Void>("next") {
            @Override
            public void onClick() {
                setResponsePage(getNextPage().setClickCounter(++clickCounter));
            }
        });
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
        ((WebResponse)getResponse()).setStatus(500);
    }

    protected HomePage getNextPage() {
        return null;
    }

    public WebPage setClickCounter(int clickCounter) {
        this.clickCounter = clickCounter;
        return this;
    }


}
