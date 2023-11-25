package com.example.projectcourse4.controllerTest;

import com.example.projectcourse4.configuration.JwtService;
import com.example.projectcourse4.controller.OrderController;
import com.example.projectcourse4.entity.Order;
import com.example.projectcourse4.service.OrderService;
import com.example.projectcourse4.token.TokenRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRepository tokenRepository;

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testGetAllOrders() throws Exception {
        List<Order> mockOrders = new ArrayList<>(); // Set up mock data
        // Mock behavior for orderService.getAll()
        Mockito.when(orderService.getAll()).thenReturn(mockOrders);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/orders")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // Add assertions based on your actual implementation
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testFindByOrderId() throws Exception {
        Long orderId = 1L;
        Order mockOrder = new Order(); // Set up mock data
        // Mock behavior for orderService.findByOrderId()
        Mockito.when(orderService.findByOrderId(orderId)).thenReturn(Optional.of(mockOrder));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/orders/findByOrderId/{orderId}", orderId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // Add assertions based on your actual implementation
    }

    // Add similar test methods for other OrderController endpoints
    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testDeleteOrder() throws Exception {
        Long orderId = 1L;
        // Mock behavior for orderService.deleteById()
        Mockito.doNothing().when(orderService).deleteById(orderId);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/orders/deleteOrder/{orderId}", orderId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        // Add assertions based on your actual implementation
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testAddOrder() throws Exception {
        Order mockOrder = new Order(); // Set up mock data

        Mockito.when(orderService.save(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(mockOrder);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/orders/saveOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWtueSIsImlhdCI6MTY5NzIwMjE0NCwiZXhwIjoxNjk3Mjg4NTQ0fQ.IKkomInHdHC13BKTwGaVpmPUpur4QQki8QofNTCdN_k") // Add this line
                        .content("{\"orderDate\":\"2023-01-01\",\"status\":\"PENDING\",\"products\":[]}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    // ...
}
