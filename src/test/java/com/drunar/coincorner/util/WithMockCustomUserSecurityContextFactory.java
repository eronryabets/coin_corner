package com.drunar.coincorner.util;

import com.drunar.coincorner.dto.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.HashSet;
import java.util.Set;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (String role : customUser.roles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(
                customUser.id(),
                customUser.username(),
                customUser.password(),
                grantedAuthorities
        );

        Authentication auth =
                new UsernamePasswordAuthenticationToken(customUserDetails,
                        customUserDetails.getPassword(), customUserDetails.getAuthorities());

        context.setAuthentication(auth);
        return context;
    }
}