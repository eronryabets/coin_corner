package com.drunar.coincorner.mapper;

import com.drunar.coincorner.dto.UserCreateEditDTO;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;

public interface UserOAuthMapper extends Mapper<OidcUserRequest, UserCreateEditDTO>{
    UserCreateEditDTO map(OidcUserRequest userRequest);
}
