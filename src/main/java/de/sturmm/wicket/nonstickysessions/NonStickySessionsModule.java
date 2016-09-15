package de.sturmm.wicket.nonstickysessions;

import de.sturmm.wicket.nonstickysessions.buffer.SessionBufferingPageRendererProvider;
import de.sturmm.wicket.nonstickysessions.buffer.SessionBufferingSystemMapper;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Helper class to enable session response buffer.
 *
 * Created by sturmm on 15.09.16.
 */
public class NonStickySessionsModule {

    public NonStickySessionsModule(WebApplication app) {
        app.setRootRequestMapper(new SessionBufferingSystemMapper(app));
        app.setPageRendererProvider(new SessionBufferingPageRendererProvider());
    }
}
