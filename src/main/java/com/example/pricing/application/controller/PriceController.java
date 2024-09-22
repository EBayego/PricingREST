package com.example.pricing.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.example.pricing.application.service.PriceService;
import com.example.pricing.domain.model.Price;
import com.example.pricing.domain.model.PriceDTO;
import com.example.pricing.infrastructure.mapper.PriceMapper;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
@Tag(name = "Pedidos", description = "API para gestionar pedidos")
public class PriceController {

	@Autowired
	private PriceService priceService;

	@Autowired
	private PriceMapper priceMapper;

	@Operation(summary = "Devolver precio por ID de producto y marca")
	@GetMapping("/find")
	public Mono<PriceDTO> getPrice(@RequestParam Long productId, @RequestParam Long brandId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

		System.out.println("productId: " + productId + "brandId: " + brandId + "date: " + date);
		// DateTimeFormatter formatter =
		// DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		// LocalDateTime applicationDate = LocalDateTime.parse(date, formatter);

		Mono<Price> price = priceService.getApplicablePrice(productId, brandId, date);

		return price.map(priceMapper::priceToPriceDto);

	}

	@Operation(summary = "Todos los productos")
	@GetMapping
	public Flux<Price> getAllPrice() {
        return priceService.getPrices();
    }
}