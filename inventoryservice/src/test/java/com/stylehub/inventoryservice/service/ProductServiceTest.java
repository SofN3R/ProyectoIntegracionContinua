package com.stylehub.inventoryservice.service;

import com.stylehub.inventoryservice.entity.Product;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
public class ProductServiceTest {

    @Test
    void findAll_returnsSeededProducts() {
        // given
        var service = new ProductService();

        // when
        List<Product> list = service.findAll();

        // then
        assertThat(list)
                .isNotNull()
                .hasSize(3)
                .extracting(Product::name)
                .containsExactlyInAnyOrder("Camiseta básica", "Jeans azul", "Chaqueta");
    }

    @Test
    void findById_returnsProduct_whenExists() {
        // given
        var service = new ProductService();

        // when
        Optional<Product> p = service.findById(2L);

        // then
        assertThat(p).isPresent();
        assertThat(p.get().id()).isEqualTo(2L);
        assertThat(p.get().name()).isEqualTo("Jeans azul");
        assertThat(p.get().stock()).isEqualTo(5);
    }

    @Test
    void findById_returnsEmpty_whenNotExists() {
        // given
        var service = new ProductService();

        // when
        Optional<Product> p = service.findById(999L);

        // then
        assertThat(p).isEmpty();
    }
}
