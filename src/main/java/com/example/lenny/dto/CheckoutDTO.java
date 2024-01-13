package com.example.lenny.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckoutDTO {
    private List<CheckoutProductDTO> products;
    private double totalPrice;
    private double shippingDiscount;
    private double taxAndFee;
    private double finalPrice;
}
