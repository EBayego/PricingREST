package com.store.order;

import com.example.pricing.PricingApplication;
import com.example.pricing.application.service.PriceService;
import com.example.pricing.domain.exceptions.PriceNotFoundException;
import com.example.pricing.domain.model.Price;
import com.example.pricing.domain.repository.PriceRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = PricingApplication.class)
class PriceServiceTest {

	@Mock
	private PriceRepository priceRepository;

	@InjectMocks
	private PriceService priceService;

	public PriceServiceTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
    void getApplicablePrice_whenPriceExists_thenReturnPrice() {
        // Arrange
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        Price expectedPrice = new Price(null, brandId, LocalDateTime.of(2020, 6, 14, 15, 0),
                                        LocalDateTime.of(2020, 6, 14, 18, 30), 2, productId, 1, 25.45, "EUR");
        when(priceRepository.findPriceWithinDateRange(productId, brandId, applicationDate))
                .thenReturn(Optional.of(expectedPrice));

        // Act
        Price actualPrice = priceService.getApplicablePrice(productId, brandId, applicationDate);

        // Assert
        assertEquals(expectedPrice, actualPrice);
    }

	@Test
    void getApplicablePrice_whenPriceNotFound_thenThrowException() {
        // Arrange
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        when(priceRepository.findPriceWithinDateRange(productId, brandId, applicationDate))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PriceNotFoundException.class, () -> {
            priceService.getApplicablePrice(productId, brandId, applicationDate);
        });
    }

    @Test
    void getApplicablePrice_onDifferentDate_shouldReturnCorrectPrice() {
        // Arrange
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0);
        Price expectedPrice = new Price(null, brandId, LocalDateTime.of(2020, 6, 15, 16, 0),
                                        LocalDateTime.of(2020, 12, 31, 23, 59), 4, productId, 1, 38.95, "EUR");
        when(priceRepository.findPriceWithinDateRange(productId, brandId, applicationDate))
                .thenReturn(Optional.of(expectedPrice));

        // Act
        Price actualPrice = priceService.getApplicablePrice(productId, brandId, applicationDate);

        // Assert
        assertEquals(expectedPrice, actualPrice);
    }
}