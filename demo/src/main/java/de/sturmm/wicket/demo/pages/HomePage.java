package de.sturmm.wicket.demo.pages;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by sturmm on 13.09.16.
 */
public class HomePage extends WebPage {

    private static final MetaDataKey<String> TEST = new MetaDataKey<String>() { };

    private int clickCounter = 0;

    public HomePage(PageParameters parameters) {
        super(parameters);

        String data = Session.get().getMetaData(TEST);

        if (data == null) {
            final String hello = parameters.get("hello").toOptionalString();
            Session.get().setMetaData(TEST, hello);
            data = hello;
        }

        add(new Label("label", "Hello"));
        add(new Label("clickCounter", PropertyModel.of(this, "clickCounter")));

        add(new Link<Void>("next") {
            @Override
            public void onClick() {
                setResponsePage(getNextPage().setClickCounter(++clickCounter));
            }
        });
    }

    protected NextPage getNextPage() {
        return new NextPage(getPageParameters()) {
            @Override
            protected HomePage getNextPage() {
                return HomePage.this;
            }
        };
    }

    public WebPage setClickCounter(int clickCounter) {
        this.clickCounter = clickCounter;
        return this;
    }
}
