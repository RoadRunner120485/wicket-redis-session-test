package de.sturmm.wicket.nonstickysessions.buffer;

import org.apache.wicket.IPageRendererProvider;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.request.handler.render.PageRenderer;
import org.apache.wicket.request.handler.render.WebPageRenderer;

/**
 * This class provider creates a default {@link WebPageRenderer} with the difference, that
 * responses that are buffered for Redirect-Get Cycle are stored in session data.
 *
 * In combination with {@link org.apache.wicket.pageStore.memory.HttpSessionDataStore} this enables you
 * to use distributed sessions in your preferred way (e.g. spring-session with redis) and get rid of the need
 * for sticky sessions.
 *
 * Created by sturmm on 15.09.16.
 */
public class SessionBufferingPageRendererProvider implements IPageRendererProvider {
    @Override
    public PageRenderer get(RenderPageRequestHandler context) {
        return new SessionBufferAwareWebPageRenderer(context);
    }
}
