package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.Role;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import com.drunar.coincorner.util.EmptyMultipartFile;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserOAuthMapper implements Mapper<OidcUserRequest, UserCreateEditDTO>{

    @Override
    public UserCreateEditDTO map(OidcUserRequest userRequest) {

        String emailClaim = userRequest.getIdToken().getClaim("email");
        String username = emailClaim.split("@")[0];

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        return new UserCreateEditDTO(
                userRequest.getIdToken().getClaim("email"),
                userRequest.getIdToken().getClaim("email"),
                "123",
                userRequest.getIdToken().getClaim("given_name"),
                userRequest.getIdToken().getClaim("family_name"),
                null,
                new EmptyMultipartFile(),
                roles

        );
    }

}