package com.example.projectcourse4.controllerTest;

import com.example.projectcourse4.configuration.JwtService;
import com.example.projectcourse4.controller.CustomerController;
import com.example.projectcourse4.entity.Customer;
import com.example.projectcourse4.service.CustomerService;
import com.example.projectcourse4.token.TokenRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean(JwtService.class)
    private JwtService jwtService;

    @MockBean(TokenRepository.class)
    private TokenRepository tokenRepository;
    @Test

    @WithMockUser(username = "testUser", roles = "ADMIN") // добавляем пользователя с ролью ADMIN
    public void testFindByCustomerName() throws Exception {
        String customerName = "TestCustomer";
        Customer mockCustomer = new Customer();
        mockCustomer.setCustomerName(customerName);
        Optional<Customer> optionalCustomer = Optional.of(mockCustomer);

        Mockito.when(customerService.findByCustomerName(customerName)).thenReturn(optionalCustomer);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/customers/findByCustomerName/{customerName}", customerName)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"customerName\":\"TestCustomer\"}"));
    }

    @Test

    @WithMockUser(username = "testUser", roles = "ADMIN") // добавляем пользователя с ролью ADMIN
    public void testDeleteCustomer() throws Exception {
        Long customerId = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/customers/deleteCustomer")
                        .param("customerId", String.valueOf(customerId))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(customerService, Mockito.times(1)).deleteById(customerId);
    }

    @Test

    @WithMockUser(username = "testUser", roles = "ADMIN") // добавляем пользователя с ролью ADMIN
    public void testAddCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerName("TestCustomer");

        Mockito.when(customerService.save(Mockito.any(com.example.projectcourse4.DTO.CustomerRequest.class))).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/customers/saveCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerName\":\"TestCustomer\"}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"customerName\":\"TestCustomer\"}"));
    }

    @Test

    @WithMockUser(username = "testUser", roles = "ADMIN") // добавляем пользователя с ролью ADMIN
    public void testUpdateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setCustomerName("UpdatedCustomer");

        Mockito.when(customerService.update(Mockito.any(com.example.projectcourse4.DTO.CustomerRequest.class))).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/customers/updateCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerName\":\"UpdatedCustomer\"}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"customerName\":\"UpdatedCustomer\"}"));
    }

    @Test

    @WithMockUser(username = "testUser", roles = "ADMIN") // добавляем пользователя с ролью ADMIN
    public void testTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/customers/test")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("test"));
    }
}
