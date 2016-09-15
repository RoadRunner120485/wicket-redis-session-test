package de.sturmm.wicket.nonstickysessions.buffer;

import de.sturmm.wicket.nonstickysessions.SessionUtils;
import de.sturmm.wicket.nonstickysessions.data.HttpResponseData;
import org.apache.wicket.core.request.mapper.BufferedResponseMapper;
import org.apache.wicket.protocol.http.BufferedWebResponse;
import org.apache.wicket.request.Url;

/**
 * Created by sturmm on 15.09.16.
 */
public class SessionBufferedResponseMapper extends BufferedResponseMapper {

    @Override
    protected boolean hasBufferedResponse(Url url) {
        return SessionUtils.getHttpSession().getAttribute(url.toString()) != null;
    }

    @Override
    protected BufferedWebResponse getAndRemoveBufferedResponse(Url url) {
        final HttpResponseData responseData = (HttpResponseData) SessionUtils.getHttpSession().getAttribute(url.toString());
        if (responseData != null) {
            SessionUtils.getHttpSession().removeAttribute(url.toString());
            return responseData.toBufferedWebResponse();
        }
        return null;
    }

}
