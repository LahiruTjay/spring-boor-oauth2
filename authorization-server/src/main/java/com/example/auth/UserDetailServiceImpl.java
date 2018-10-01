package com.example.auth;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.entity.Role;
import com.example.auth.entity.SystemUser;
import com.example.auth.repository.RoleAuthorityRepository;
import com.example.auth.repository.RoleRepository;
import com.example.auth.repository.SystemUserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {

            SystemUser systemUser = systemUserRepository.findByUsernameIgnoreCase(username);
            return new User(systemUser.getUsername(), systemUser.getPassword(), getGrantedAuthoritiesForUser(systemUser));

        } catch (UsernameNotFoundException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public Set<GrantedAuthority> getGrantedAuthoritiesForUser(SystemUser user) {

        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        String userRole = user.getRole()
            .getRoleName();

        if (userRole != null) {
            Role role = roleRepository.findByRoleName(userRole);
            if (role != null) {
                roleAuthorityRepository.findAllByRole(role).forEach(roleAuthority -> {
                    grantedAuthoritySet.add(new SimpleGrantedAuthority(roleAuthority.getAuthority().getAuthority()));
                });
                return grantedAuthoritySet;
            } else {
                throw new RoleNotFoundException();
            }
        } else {
            throw new RoleNotFoundException();
        }
    }

}
