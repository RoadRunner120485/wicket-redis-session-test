package de.sturmm.wicket.nonstickysessions;

import de.sturmm.wicket.nonstickysessions.buffer.SessionBufferingPageRendererProvider;
import de.sturmm.wicket.nonstickysessions.buffer.SessionBufferingSystemMapper;
import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.page.IPageManagerContext;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.memory.HttpSessionDataStore;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Helper class to enable session response buffer.
 *
 * Created by sturmm on 15.09.16.
 */
public class NonStickySessionsModule {

    private NonStickySessionsModule() {
        // prevent initialization
    }

    public static void setup(WebApplication app, IPageManagerContext ctx) {
        app.setRootRequestMapper(new SessionBufferingSystemMapper(app));
        app.setPageRendererProvider(new SessionBufferingPageRendererProvider());
        app.setPageManagerProvider(new DefaultPageManagerProvider(app) {
            @Override
            protected IDataStore newDataStore() {
                return new HttpSessionDataStore(ctx, new PageNumberEvictionStrategy(20));
            }
        });
    }
}
