package com.example.auth;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.auth.entity.SystemUser;
import com.example.auth.repository.SystemUserRepository;

@Configuration
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserDetailServiceImpl customUserDetailsService;

    @Autowired
    SystemUserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String fitId = request.getParameter("username");
        String password = request.getParameter("password");

        SystemUser user = userRepository.findByUsernameIgnoreCase(fitId);

        if (user != null) {
            if (password.equals(user.getPassword())) {
                return retrieveToken(user); // Give grant permission
            } else {
                throw new BadCredentialsException("Invalid credentials");
            }
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }

    }

    private UsernamePasswordAuthenticationToken retrieveToken(SystemUser user) {
        Set<GrantedAuthority> grantedAuthoritySet = customUserDetailsService.getGrantedAuthoritiesForUser(user);
        if (!grantedAuthoritySet.isEmpty()) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), grantedAuthoritySet);
        } else {
            throw new RoleNotFoundException();
        }
    }

}
