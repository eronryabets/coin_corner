package com.drunar.coincorner.util;

import com.drunar.coincorner.database.entity.Role;
import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.dto.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.HashSet;
import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = User.builder()
                .id(customUser.id())
                .username(customUser.username())
                .password(customUser.password())
                .roles(new HashSet<>(List.of(Role.ADMIN, Role.USER)))
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication auth =
                new UsernamePasswordAuthenticationToken(customUserDetails,
                        customUserDetails.getPassword(), customUserDetails.getAuthorities());

        context.setAuthentication(auth);
        return context;
    }
}