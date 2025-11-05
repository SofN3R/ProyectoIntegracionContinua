package com.stylehub.inventoryservice.entity;

public record Product(Long id, String name, String size, double price, int stock) {
}
