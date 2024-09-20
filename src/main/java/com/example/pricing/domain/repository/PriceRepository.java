package com.example.pricing.domain.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.example.pricing.domain.model.Price;

import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

public interface PriceRepository extends ReactiveCrudRepository<Price, Long> {
    
    @Query("SELECT * FROM prices WHERE product_id = :productId AND brand_id = :brandId AND :applicationDate BETWEEN start_date AND end_date ORDER BY priority DESC LIMIT 1")
    Mono<Price> findPriceWithinDateRange(Long productId, Long brandId, LocalDateTime applicationDate);
}