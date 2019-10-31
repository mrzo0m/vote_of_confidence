package io.voteofconf.frontend.config;

import org.springframework.security.web.PortResolver;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.util.UrlUtils;

import javax.servlet.http.HttpServletRequest;

public class VocDefaultSavedRequest extends DefaultSavedRequest {

    private final String serverUri;

    public VocDefaultSavedRequest(HttpServletRequest request, PortResolver portResolver) {
        super(request, portResolver);
        this.serverUri = request.getHeader("SERVER_URI");
    }


    @Override
    public String getRedirectUrl() {
        StringBuilder url = new StringBuilder(serverUri);

        url.append(getRequestURI());
        if (getQueryString() != null) {
            url.append("?").append(getQueryString());
        }

        return url.toString();
    }
}
