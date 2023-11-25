package com.example.projectcourse4.repoTest;

import com.example.projectcourse4.entity.Supplier;
import com.example.projectcourse4.repository.SupplierRepository;
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
public class SupplierRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testSaveSupplier() {
        // Arrange
        User user = new User();
        user.setUsername("exampleUser");
        user.setEmail("user@example.com");
        user.setRegistrationDate(java.sql.Date.valueOf("2023-01-01")); // Замените дату на актуальную
        user.setPhoneNumber("1234567890");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password123"); // Хорошей практикой является использование хеша пароля в реальном приложении
        user.setRole(Role.valueOf("USER"));
        User savedUser = userRepository.save(user);

        Supplier supplier = new Supplier();
        supplier.setSupplierName("TestSupplier");
        supplier.setSupplierEmail("supplier@example.com");
        supplier.setContactPerson(123123L);
        supplier.setUserId(savedUser.getUserId());

        // Act
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Assert
        assertNotNull(savedSupplier.getSupplierId());
        assertEquals("TestSupplier", savedSupplier.getSupplierName());
        assertEquals("supplier@example.com", savedSupplier.getSupplierEmail());
        assertEquals(123123L, savedSupplier.getContactPerson());
        assertEquals(savedUser.getUserId(), savedSupplier.getUserId());
    }

    @Test
    @Transactional
    public void testFindSupplierById() {
        // Arrange
        User user = new User();
        user.setUsername("exampleUser");
        user.setEmail("user@example.com");
        user.setRegistrationDate(java.sql.Date.valueOf("2023-01-01")); // Замените дату на актуальную
        user.setPhoneNumber("1234567890");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password123"); // Хорошей практикой является использование хеша пароля в реальном приложении
        user.setRole(Role.valueOf("USER"));
        User savedUser = userRepository.save(user);

        Supplier supplier = new Supplier();
        supplier.setSupplierName("TestSupplier");
        supplier.setSupplierEmail("supplier@example.com");
        supplier.setContactPerson(123123L);
        supplier.setUserId(savedUser.getUserId());
        Supplier savedSupplier = entityManager.persistAndFlush(supplier);

        // Act
        Optional<Supplier> foundSupplier = supplierRepository.findById(savedSupplier.getSupplierId());

        // Assert
        assertTrue(foundSupplier.isPresent());
        assertEquals("TestSupplier", foundSupplier.get().getSupplierName());
    }


}
