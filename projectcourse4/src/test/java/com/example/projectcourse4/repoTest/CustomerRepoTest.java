package com.example.projectcourse4.repoTest;

import com.example.projectcourse4.entity.Customer;
import com.example.projectcourse4.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Transactional
    public void testSaveCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerName("TestName");
        customer.setCustomerEmail("test@example.com");
        customer.setCustomerPhoneNumber(1234567890L);


        // Act
        Customer savedCustomer = customerRepository.save(customer);

        // Assert
        assertNotNull(savedCustomer.getCustomerId());
        assertEquals("TestName", savedCustomer.getCustomerName());
        assertEquals("test@example.com", savedCustomer.getCustomerEmail());
        assertEquals(1234567890L, savedCustomer.getCustomerPhoneNumber());

    }
}

