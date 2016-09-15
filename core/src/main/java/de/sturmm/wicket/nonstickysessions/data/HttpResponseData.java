package de.sturmm.wicket.nonstickysessions.data;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.wicket.mock.MockWebResponse;
import org.apache.wicket.protocol.http.BufferedWebResponse;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.collections.MultiMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Serializable container class to store {@link BufferedWebResponse} data in session.
 *
 * Created by sturmm on 14.09.16.
 */
@Builder
@Getter
public class HttpResponseData implements Serializable {

    private final List<Cookie> cookies;

    private final MultiMap<String, String> headers;

    private final int status;

    private final String redirectUrl;
    private final String body;
    private final Locale locale;
    private final String encoding;
    private final String contentType;
    
    public boolean isRedirect() {
        return this.redirectUrl != null;
    }


    public static HttpResponseData from(BufferedWebResponse source) {
        // apply all buffered actions to a MockResponse to get response data we need.
        final MockWebResponse mockResponse = new MockWebResponse();
        source.writeTo(mockResponse);

        // extract ServletResponse to get data which are not accessible on BufferedWebResponse
        final HttpServletResponse containerResponse = (HttpServletResponse) source.getContainerResponse();

        return HttpResponseData.builder()
                .status(mockResponse.getStatus() == null ? containerResponse.getStatus() : mockResponse.getStatus())
                .body(mockResponse.getTextResponse().toString())
                .locale(containerResponse.getLocale())
                .redirectUrl(mockResponse.getRedirectUrl())
                .encoding(containerResponse.getCharacterEncoding())
                .contentType(mockResponse.getContentType())
                .cookies(new ArrayList<>(mockResponse.getCookies()))
                .headers(mockResponse.getHeaderNames().stream()
                        .map(name -> Pair.of(name, mockResponse.getHeader(name)))
                        .reduce(new MultiMap<String, String>(),
                                (headers, p) -> {
                                    headers.addValue(p.getLeft(), p.getRight());
                                    return headers;
                                },
                                (m1, m2) -> {
                                    if (m1 == m2) return m1;
                                    else m1.putAll(m2);
                                    return m1;
                                }))
                .build();
    }

    public BufferedWebResponse toBufferedWebResponse() {
        // Get the current to be able to instantiate BufferedWebResponse
        final WebResponse webResponse = (WebResponse) RequestCycle.get().getResponse();
        final BufferedWebResponse result = new BufferedWebResponse(webResponse);

        result.setStatus(this.getStatus());
        result.setText(this.getBody());
        result.setContentType(this.getContentType());

        if (this.isRedirect()) {
            result.sendRedirect(this.getRedirectUrl());
        }

        this.getCookies().stream().forEach(result::addCookie);
        this.getHeaders().keySet().stream()
                .forEach(name -> this.getHeaders().get(name).stream()
                        .forEach(value -> result.addHeader(name, value))
                );

        // maybe this part is not necessary
        final HttpServletResponse containerResponse = (HttpServletResponse) result.getContainerResponse();
        containerResponse.setLocale(this.getLocale());
        containerResponse.setCharacterEncoding(this.getEncoding());

        return result;
    }
}
