package com.example.pricing.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("PRICES")
public class Price {

    @Id 
    private Long id;

    @Column("BRAND_ID")  
    private Long brandId;

    @Column("START_DATE")
    private LocalDateTime startDate;

    @Column("END_DATE")
    private LocalDateTime endDate;

    @Column("PRICE_LIST")
    private Integer priceList;

    @Column("PRODUCT_ID")
    private Long productId;

    @Column("PRIORITY")
    private Integer priority;

    @Column("PRICE")
    private Double price;

    @Column("CURR")
    private String currency;
}
