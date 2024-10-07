package hnt.com.base.config.oauth2;

import hnt.com.base.config.JwtUtil;
import hnt.com.base.dto.UserInfo;
import hnt.com.base.model.ClaimsToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleIntrospector implements OpaqueTokenIntrospector {
    private final JwtUtil jwtUtil;
    private final WebClient userInfoClient;

    public GoogleIntrospector(WebClient userInfoClient, JwtUtil jwtUtil) {
        this.userInfoClient = userInfoClient;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        ClaimsToken claimsToken = isSystemUserToken(token);
        if (claimsToken != null) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", claimsToken.getEmail());
            return new OAuth2IntrospectionAuthenticatedPrincipal(claimsToken.getUsername(), attributes,null);
        }
        UserInfo user = userInfoClient.get()
                .uri(uriBuilder -> uriBuilder.path("/oauth2/v3/userinfo").queryParam("access_token", token).build())
                .retrieve()
                .bodyToMono(UserInfo.class)
                .block();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", user.sub());
        attributes.put("name", user.name());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("GOOGLE_USER"));
        return new OAuth2IntrospectionAuthenticatedPrincipal(user.name(), attributes,authorities);
    }

    private ClaimsToken isSystemUserToken(String token) {
        try {
            ClaimsToken model = jwtUtil.getClaimsToken(token);
            return model;
        } catch (Exception e) {
            return null;
        }
    }



}
