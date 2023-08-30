package com.drunar.coincorner.listener;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener {

    private final HttpServletRequest httpServletRequest;
    private static final Logger authLogger =
            LoggerFactory.getLogger("com.drunar.coincorner.auth");

    @EventListener
    public void onAuthenticationSuccess(InteractiveAuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        String username = authentication.getName();
        String ipAddress = getIpAddress();

        authLogger.info("User '{}' logged in from IP address '{}'", username, ipAddress);
    }

    public String getIpAddress() {
        return httpServletRequest.getRemoteAddr();
    }
}