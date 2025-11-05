package com.stylehub.billingservice.entity;

public record InvoiceResponse(Integer invoiceId, ProductDTO productInfo, String message, double total) {
}
