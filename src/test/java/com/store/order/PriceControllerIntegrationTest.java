package com.store.order;

import com.example.pricing.PricingApplication;
import com.example.pricing.application.service.PriceService;
import com.example.pricing.domain.model.Price;
import com.example.pricing.domain.repository.PriceRepository;
import com.example.pricing.infrastructure.mapper.PriceMapper;

import reactor.core.publisher.Mono;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(classes = PricingApplication.class)
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
class PriceControllerIntegrationTest {

	@MockBean
    private PriceService priceService;

	@MockBean
    private PriceMapper priceMapper;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private WebTestClient webTestClient;
    
    private static List<Price> prices;
    
    @BeforeAll
    public static void setup() {
    	prices = List.of(
                new Price(null, 1L, LocalDateTime.of(2020, 6, 14, 0, 0), LocalDateTime.of(2020, 12, 31, 23, 59), 1, 35455L, 0, 35.50, "EUR"),
                new Price(null, 1L, LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 14, 18, 30), 2, 35455L, 1, 25.45, "EUR"),
                new Price(null, 1L, LocalDateTime.of(2020, 6, 15, 0, 0), LocalDateTime.of(2020, 6, 15, 11, 0), 3, 35455L, 1, 30.50, "EUR"),
                new Price(null, 1L, LocalDateTime.of(2020, 6, 15, 16, 0), LocalDateTime.of(2020, 12, 31, 23, 59), 4, 35455L, 1, 38.95, "EUR")
            );
    }

    @BeforeEach
    void setUp() {
        // Limpiar la base de datos antes de cada prueba
        priceRepository.deleteAll();
        priceRepository.saveAll(prices);
    }

    @Test
    void test1_getPrice_at1000_on14th() throws Exception {
    Long productId = 35455L;
    Long brandId = 1L;
    String applicationDate = "2020-06-14T10:00:00";

    Price price = prices.get(0);

    when(priceService.getApplicablePrice(productId, brandId, LocalDateTime.parse(applicationDate)))
        .thenReturn(Mono.just(price));

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/prices/find")
            .queryParam("productId", productId)
            .queryParam("brandId", brandId)
            .queryParam("date", applicationDate)
            .build())
        .exchange()
        .expectStatus().isOk()  // Verificar que la respuesta es 200 OK
        .expectBody()
        .jsonPath("$.priceList").isEqualTo(1)
        .jsonPath("$.price").isEqualTo(35.50);
}

    @Test
    void test2_getPrice_at1600_on14th() throws Exception {
        Long productId = 35455L;
        Long brandId = 1L;
        String applicationDate = "2020-06-14T16:00:00";

        Price price = prices.get(1);

        when(priceService.getApplicablePrice(productId, brandId, LocalDateTime.parse(applicationDate)))
            .thenReturn(Mono.just(price));

        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/prices/find")
                .queryParam("productId", productId)
                .queryParam("brandId", brandId)
                .queryParam("date", applicationDate)
                .build())
            .exchange()
            .expectStatus().isOk() 
            .expectBody()
            .jsonPath("$.priceList").isEqualTo(2)
            .jsonPath("$.price").isEqualTo(25.45);
    }

    @Test
    void test3_getPrice_at2100_on14th() throws Exception {
    	Long productId = 35455L;
        Long brandId = 1L;
        String applicationDate = "2020-06-14T21:00:00";

        Price price = prices.get(2);

        when(priceService.getApplicablePrice(productId, brandId, LocalDateTime.parse(applicationDate)))
            .thenReturn(Mono.just(price));

        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/prices/find")
                .queryParam("productId", productId)
                .queryParam("brandId", brandId)
                .queryParam("date", applicationDate)
                .build())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.priceList").isEqualTo(1)
            .jsonPath("$.price").isEqualTo(35.50);
    }

    @Test
    void test4_getPrice_at1000_on15th() throws Exception {
    	Long productId = 35455L;
        Long brandId = 1L;
        String applicationDate = "2020-06-15T10:00:00";

        Price price = prices.get(3);

        when(priceService.getApplicablePrice(productId, brandId, LocalDateTime.parse(applicationDate)))
            .thenReturn(Mono.just(price));

        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/prices/find")
                .queryParam("productId", productId)
                .queryParam("brandId", brandId)
                .queryParam("date", applicationDate)
                .build())
            .exchange()
            .expectStatus().isOk() 
            .expectBody()
            .jsonPath("$.priceList").isEqualTo(3)
            .jsonPath("$.price").isEqualTo(30.50);
    }

    @Test
    void test5_getPrice_at2100_on16th() throws Exception {
    	Long productId = 35455L;
        Long brandId = 1L;
        String applicationDate = "2020-06-16T21:00:00";

        Price price = prices.get(2);

        when(priceService.getApplicablePrice(productId, brandId, LocalDateTime.parse(applicationDate)))
            .thenReturn(Mono.just(price));

        webTestClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/prices/find")
                .queryParam("productId", productId)
                .queryParam("brandId", brandId)
                .queryParam("date", applicationDate)
                .build())
            .exchange()
            .expectStatus().isOk() 
            .expectBody()
            .jsonPath("$.priceList").isEqualTo(4)
            .jsonPath("$.price").isEqualTo(38.95);
    }

    @Test
    void getPrice_shouldReturn404_whenPriceNotFound() throws Exception {
    	webTestClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/prices/find")
            .queryParam("productId", "99999")
            .queryParam("brandId", "1")
            .queryParam("date", "2020-06-14T00:00:00")
            .build())
        .exchange()
        .expectStatus().isNotFound();
    }
}
