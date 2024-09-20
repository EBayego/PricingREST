package com.example.pricing.application.service;

import com.example.pricing.domain.repository.PriceRepository;
import com.example.pricing.domain.exceptions.PriceNotFoundException;
import com.example.pricing.domain.model.Price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriceService {

	@Autowired
	private PriceRepository priceRepository;

	public Price getApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
		System.out.println("Iniciando el service");
		return priceRepository.findPriceWithinDateRange(productId, brandId, applicationDate)
				.orElseThrow(() -> new PriceNotFoundException("No applicable price found"));
	}
	
	public List<Price> getPrices() {
		System.out.println("Iniciando el service para devolver todos");
		return priceRepository.findAll();
	}
}