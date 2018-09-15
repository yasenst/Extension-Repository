package com.extensionrepository.service;

//import com.extensionrepository.configuration.WebUserDetails;
import com.extensionrepository.entity.User;
import com.extensionrepository.repository.base.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service("webUserDetailsService")
public class WebUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public WebUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid user");
        } else {
            Set<GrantedAuthority> grantedAuthorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toSet());


            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),user.getPassword(),
                    user.isEnabled(),true,true,true,
                    grantedAuthorities
            );


/*
            return new org
                    .springframework
                    .security
                    .core
                    .userdetails
                    .User(user.getUsername(),user.getPassword(),grantedAuthorities);

*/
        }
    }
}