package hnt.com.base.model;

import hnt.com.base.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class LoginUserInfo extends org.springframework.security.core.userdetails.User {
    private Integer id;
    private String username;
    private String email;
    private Collection<GrantedAuthority> authorities;

    public LoginUserInfo(User user, Collection<GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        setInfo(user, authorities);
    }

    private void setInfo(User user, Collection<GrantedAuthority> authorities) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.authorities = authorities;
    }
}
