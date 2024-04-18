package com.quiz.quizback.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Log4j2
public class JwtAuthenticationTokenConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;

    @Value("${jwt.auth.converter.principal-attribute}")
    private String principalAttribute;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities =
                Stream.concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(), extractResourceRoles(jwt).stream())
                        .collect(Collectors.toSet());
        log.info(extractResourceRoles(jwt).toString());
        String claimName = principalAttribute == null ? JwtClaimNames.SUB : principalAttribute;
        return new JwtAuthenticationToken(jwt, authorities, jwt.getClaim(claimName));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Collection<String> roles = new HashSet<>();

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null) {
            Collection<String> realmRoles = (Collection<String>) realmAccess.get("roles");
            if (realmRoles != null) {
                roles.addAll(realmRoles);
            }
        }

        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null) {
            Map<String, Object> accountAccess = (Map<String, Object>) resourceAccess.get("account");
            if (accountAccess != null) {
                Collection<String> accountRoles = (Collection<String>) accountAccess.get("roles");
                if (accountRoles != null) {
                    roles.addAll(accountRoles);
                }
            }
        }

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
