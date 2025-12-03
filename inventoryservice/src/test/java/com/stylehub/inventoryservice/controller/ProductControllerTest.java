package com.stylehub.inventoryservice.controller;

import com.stylehub.inventoryservice.entity.Product;
import com.stylehub.inventoryservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired private MockMvc mvc;
    @MockBean private ProductService service;

    @Test
    void all_returns200_andJsonArray() throws Exception {
        when(service.findAll()).thenReturn(List.of(
                new Product(1L,"Camiseta básica","M",19.99,10),
                new Product(2L,"Jeans azul","32",39.99,5)
        ));

        mvc.perform(get("/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].name").value("Jeans azul"));
    }

    @Test
    void byId_returns200_whenExists() throws Exception {
        when(service.findById(2L)).thenReturn(Optional.of(
                new Product(2L,"Jeans azul","32",39.99,5)
        ));

        mvc.perform(get("/products/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.price").value(39.99));
    }

    @Test
    void byId_returns404_whenNotFound() throws Exception {
        when(service.findById(999L)).thenReturn(Optional.empty());
        mvc.perform(get("/products/999")).andExpect(status().isNotFound());
    }
}
