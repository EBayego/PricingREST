package com.store.order;

import com.example.pricing.PricingApplication;
import com.example.pricing.domain.model.Price;
import com.example.pricing.domain.repository.PriceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PricingApplication.class)
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        // Limpiar la base de datos antes de cada prueba
        priceRepository.deleteAll();

        List<Price> prices = List.of(
            new Price(null, 1L, LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59), 1, 35455L, 0, 35.50, "EUR"),
            new Price(null, 1L, LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 14, 18, 30), 2, 35455L, 1, 25.45, "EUR"),
            new Price(null, 1L, LocalDateTime.of(2020, 6, 15, 0, 0), LocalDateTime.of(2020, 6, 15, 11, 0), 3, 35455L, 1, 30.50, "EUR"),
            new Price(null, 1L, LocalDateTime.of(2020, 6, 15, 16, 0), LocalDateTime.of(2020, 12, 31, 23, 59), 4, 35455L, 1, 38.95, "EUR")
        );
        priceRepository.saveAll(prices);
    }

    @Test
    void test1_getPrice_at1000_on14th() throws Exception {
        mockMvc.perform(get("/api/prices/find")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("date", "2020-06-14T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.50))  // Tarifa con priceList 1
                .andExpect(jsonPath("$.priceList").value(1));
    }

    @Test
    void test2_getPrice_at1600_on14th() throws Exception {
        mockMvc.perform(get("/api/prices/find")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("date", "2020-06-14T16:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(25.45))  // Tarifa con priceList 2
                .andExpect(jsonPath("$.priceList").value(2));
    }

    @Test
    void test3_getPrice_at2100_on14th() throws Exception {
        mockMvc.perform(get("/api/prices/find")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("date", "2020-06-14T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.50))  // Tarifa con priceList 1
                .andExpect(jsonPath("$.priceList").value(1));
    }

    @Test
    void test4_getPrice_at1000_on15th() throws Exception {
        mockMvc.perform(get("/api/prices/find")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("date", "2020-06-15T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(30.50))  // Tarifa con priceList 3
                .andExpect(jsonPath("$.priceList").value(3));
    }

    @Test
    void test5_getPrice_at2100_on16th() throws Exception {
        mockMvc.perform(get("/api/prices/find")
                .param("productId", "35455")
                .param("brandId", "1")
                .param("date", "2020-06-16T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(38.95))  // Tarifa con priceList 4
                .andExpect(jsonPath("$.priceList").value(4));
    }

    @Test
    void getPrice_shouldReturn404_whenPriceNotFound() throws Exception {
        mockMvc.perform(get("/api/prices/find")
                .param("productId", "99999")
                .param("brandId", "1")
                .param("date", "2020-06-14T00:00:00"))
                .andExpect(status().isNotFound());
    }
}
