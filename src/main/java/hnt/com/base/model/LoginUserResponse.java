package hnt.com.base.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserResponse {
    private Integer id;
    private String username;
    private String role;
    private String email;
    private String token;
    private String refreshToken;
}
