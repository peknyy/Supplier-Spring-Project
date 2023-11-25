package com.example.projectcourse4.controllerTest;

import com.example.projectcourse4.configuration.JwtService;
import com.example.projectcourse4.controller.UserController;
import com.example.projectcourse4.entity.User;
import com.example.projectcourse4.roles.Role;
import com.example.projectcourse4.service.UserService;
import com.example.projectcourse4.token.TokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean(TokenRepository.class)
    private TokenRepository tokenRepository;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetAllUsers() throws Exception {

        User mockUser = User.builder()
                .userId(1L)
                .username("testUser")
                .email("test@example.com")
                .registrationDate(Date.valueOf("2023-01-01"))
                .phoneNumber("1234567890")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .role(Role.ADMIN)
                .build();
        when(userService.getAll()).thenReturn(Collections.singletonList(mockUser));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testFindByUsername() throws Exception {
        User mockUser = User.builder()
                .userId(1L)
                .username("testUser")
                .email("test@example.com")
                .registrationDate(Date.valueOf("2023-01-01"))
                .phoneNumber("1234567890")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .role(Role.ADMIN)
                .build();

        when(userService.findByUsername(mockUser.getUsername())).thenReturn(Optional.of(mockUser));

        mockMvc.perform(get("/api/v1/users/findByUsername/{username}", mockUser.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(mockUser.getUsername()));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteUser() throws Exception {
        User mockUser = User.builder()
                .userId(1L)
                .username("testUser")
                .email("test@example.com")
                .registrationDate(Date.valueOf("2023-01-01"))
                .phoneNumber("1234567890")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .role(Role.ADMIN)
                .build();

        mockMvc.perform(delete("/api/v1/users/deleteUser")
                        .param("userId", String.valueOf(1L))
                .with(csrf()))
                .andExpect(status().isOk());

        // Add verification if needed
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAddUser() throws Exception {
        User mockUser = User.builder()
                .userId(1L)
                .username("testUser")
                .email("test@example.com")
                .registrationDate(Date.valueOf("2023-01-01"))
                .phoneNumber("1234567890")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .role(Role.ADMIN)
                .build();

        when(userService.save(any(User.class))).thenReturn(mockUser);

        mockMvc.perform(post("/api/v1/users/saveUser")
                        .contentType("application/json")
                        .content("{\"username\":\"testUser\",\"email\":\"test@example.com\",\"registrationDate\":\"2023-01-01\",\"phoneNumber\":\"1234567890\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"password\":\"password\",\"groupId\":1}")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(mockUser.getUsername()));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateUser() throws Exception {
        User mockUser = User.builder()
                .userId(1L)
                .username("testUser")
                .email("test@example.com")
                .registrationDate(Date.valueOf("2023-01-01"))
                .phoneNumber("1234567890")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .role(Role.ADMIN)
                .build();

        when(userService.update(any(User.class))).thenReturn(mockUser);

        mockMvc.perform(get("/api/v1/users/updateUser")
                        .contentType("application/json")
                        .content("{\"username\":\"testUser\",\"email\":\"test@example.com\",\"registrationDate\":\"2023-01-01\",\"phoneNumber\":\"1234567890\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"password\":\"password\",\"groupId\":1}")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(mockUser.getUsername()));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testTestEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/users/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("test"));
    }
}
