package com.example.pricing.infrastructure.configuration.mapper;

import org.springframework.stereotype.Component;

import com.example.pricing.domain.model.Price;
import com.example.pricing.domain.model.PriceDTO;

@Component
public class PriceMapper {
	public PriceDTO priceToPriceDto(Price price) {
		PriceDTO priceDto = new PriceDTO();
		priceDto.setBrandId(price.getBrandId());
		priceDto.setEndDate(price.getEndDate());
		priceDto.setPrice(price.getPrice());
		priceDto.setPriceList(price.getPriceList());
		priceDto.setProductId(price.getProductId());
		priceDto.setStartDate(price.getStartDate());

		return priceDto;
	}
}