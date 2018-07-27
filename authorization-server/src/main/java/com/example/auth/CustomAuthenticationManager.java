package com.example.auth;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
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

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String authType = request.getParameter("auth_type");

        if (authType != null) {

            if (authType.equals("mobile")) {

                String fitId = request.getParameter("username");
                String password = request.getParameter("password");

                SystemUser user = userRepository.findByUsernameIgnoreCase(fitId);

                if (user != null) {
                    if (password.equals(user.getPassword())) {
                        // Give grant permission
                        return retrieveToken(user);
                    } else {
                        throw new BadCredentialsException("Invalid credentials");
                    }
                } else {
                    throw new BadCredentialsException("Invalid credentials");
                }
            }
        }
        return null;
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
