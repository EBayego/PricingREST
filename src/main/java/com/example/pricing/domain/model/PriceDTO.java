package com.example.pricing.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PriceDTO {

	private Long productId;
	private Long brandId;
	private Integer priceList;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Double price;

}