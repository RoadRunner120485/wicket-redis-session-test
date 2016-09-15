package de.sturmm.wicket.nonstickysessions;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Helper class to access container session and response.
 *
 * Created by sturmm on 15.09.16.
 */
public class SessionUtils {

    private SessionUtils() {
        // prevent instantiation
    }

    static WebRequest getWebRequest() {
        return (WebRequest) RequestCycle.get().getRequest();
    }

    public static HttpServletRequest getContainerRequest() {
        return (HttpServletRequest) getWebRequest().getContainerRequest();
    }

    public static HttpSession getHttpSession() {
        return getContainerRequest().getSession();
    }

}
