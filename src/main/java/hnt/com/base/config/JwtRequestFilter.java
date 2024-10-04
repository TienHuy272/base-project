package hnt.com.base.config;

import ch.qos.logback.core.util.StringUtil;
import hnt.com.base.constants.Constants;
import hnt.com.base.model.ClaimsToken;
import hnt.com.base.model.LoginUserInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (StringUtil.notNullNorEmpty(header)) {
            String token = header.replace(Constants.BEARER_TOKEN_PREFIX, "").trim();
            ClaimsToken model = jwtUtil.getClaimsToken(token);
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : model.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(model.getUsername(), null, authorities);
            authentication.setDetails(model);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
