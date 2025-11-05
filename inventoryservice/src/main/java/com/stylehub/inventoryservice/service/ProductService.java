package com.stylehub.inventoryservice.service;

import com.stylehub.inventoryservice.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>(
            List.of(
                    new Product(1L, "Camiseta básica", "M", 19.99, 10),
                    new Product(2L, "Jeans azul", "32", 39.99, 5),
                    new Product(3L, "Chaqueta", "M", 59.99, 2)
            )
    );

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(Long id) {
        return products.stream().filter(p -> p.id().equals(id)).findFirst();
    }
}
