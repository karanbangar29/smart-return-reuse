package com.example.smartreturn;

import com.example.smartreturn.dto.ProductDto;
import com.example.smartreturn.model.Product;
import com.example.smartreturn.repository.ProductRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
public class ProductServiceRedisIntegrationTest {

//    @Container
//    @ServiceConnection
//    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:8.0.3"))
//            .withExposedPorts(6379);

    @Container
    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:8.0.3"))
            .withExposedPorts(6379);

    @DynamicPropertySource
    static void redisProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379));
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CacheManager cacheManager;

    @SpyBean
    private ProductRepo productRepoSpy;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        productRepo.deleteAll();  // clean db before each test
    }

    @Test
    void testAddProductAndCacheIt() throws Exception {
        ProductDto productDto = new ProductDto(null, "Test Product", Double.valueOf(999.99));

        MvcResult result = mockMvc.perform(post("/product/apis/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andReturn();

        Product savedProduct = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);
        Long productId = savedProduct.getId();

        assertTrue(productRepo.findById(productId).isPresent());

        Cache cache = cacheManager.getCache("PRODUCT_CACHE");
        assertNotNull(cache);
        assertNotNull(cache.get(productId, Product.class));
    }

    @Test
    void testGetProductAndVerifyCache() throws Exception {
        Product product = new Product();
        product.setProductName("Cached Product");
        product.setPrice(Double.valueOf(123.45));
        product = productRepo.save(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/apis?id=" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Cached Product"));

        Mockito.verify(productRepoSpy, Mockito.times(1)).findById(product.getId());

        Mockito.clearInvocations(productRepoSpy);

        // Call again, should hit cache and NOT call repo
        mockMvc.perform(MockMvcRequestBuilders.get("/product/apis?id=" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Cached Product"));

        Mockito.verify(productRepoSpy, Mockito.times(0)).findById(product.getId());
    }

    @Test
    void testUpdateProductAndVerifyCache() throws Exception {
        Product product = new Product();
        product.setProductName("Old Name");
        product.setPrice(Double.valueOf(50.0));
        product = productRepo.save(product);

        ProductDto updatedDto = new ProductDto(product.getId(), "Updated Name", (75.0));

        mockMvc.perform(MockMvcRequestBuilders.put("/product/apis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Updated Name"))
                .andExpect(jsonPath("$.price").value(75.0));

        Cache cache = cacheManager.getCache("PRODUCT_CACHE");
        assertNotNull(cache);
        Product cachedProduct = cache.get(product.getId(), Product.class);
        assertNotNull(cachedProduct);
        assertEquals("Updated Name", cachedProduct.getProductName());
    }

    @Test
    void testDeleteProductAndEvictCache() throws Exception {
        Product product = new Product();
        product.setProductName("To Be Deleted");
        product.setPrice((10.0));
        product = productRepo.save(product);

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/apis?id=" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("product deleted" + product.getId()));

        assertFalse(productRepo.findById(product.getId()).isPresent());

        Cache cache = cacheManager.getCache("PRODUCT_CACHE");
        assertNotNull(cache);
        assertNull(cache.get(product.getId()));
    }
}
