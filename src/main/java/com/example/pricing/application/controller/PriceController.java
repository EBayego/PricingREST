package com.example.pricing.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pricing.application.service.PriceService;
import com.example.pricing.domain.model.Price;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
@Tag(name = "Pedidos", description = "API para gestionar pedidos")
public class PriceController {

	@Autowired
	private PriceService priceService;

	@Operation(summary = "Devolver precio por ID de producto y marca")
	@GetMapping("/find")
	public ResponseEntity<Price> getPrice(@RequestParam Long productId, @RequestParam Long brandId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String date) {

		System.out.println("productId: " + productId + "brandId: " + brandId + "date: " + date);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDateTime applicationDate = LocalDateTime.parse(date, formatter);
		System.out.println("date transformada: " + date);

		Price price = priceService.getApplicablePrice(productId, brandId, applicationDate);

		System.out.println("el objeto final es: " + price);
		return ResponseEntity.ok(price);

	}
	
	@Operation(summary = "Todos los productos")
	@GetMapping
	public ResponseEntity<List<Price>> getAllPrice() {
		List<Price> prices = priceService.getPrices();

		System.out.println("la lista es: " + prices);
		return ResponseEntity.ok(prices);

	}
}