package de.sturmm.wicket.nonstickysessions.buffer;

import org.apache.wicket.Application;
import org.apache.wicket.SystemMapper;
import org.apache.wicket.request.IRequestMapper;

/**
 * This class extends the default {@link SystemMapper} in a way that gets buffered responses, that has been stored in
 * session by {@link SessionBufferingPageRendererProvider}, back from session instead from static map.
 *
 * Created by sturmm on 15.09.16.
 */
public class SessionBufferingSystemMapper extends SystemMapper {

    public SessionBufferingSystemMapper(Application application) {
        super(application);
    }

    @Override
    protected IRequestMapper newBufferedResponseMapper() {
        return new SessionBufferedResponseMapper();
    }
}
