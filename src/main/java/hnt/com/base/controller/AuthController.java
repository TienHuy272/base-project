package hnt.com.base.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import hnt.com.base.dto.TokenDto;
import hnt.com.base.dto.UrlDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class AuthController {

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque-token.client-secret}")
    private String clientSecret;

    @Value("${url.request}")
    private String urlRequest;

    @GetMapping("/auth/url")
    public ResponseEntity<UrlDto> authUrl() {
        String url = new GoogleAuthorizationCodeRequestUrl(clientId, urlRequest
        , Arrays.asList("email", "profile", "openid")).build();
        return ResponseEntity.ok(new UrlDto(url));
    }

    @GetMapping("/auth/callback")
    public ResponseEntity<TokenDto> callback(@RequestParam("code") String code) {
        try {
            GoogleAuthorizationCodeTokenRequest googleAuthorizationCodeTokenRequest = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    new GsonFactory() ,
                    clientId, clientSecret, code, urlRequest);
            String token = googleAuthorizationCodeTokenRequest.execute().getAccessToken();
            return ResponseEntity.ok(new TokenDto(token));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
