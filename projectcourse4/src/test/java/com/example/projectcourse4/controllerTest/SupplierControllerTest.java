package com.example.projectcourse4.controllerTest;

import com.example.projectcourse4.configuration.JwtService;
import com.example.projectcourse4.controller.SupplierController;
import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.entity.Supplier;
import com.example.projectcourse4.service.SupplierService;
import com.example.projectcourse4.token.TokenRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(SupplierController.class)
class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupplierService supplierService;

    @MockBean
    private JwtService jwtService;
    @MockBean(TokenRepository.class)
    private TokenRepository tokenRepository;

    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN") // добавляем пользователя с ролью ADMIN
    void findBySupplierName() throws Exception {
        Supplier mockSupplier = Supplier.builder()
                .supplierName("Test Supplier")
                .build();
        when(supplierService.findBySupplierName("Test Supplier")).thenReturn(mockSupplier);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/suppliers/findBySupplierName/Test Supplier")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.supplierName").value("Test Supplier"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN") // добавляем пользователя с ролью ADMIN
    void findAllProductsForSupplier() throws Exception {
        Supplier mockSupplier = Supplier.builder()
                .supplierName("Test Supplier")
                .build();

        Product mockProduct1 = Product.builder()
                .productName("Test Product 1")
                .productDesc("Description of Test Product 1")
                .productPrice(50)
                .supplier_id(mockSupplier)  // Set the Supplier instance
                .imageUrl("http://example.com/image1.jpg")
                .category(1L)
                .available(true)
                .build();

        Product mockProduct2 = Product.builder()
                .productName("Test Product 2")
                .productDesc("Description of Test Product 2")
                .productPrice(50)
                .supplier_id(mockSupplier)  // Set the Supplier instance
                .imageUrl("http://example.com/image2.jpg")
                .category(1L)
                .available(true)
                .build();
        // Assuming you have a method in SupplierService that returns a list of products for a given supplier name
        List<Product> mockProducts = Arrays.asList(mockProduct1,mockProduct2);
        when(supplierService.findAllProductsForSupplier("TestSupplier")).thenReturn(mockProducts);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/suppliers/findAllProductsForSupplier/TestSupplier")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productName").exists());
    }

    // Add more test methods for other endpoints as needed
}

