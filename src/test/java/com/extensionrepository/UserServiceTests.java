package com.extensionrepository;

import com.extensionrepository.entity.User;
import com.extensionrepository.repository.base.UserRepository;
import com.extensionrepository.service.UserServiceImpl;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getAll_shouldReturnCorrectNumberOfEntities() {
        // Arrange
        List<User> users = Arrays.asList(
                new User("User1", "pass", "User1"),
                new User("User2", "pass", "User2"),
                new User("User3", "pass", "User3")
        );

        when(mockUserRepository.getAll()).thenReturn(users);

        //Act
        List<User> actualUserList = userService.getAll();

        // Assert
        Assert.assertEquals(actualUserList.size(),3);
    }

    @Test
    public void findByUsername_shouldReturnUserWithRequestedUsername(){
        //Arrange
        User user = new User();
        user.setUsername("test");

        when(mockUserRepository.findByUsername(user.getUsername())).thenReturn(user);

        //Act
        User actualUser = userService.findByUsername("test");

        //Assert
        Assert.assertEquals("test", actualUser.getUsername());
    }

    @Test
    public void changeStatus_shouldToggleUserEnabledStatus() {
        // Arrange
        User user1 = new User();
        user1.setId(1);
        user1.setEnabled(true);

        User user2 = new User();
        user2.setId(2);
        user2.setEnabled(false);

        when(mockUserRepository.getById(1)).thenReturn(user1);
        when(mockUserRepository.getById(2)).thenReturn(user2);

        // Act
        userService.changeStatus(1);
        userService.changeStatus(2);

        // Assert
        Assert.assertFalse(user1.isEnabled());
        Assert.assertTrue(user2.isEnabled());
    }

    @Test
    public void register_shouldReturnTrue_whenUserSaved() {
        // Arrange
        User user1 = new User();
        User user2 = new User();

        when(mockUserRepository.save(user1)).thenReturn(user1);
        when(mockUserRepository.save(user2)).thenReturn(null);

        // Act
        boolean registrationSuccess = userService.register(user1);
        boolean registrationFail = userService.register(user2);

        // Assert
        Assert.assertTrue(registrationSuccess);
        Assert.assertFalse(registrationFail);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void fieldValueExists_shouldThrowUnsupportedOperationException_whenFieldNameNotUsername() {
        userService.fieldValueExists(new Object(), "not username");
    }

    @Test
    public void fieldValueExists_shouldReturnFalse_whenObjectIsNull() {
        // Act
        boolean result = userService.fieldValueExists(null, "username");

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void fieldValueExists_shouldReturnFalse_whenValuePresent() {
        // Arrange
        User user = new User();

        // Act
        boolean result = userService.fieldValueExists(user, "username");

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void isExistUsername_shouldReturnTrue_whenUsernameExists() {
        // Arrange
        User user = new User();
        user.setUsername("test");

        when(mockUserRepository.findByUsername(user.getUsername())).thenReturn(user);

        // Act
        boolean result = userService.isExistUsername("test");

        // Assert
        Assert.assertTrue(result);
    }
 }
