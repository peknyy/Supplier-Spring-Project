package com.example.projectcourse4.repoTest;

import com.example.projectcourse4.entity.Order;
import com.example.projectcourse4.repository.OrderRepository;
import com.example.projectcourse4.entity.Customer;
import com.example.projectcourse4.repository.CustomerRepository;
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
public class OrderBoxRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Transactional
    public void testSaveOrderBox() {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerName("TestCustomer");
        customer.setCustomerEmail("customer@example.com");
        customer.setCustomerPhoneNumber(1234567890L);
        Customer savedCustomer = customerRepository.save(customer);

        Order orderBox = new Order();
        orderBox.setCustomer_id(savedCustomer);
        orderBox.setStatus("Pending");

        // Act
        Order savedOrderBox = orderRepository.save(orderBox);

        // Assert
        assertNotNull(savedOrderBox.getOrderId());
        assertEquals(savedCustomer.getCustomerId(), savedOrderBox.getCustomer_id().getCustomerId());
        assertEquals("Pending", savedOrderBox.getStatus());
    }

    @Test
    @Transactional
    public void testFindOrderBoxById() {
        // Arrange
        Order orderBox = new Order();
        orderBox.setStatus("Shipped");
        Order savedOrderBox = entityManager.persistAndFlush(orderBox);

        // Act
        Optional<Order> foundOrderBox = orderRepository.findById(savedOrderBox.getOrderId());

        // Assert
        assertTrue(foundOrderBox.isPresent());
        assertEquals("Shipped", foundOrderBox.get().getStatus());
    }


}
