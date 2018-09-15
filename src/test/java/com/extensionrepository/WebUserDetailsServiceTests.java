package com.extensionrepository;

import com.extensionrepository.entity.User;
import com.extensionrepository.repository.base.UserRepository;
import com.extensionrepository.service.WebUserDetailsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WebUserDetailsServiceTests {
    @Mock
    private UserRepository mockUserRepository;

    private WebUserDetailsService webUserDetailsService;

    private final String mockUsername = "mockUser";
    

    @Before
    public void beforeTest() {
        webUserDetailsService = new WebUserDetailsService(mockUserRepository);
    }

    @Test
    public void loadUserByUsername_shouldReturnUserDetails_whenSuchUserExists() {
        //Arrange
        User user = new User(mockUsername, "pass", "user user");

        Set<GrantedAuthority> grantedAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        org.springframework.security.core.userdetails.User userToFind =
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        grantedAuthorities
                );

        when(mockUserRepository.findByUsername(mockUsername)).thenReturn(user);

        // ACT
        UserDetails userFound = webUserDetailsService.loadUserByUsername(mockUsername);

        // ASSERT
        Assert.assertEquals(userToFind, userFound);

    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_shouldThrowUsernameNotFoundException_whenUserNotFound() {
        //Arrange
        User user = new User(mockUsername, "pass", "user user");

        Set<GrantedAuthority> grantedAuthorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        org.springframework.security.core.userdetails.User userToFind =
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        grantedAuthorities
                );

        when(mockUserRepository.findByUsername(mockUsername)).thenReturn(null);

        // ACT
        UserDetails userFound = webUserDetailsService.loadUserByUsername(mockUsername);
    }
}