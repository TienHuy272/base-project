package hnt.com.base.controller;

import hnt.com.base.config.JwtUtil;
import hnt.com.base.model.LoginUserInfo;
import hnt.com.base.model.LoginUserResponse;
import hnt.com.base.model.TokenModel;
import hnt.com.base.model.UserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BaseController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @GetMapping("/ping")
    public String ping() {
        log.info("Someone ping me !!!");
        return "pong";
    }

    @PostMapping("/login")
    public LoginUserResponse login(@RequestBody UserModel request) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            LoginUserResponse loginUserResponse = new LoginUserResponse();
            TokenModel tokenModel = jwtUtil.generateToken(authentication);
            LoginUserInfo user = (LoginUserInfo) authentication.getPrincipal();
            loginUserResponse.setToken(tokenModel.getToken());
            loginUserResponse.setRefreshToken(tokenModel.getRefreshToken());
            loginUserResponse.setUsername(user.getUsername());
            loginUserResponse.setEmail(user.getEmail());
            return loginUserResponse;
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }
    }

}
