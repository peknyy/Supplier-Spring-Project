package com.example.projectcourse4.controllerTest;

import com.example.projectcourse4.configuration.JwtService;
import com.example.projectcourse4.controller.ProductController;
import com.example.projectcourse4.entity.Product;
import com.example.projectcourse4.entity.Supplier;
import com.example.projectcourse4.service.ProductService;
import com.example.projectcourse4.DTO.ProductRequest;
import com.example.projectcourse4.token.TokenRepository;
import org.junit.jupiter.api.Test;
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

import java.util.List;
import java.util.Optional;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @MockBean(JwtService.class)
    private JwtService jwtService;
    @MockBean(TokenRepository.class)
    private TokenRepository tokenRepository;

    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    public void testGetAllProducts() throws Exception {
        // Set up mock data
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

        Mockito.when(productService.getAll()).thenReturn(List.of(mockProduct1, mockProduct2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/products")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].productName").exists());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    public void testFindByProductName() throws Exception {
        String productName = "TestProduct";
        Product mockProduct = new Product();
        mockProduct.setProductName(productName);
        Optional<Product> optionalProduct = Optional.of(mockProduct);

        Mockito.when(productService.findByProductName(productName)).thenReturn(optionalProduct);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/products/findByProductName/{productName}", productName)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"productName\":\"TestProduct\"}"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    public void testDeleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/products/deleteProduct/{productId}", productId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(productService, Mockito.times(1)).deleteById(productId);
    }

    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    public void testAddProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("TestProduct");

        Mockito.when(productService.save(Mockito.anyString(), Mockito.any())).thenReturn(new Product());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/products/saveProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWtueSIsImlhdCI6MTY5NzIwMjE0NCwiZXhwIjoxNjk3Mjg4NTQ0fQ.IKkomInHdHC13BKTwGaVpmPUpur4QQki8QofNTCdN_k") // Add this line
                        .content("{\"productName\":\"TestProduct\"}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    public void testUpdateProduct() throws Exception {
        Long productId = 1L;
        ProductRequest productRequest = new ProductRequest();

        Mockito.when(productService.update(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(new Product());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/products/updateProduct/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWtueSIsImlhdCI6MTY5NzIwMjE0NCwiZXhwIjoxNjk3Mjg4NTQ0fQ.IKkomInHdHC13BKTwGaVpmPUpur4QQki8QofNTCdN_k") // Add this line
                        .content("{}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "ADMIN")
    public void testTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/products/test")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("test"));
    }
}
