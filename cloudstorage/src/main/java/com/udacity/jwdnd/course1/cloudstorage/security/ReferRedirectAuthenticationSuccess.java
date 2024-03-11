package com.udacity.jwdnd.course1.cloudstorage.security;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class ReferRedirectAuthenticationSuccess extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    public ReferRedirectAuthenticationSuccess() {
        super();
        setUseReferer(true);
    }
}
