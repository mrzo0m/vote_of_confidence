package io.voteofconf.frontend.config;

import org.springframework.security.web.PortResolver;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.servlet.http.HttpServletRequest;

public class VocDefaultSavedRequest extends DefaultSavedRequest {

    private final String requestURL;
    private final String serverName;

    public VocDefaultSavedRequest(HttpServletRequest request, PortResolver portResolver) {
        super(request, portResolver);
        this.requestURL = super.getRequestURI().replace("frontend-microservice", "xn--b1aaffpuncuol5m.xn--p1ai");
        this.serverName = super.getServerName().replace("frontend-microservice", "xn--b1aaffpuncuol5m.xn--p1ai");
    }

    @Override
    public String getRequestURL() {
        return requestURL;
    }

    @Override
    public String getServerName() {
        return serverName;
    }
}
