package de.sturmm.wicket.nonstickysessions.buffer;

import de.sturmm.wicket.nonstickysessions.SessionUtils;
import de.sturmm.wicket.nonstickysessions.data.HttpResponseData;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.protocol.http.BufferedWebResponse;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.handler.render.WebPageRenderer;

/**
 * Created by sturmm on 15.09.16.
 */
public class SessionBufferAwareWebPageRenderer extends WebPageRenderer {

    /**
     * Construct.
     *
     * @param renderPageRequestHandler
     */
    public SessionBufferAwareWebPageRenderer(RenderPageRequestHandler renderPageRequestHandler) {
        super(renderPageRequestHandler);
    }

    @Override
    protected void storeBufferedResponse(Url url, BufferedWebResponse response) {
        SessionUtils.getHttpSession().setAttribute(url.toString(), HttpResponseData.from(response));
    }
}
