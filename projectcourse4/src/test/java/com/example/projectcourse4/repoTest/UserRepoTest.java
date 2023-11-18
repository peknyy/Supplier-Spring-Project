package com.example.projectcourse4.repoTest;

import com.example.projectcourse4.entity.User;
import com.example.projectcourse4.repository.UserRepository;
import com.example.projectcourse4.roles.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testSaveUser() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setRegistrationDate(java.sql.Date.valueOf("2023-01-01")); // Замените дату на актуальную
        user.setPhoneNumber("1234567890");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password123");
        user.setRole(Role.valueOf("USER"));

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        assertNotNull(savedUser.getUserId());
        assertEquals("testUser", savedUser.getUsername());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals(java.sql.Date.valueOf("2023-01-01"), savedUser.getRegistrationDate());
        assertEquals("1234567890", savedUser.getPhoneNumber());
        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        assertEquals("password123", savedUser.getPassword());
        assertEquals(Role.valueOf("USER"), savedUser.getRole());
    }

    @Test
    @Transactional
    public void testFindByUsername() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setRegistrationDate(java.sql.Date.valueOf("2023-01-01")); // Замените дату на актуальную
        user.setPhoneNumber("1234567890");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password123");
        user.setRole(Role.valueOf("USER"));
        entityManager.persistAndFlush(user);

        // Act
        Optional<User> foundUser = userRepository.findUserByUsername("testUser");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    @Transactional
    public void testFindByEmail() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setRegistrationDate(java.sql.Date.valueOf("2023-01-01")); // Замените дату на актуальную
        user.setPhoneNumber("1234567890");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password123");
        user.setRole(Role.valueOf("USER"));
        entityManager.persistAndFlush(user);

        // Act
        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    // Add more test methods for other scenarios (update, delete, etc.)
}

