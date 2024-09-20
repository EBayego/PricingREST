package com.example.pricing.domain.repository;

import com.example.pricing.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

	@Query("SELECT p FROM Price p WHERE p.productId = :productId AND p.brandId = :brandId AND :applicationDate BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC LIMIT 1")
	Optional<Price> findPriceWithinDateRange(@Param("productId") Long productId, @Param("brandId") Long brandId, @Param("applicationDate") LocalDateTime applicationDate);
}