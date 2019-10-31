package io.voteofconf.frontend.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.security.web.util.UrlUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VocLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

        // ~ Static fields/initializers
        // =====================================================================================

        private static final Log logger = LogFactory
                .getLog(LoginUrlAuthenticationEntryPoint.class);

        // ~ Instance fields
        // ================================================================================================

        private PortMapper portMapper = new PortMapperImpl();

        private PortResolver portResolver = new PortResolverImpl();

        private String loginFormUrl;

        private boolean forceHttps = false;

        private boolean useForward = false;

        private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


        /**
         * @param loginFormUrl URL where the login page can be found. Should either be
         *                     relative to the web-app context path (include a leading {@code /}) or an absolute
         *                     URL.
         */
    public VocLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Override
    protected String buildRedirectUrlToLoginPage(HttpServletRequest request,
                                                 HttpServletResponse response, AuthenticationException authException) {

        StringBuilder url = new StringBuilder(request.getHeader("SERVER_URI"));

        url.append(request.getRequestURI());
        if (request.getQueryString() != null) {
            url.append("?").append(request.getQueryString());
        }

        return url.toString();
    }
}
