package com.drunar.coincorner.config;

import com.drunar.coincorner.dto.CustomUserDetails;
import com.drunar.coincorner.http.handler.CustomLoginFailureHandler;
import com.drunar.coincorner.http.handler.CustomLoginSuccessHandler;
import com.drunar.coincorner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

import static com.drunar.coincorner.database.entity.Role.ADMIN;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final UserService userService;
    private final CustomLoginFailureHandler loginFailureHandler;
    private final CustomLoginSuccessHandler loginSuccessHandler;
    private final DataSource dataSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .csrf().disable()
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .requestMatchers("/login", "/users/registration",
                                "/v3/api-docs/**", "/swagger-ui/**", "/static/**","/forgot/**").permitAll()
                        .requestMatchers("/users/\\d+/delete").hasAuthority(ADMIN.getAuthority())
                        .requestMatchers("/admin/**").hasAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .usernameParameter("username")
                        .failureHandler(loginFailureHandler)
                        .successHandler(loginSuccessHandler)
                        .permitAll()
                        .defaultSuccessUrl("/")
                )
                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService()))
                )
                .rememberMe((remember) -> remember
                        .tokenRepository(persistentTokenRepository())
                        .userDetailsService(userService)
                        .alwaysRemember(true)
                        .tokenValiditySeconds(24*60*60*14)
                        .key("Y4PnA7qFgMzR3Wx1oLb9")

                );

        return http.build();
    }


    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            String email = userRequest.getIdToken().getClaim("email");
            if(!userService.checkEmailIfExists(email)){
                userService.newUserFromOAuth(userRequest);
            }

            CustomUserDetails userDetails = userService.loadUserByUsername(email);
            DefaultOidcUser oidcUser =
                    new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());

            Set<Method> userDetailsMethods = Set.of(UserDetails.class.getMethods());
            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> userDetailsMethods.contains(method)
                            ? method.invoke(userDetails,args)
                            : method.invoke(oidcUser, args));
        };
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepo = new JdbcTokenRepositoryImpl();
        tokenRepo.setDataSource(dataSource);
        return tokenRepo;
    }

}