package hnt.com.base.config;

import hnt.com.base.entities.Role;
import hnt.com.base.entities.User;
import hnt.com.base.model.LoginUserInfo;
import hnt.com.base.repositories.RoleRepository;
import hnt.com.base.repositories.UserRepository;
import hnt.com.base.utils.MessageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findFirstByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(MessageHelper.getMessage("msg.user.not.found"));
        }
        User user = userOptional.get();
        if (user.getRoleId() == null) {
            throw new UsernameNotFoundException(MessageHelper.getMessage("msg.user.invalid.role"));
        }

        Optional<Role> roleOptional = roleRepository.findById(user.getRoleId());
        if (roleOptional.isEmpty()) {
            throw new UsernameNotFoundException(MessageHelper.getMessage("msg.user.invalid.role"));
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(roleOptional.get().getRoleName());
        authorities.add(authority);
        return new LoginUserInfo(user, authorities);
    }
}
