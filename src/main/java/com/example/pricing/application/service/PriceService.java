package com.example.pricing.application.service;

import com.example.pricing.domain.repository.PriceRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.example.pricing.domain.exceptions.PriceNotFoundException;
import com.example.pricing.domain.model.Price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PriceService {

	@Autowired
	private PriceRepository priceRepository;

	public Mono<Price> getApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
		return priceRepository.findPriceWithinDateRange(productId, brandId, applicationDate).switchIfEmpty(Mono.error(new PriceNotFoundException("No applicable price found")));
	}
	
	public Flux<Price> getPrices() {
		return priceRepository.findAll();
	}
}