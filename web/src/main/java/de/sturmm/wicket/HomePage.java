package de.sturmm.wicket;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Created by sturmm on 13.09.16.
 */
public class HomePage extends WebPage {

    private static final MetaDataKey<String> TEST = new MetaDataKey<String>() { };

    public HomePage(PageParameters parameters) {
        super(parameters);

        String data = Session.get().getMetaData(TEST);

        if (data == null) {
            final String hello = parameters.get("hello").toOptionalString();
            Session.get().setMetaData(TEST, hello);
            data = hello;
        }

        add(new Label("label", data));

        add(new Link<Void>("next") {
            @Override
            public void onClick() {
                setResponsePage(getNextPage());
            }
        });
    }

    protected IRequestablePage getNextPage() {
        return new HomePage(getPageParameters()) {
            @Override
            protected IRequestablePage getNextPage() {
                return HomePage.this;
            }
        };
    }


}
