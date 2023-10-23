package com.smnravci.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {
    private int id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}