package com.drunar.coincorner.listener;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFailureListener {

    private final HttpServletRequest httpServletRequest;
    private static final Logger failedAuthLogger =
            LoggerFactory.getLogger("com.drunar.coincorner.auth.failed");

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        String username = (String) event.getAuthentication().getPrincipal();
        String ipAddress = getIpAddress();

        failedAuthLogger.info("Failed login attempt for user '{}' from IP address '{}'", username, ipAddress);
    }

    public String getIpAddress() {
        return httpServletRequest.getRemoteAddr();
    }
}