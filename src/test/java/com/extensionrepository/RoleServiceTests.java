package com.extensionrepository;

import com.extensionrepository.entity.Role;
import com.extensionrepository.repository.base.RoleRepository;
import com.extensionrepository.service.RoleServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTests {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void findByName_shouldReturnRole_whenExistingNameGiven() {
        // Arrange
        Role role = new Role();
        role.setName("MOCK_ROLE");

        Mockito.when(roleRepository.findByName("MOCK_ROLE")).thenReturn(role);

        // Act
        Role actualRole = roleService.findByName("MOCK_ROLE");

        // Assert
        Assert.assertEquals(role, actualRole);
    }
}
