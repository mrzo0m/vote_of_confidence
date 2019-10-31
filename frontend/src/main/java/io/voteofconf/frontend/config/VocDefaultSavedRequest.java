package io.voteofconf.frontend.config;

import org.springframework.security.web.PortResolver;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.util.UrlUtils;

import javax.servlet.http.HttpServletRequest;

public class VocDefaultSavedRequest extends DefaultSavedRequest {

    private final String requestURI;
    private final String requestURL;
    private final String serverName;

    public VocDefaultSavedRequest(HttpServletRequest request, PortResolver portResolver) {
        super(request, portResolver);
        this.requestURL = super.getRequestURL().replace("frontend-microservice", "xn--b1aaffpuncuol5m.xn--p1ai");
        this.requestURI = super.getRequestURI().replace("frontend-microservice", "xn--b1aaffpuncuol5m.xn--p1ai");
        this.serverName = super.getServerName().replace("frontend-microservice", "xn--b1aaffpuncuol5m.xn--p1ai");
    }

    @Override
    public String getRequestURL() {
        return requestURL;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }

    @Override
    public String getServerName() {
        return serverName;
    }

    @Override
    public String getRedirectUrl() {
        return UrlUtils.buildFullRequestUrl(getScheme(), serverName, getServerPort(), requestURI,
                getQueryString());
    }
}
